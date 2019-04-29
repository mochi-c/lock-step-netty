package com.mochi.lockstep.server;

import com.mochi.lockstep.communicate.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-28
 * version: 1.0
 */
@Component
public class SyncServer {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    long syncTimeStap;

    ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);//启用2个线程

    class SyncThread implements Runnable {
        private final String roomName;
        private final Room room;

        SyncThread(String name) {
            roomName = name;
            room = roomRepository.getWithCreate(name);
        }

        @Override
        public void run() {
            CommunicateRepository communicateRepository = room.getCommunicateRepository();
            OperationLog operationLog = new OperationLog(room.getFrames());
            for (Communicate communicate : communicateRepository.getAllCommunicate()) {
                operationLog.addOperation(communicate.getName(), communicate.getOperations());
            }
            for (Communicate communicate : communicateRepository.getAllCommunicate()) {
                communicate.getChannel().write(operationLog);
            }
            for (Communicate communicate : communicateRepository.getAllCommunicate()) {
                communicate.getChannel().flush();
            }
            room.incrFrames();
        }

    }

    public void createAndRun(String name) {
        SyncThread syncThread = new SyncThread(name);
        pool.scheduleAtFixedRate(syncThread, 0, syncTimeStap, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void stop() {
        pool.shutdown();
    }

}
