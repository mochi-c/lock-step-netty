package com.mochi.lockstep.communicate.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-29
 * version: 1.0
 */
@Getter
@RequiredArgsConstructor
public class Operation {
    private final long time;
    private final String operation;

    @Override
    public String toString() {
        return time + ":" + operation;
    }
}
