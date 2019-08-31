package com.example.atomixtest;


import io.atomix.core.Atomix;
import io.atomix.core.profile.Profile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockManager {

    @Bean
    Atomix atomix() {
        Atomix atomix = Atomix.builder()
                // unique ID per instane, probably not required
                .withMemberId("member-1")
                // get discoverable IP
                .withAddress("localhost:5000")
                // for auto discovery.
                .withMulticastEnabled()
                // Primitives like distributed lock needs Partition Groups https://atomix.io/docs/latest/user-manual/cluster-management/partition-groups/
                // If advanced config is not required, can directly use Profiles.
                .addProfile(Profile.dataGrid())
                .build();

        atomix.start().join();
        return atomix;
    }
}
