package com.mochi.lockstep.communicate;

import com.mochi.lockstep.communicate.message.Operation;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-28
 * version: 1.0
 */
@Getter
@RequiredArgsConstructor
public class Communicate {
    private final String name;
    private final Channel channel;
    private LinkedBlockingQueue<Operation> msgs = new LinkedBlockingQueue<>();

    public void putMsg(Operation msg) {
        msgs.add(msg);
    }

    public void putMsg(Collection<Operation> operations) {
        msgs.addAll(operations);
    }

    public List<Operation> getOperations() {
        List<Operation> resut = new ArrayList<>();
        while(true) {
            Operation temp = msgs.poll();
            if(temp==null) {
                return resut;
            } else {
                resut.add(temp);
            }
        }
    }
}
