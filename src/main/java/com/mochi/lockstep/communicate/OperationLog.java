package com.mochi.lockstep.communicate;

import com.mochi.lockstep.communicate.message.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-29
 * version: 1.0
 */
@Getter
@RequiredArgsConstructor
public class OperationLog {
    private final long frames;
    private Map<String, List<Operation>> operationLogs = new HashMap<>();

    public void addOperation(String name, List<Operation> operations) {
        operationLogs.put(name, operations);
    }

    @Override
    public String toString() {
        String result = "frames:" + frames + "\r\n";
        for (Map.Entry<String, List<Operation>> op : operationLogs.entrySet()) {
            result += "\t" + op.getKey() + ":" + op.getValue() + "\r\n";
        }
        return result;
    }
}
