package com.mochi.lockstep.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-28
 * version: 1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "sync")
public class SyncProperties {
    long timeStep;
    int threads;
}
