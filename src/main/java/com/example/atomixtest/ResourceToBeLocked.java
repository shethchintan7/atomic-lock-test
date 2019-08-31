package com.example.atomixtest;

import io.atomix.core.Atomix;
import io.atomix.core.lock.AsyncDistributedLock;
import io.atomix.core.lock.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Configuration
public class ResourceToBeLocked {

    @Autowired
    Atomix atomix;

    @PostConstruct
    void postConstruct() {
        DistributedLock distributedLock = atomix.lockBuilder("my-lock").build();

        AsyncDistributedLock asyncLock = distributedLock.async();
        asyncLock.tryLock(2, TimeUnit.SECONDS).thenAccept(didWeAcquireLock -> {
            if (didWeAcquireLock) {
                System.out.println("Acquired 1");
            } else {
                System.out.println("Didn't acquire 1");
            }
        });

        asyncLock.tryLock(2, TimeUnit.SECONDS).thenAccept(didWeAcquireLock -> {
            if (didWeAcquireLock) {
                System.out.println("Acquired 2");
            } else {
                System.out.println("Didn't acquire 2");
            }
        });
    }
}
