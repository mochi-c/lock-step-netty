package com.mochi.lockstep;

import com.mochi.lockstep.communicate.CommunicateRepository;
import com.mochi.lockstep.config.NettyProperties;
import com.mochi.lockstep.config.SyncProperties;
import com.mochi.lockstep.server.SyncServer;
import com.mochi.lockstep.server.TCPServer;
import com.mochi.lockstep.server.handler.SomethingChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Spring Java Configuration and Bootstrap
 *
 * @author Jibeom Jung
 */
@SpringBootApplication
@EnableConfigurationProperties({NettyProperties.class, SyncProperties.class})
public class NettyApplication {

    @Autowired
    private NettyProperties nettyProperties;
    @Autowired
    private SyncProperties syncProperties;

    @Autowired
    private SomethingChannelInitializer somethingChannelInitializer;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(NettyApplication.class, args);
        TCPServer tcpServer = context.getBean(TCPServer.class);
        tcpServer.start();
        SyncServer syncServer = context.getBean(SyncServer.class);
        syncServer.createAndRun("defRoom");
        System.out.println("Start");
    }

    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(somethingChannelInitializer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        for (@SuppressWarnings("rawtypes") ChannelOption option : keySet) {
            b.option(option, tcpChannelOptions.get(option));
        }
        return b;
    }

    @Bean
    public Map<ChannelOption<?>, Object> tcpChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_BACKLOG, nettyProperties.getBacklog());
        return options;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(nettyProperties.getBossCount());
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(nettyProperties.getWorkerCount());
    }

    @Bean
    public InetSocketAddress tcpSocketAddress() {
        //指定ipv4地址 默认ipv6
        return new InetSocketAddress("127.0.0.1", nettyProperties.getTcpPort());
    }

    @Bean
    public long syncTimeStap() {
        return syncProperties.getTimeStep();
    }

    @Bean
    public int syncThreads() {
        return syncProperties.getThreads();
    }

}
