/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mochi.lockstep.communicate;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Channel Repository using HashMap
 *
 * @author heks
 */
public class CommunicateRepository {

    private ConcurrentMap<String, Communicate> communicateCache = new ConcurrentHashMap<String, Communicate>();

    public CommunicateRepository put(String key, Channel value) {
        Communicate communicate = new Communicate(key, value);
        communicateCache.put(key, communicate);
        return this;
    }

    public ConcurrentMap<String, Communicate> getCommunicateCache() {
        return communicateCache;
    }

    public Communicate getCommunicate(String key) {
        return communicateCache.get(key);
    }

    public Channel getChannel(String key) {
        return communicateCache.get(key).getChannel();
    }

    public Collection<Communicate> getAllCommunicate() {
        return communicateCache.values();
    }

    public void remove(String key) { this.communicateCache.remove(key); }

    public int size() {
        return this.communicateCache.size();
    }
}
