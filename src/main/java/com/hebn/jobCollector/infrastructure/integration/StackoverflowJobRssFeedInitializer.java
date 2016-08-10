package com.hebn.jobCollector.infrastructure.integration;

import com.rometools.rome.feed.synd.SyndEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by greg.lee on 2016. 8. 1..
 */

@Slf4j
@Configuration
public class StackoverflowJobRssFeedInitializer {

    @Autowired
    private Environment env;

    @Bean
    @InboundChannelAdapter(value = "feedChannel", poller = @Poller(maxMessagesPerPoll = "500", fixedRate = "600000"))
    public MessageSource<SyndEntry> feedAdapter() throws MalformedURLException {
        // env.getProperty("url")
        return new FeedEntryMessageSource(new URL("http://stackoverflow.com/jobs/feed"), "feedAdapter");
    }

    // pipe
    @Bean
    public MessageChannel feedChannel() {
        return new QueueChannel(1000);
    }

    // <int:poller id="poller" default="true" fixed-rate="10"/>
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        PeriodicTrigger trigger = new PeriodicTrigger(1000 * 60 * 10);
        trigger.setFixedRate(true);
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(trigger);
        return pollerMetadata;
    }
}
