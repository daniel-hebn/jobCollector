package com.hebn.jobCollector.application;

import com.hebn.jobCollector.domain.StackoverflowJobPosting;
import com.hebn.jobCollector.domain.StackoverflowJobPostingRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by coupang on 2016. 8. 1..
 */

@Slf4j
@Service
@MessageEndpoint
public class StackoverflowJobPostingEventListener {

    private static final String STACKOVERFLOW_JOB_BASE_URL = "http://stackoverflow.com/jobs/";

    @Autowired
    private StackoverflowJobPostingRepository stackoverflowJobPostingRepository;

    // filter
    @Transactional(propagation = Propagation.SUPPORTS)
    @ServiceActivator(inputChannel = "feedChannel")
    public void syncJobPosting(Message<SyndEntry> message) {
        StackoverflowJobPosting jobPosting = convertFrom(message.getPayload());

        if (notExistJobPosting(jobPosting))
            stackoverflowJobPostingRepository.save(jobPosting);
    }

    private boolean notExistJobPosting(StackoverflowJobPosting jobPosting) {
        return stackoverflowJobPostingRepository.findByPostingId(jobPosting.getPostingId()) == null;
    }

    private StackoverflowJobPosting convertFrom(SyndEntry payload) {
        String link = payload.getLink();
        String title = payload.getTitle();
        String categories = payload.getCategories().stream().map(category -> category.getName()).collect(Collectors.joining(","));
        Date publishDate = payload.getPublishedDate();

        int preIndex = STACKOVERFLOW_JOB_BASE_URL.length();
        int postIndex = link.indexOf("/", preIndex);
        Long postingId = Long.parseLong(link.substring(preIndex, postIndex));

        return new StackoverflowJobPosting(postingId, link, title, categories, publishDate);
    }

}
