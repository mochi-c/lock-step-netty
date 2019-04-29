package com.mochi.lockstep.communicate;

import com.mochi.lockstep.communicate.message.Operation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-29
 * version: 1.0
 */
@Getter
public class Room {

    private volatile long frames;
    private CommunicateRepository communicateRepository = new CommunicateRepository();
    private List<OperationLog> operationLogs = new ArrayList<>();

    OperationLog addOperationLog(OperationLog operationLog) {
        operationLogs.add(operationLog);
        return operationLog;
    }

    public long incrFrames() {
        return ++frames;
    }
}
