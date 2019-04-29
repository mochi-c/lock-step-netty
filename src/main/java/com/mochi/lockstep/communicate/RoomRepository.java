package com.mochi.lockstep.communicate;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-29
 * version: 1.0
 */
@Component
public class RoomRepository {
    private ConcurrentMap<String, Room> roomCache = new ConcurrentHashMap<String, Room>();

    public Room getRoom(String name) {
        return roomCache.get(name);
    }

    public Room getWithCreate(String name) {
        if (roomCache.get(name) == null) {
            synchronized (this) {
                if(roomCache.get(name) == null) {
                    Room room = new Room();
                    roomCache.put(name, room);
                    return room;
                } else {
                    return roomCache.get(name);
                }
            }
        } else {
            return roomCache.get(name);
        }
    }
}
