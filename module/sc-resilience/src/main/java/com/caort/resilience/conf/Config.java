package com.caort.resilience.conf;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Caort
 * @date 2022/7/17 12:13
 */
@Configuration
public class Config {
    private static Logger LOG = LoggerFactory.getLogger(Config.class);

    @Bean
    public RegistryEventConsumer<CircuitBreaker> myRegistryEventConsumer() {

        return new RegistryEventConsumer<>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                LOG.info("添加CircuitBreaker[{}]", entryAddedEvent.getAddedEntry().getName());
                entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> LOG.info(event.toString()));
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {

            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {

            }
        };
    }
}
