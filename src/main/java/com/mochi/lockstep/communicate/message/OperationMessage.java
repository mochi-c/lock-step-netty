package com.mochi.lockstep.communicate.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-29
 * version: 1.0
 */
@Getter
@RequiredArgsConstructor
public class OperationMessage {
    private final String room;
    private final String name;
    private final List<Operation> operations = new ArrayList<>();
}
