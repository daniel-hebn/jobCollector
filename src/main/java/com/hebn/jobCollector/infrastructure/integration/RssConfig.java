package com.hebn.jobCollector.infrastructure.integration;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by greg.lee on 2016. 8. 1..
 */

@Slf4j
@Configuration
public class RssConfig {

    @Autowired
    private Environment env;

    @Bean
    @InboundChannelAdapter(value = "feedChannel", poller = @Poller(maxMessagesPerPoll = "100", fixedRate = "10000"))
    public MessageSource<SyndEntry> feedAdapter() throws MalformedURLException {
        // env.getProperty("url")
        return new FeedEntryMessageSource(new URL("http://stackoverflow.com/jobs/feed"), "feedAdapter");
    }

    // filter
    @MessageEndpoint
    public static class Endpoint {

        @ServiceActivator(inputChannel = "feedChannel")
        public void log(Message<SyndEntry> message) {
            SyndEntry payload = message.getPayload();

            log.info("link = {}", payload.getLink());
            log.info("title = {}", payload.getTitle());
            log.info("url = {} ", payload.getUri());
            log.info("category === ");
            payload.getCategories().stream().forEach(category -> System.out.println(category.getName()));
            log.info("category === ");
            log.info("description = {}", payload.getDescription().getValue());
            log.info("pub date = {}", payload.getPublishedDate().toString());

        }
    }

    // pipe
    @Bean
    public MessageChannel feedChannel() {
        return new QueueChannel(500);
    }

    // <int:poller id="poller" default="true" fixed-rate="10"/>
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        PeriodicTrigger trigger = new PeriodicTrigger(1000 * 10);
        trigger.setFixedRate(true);
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(trigger);
        return pollerMetadata;
    }
}
