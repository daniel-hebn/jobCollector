package com.hebn.jobCollector.application.collect;

import com.hebn.jobCollector.domain.StackoverflowJobPosting;
import com.hebn.jobCollector.domain.StackoverflowJobPostingRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
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

	private static final String STACKOVERFLOW_JOB_URL_PARTITION = "stackoverflow.com/jobs/";
	private static final String W3_ORG_NAMESPACE_URL = "http://www.w3.org/2005/Atom";

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
		Date publishDate = payload.getPublishedDate();
		String categories = payload.getCategories().stream().map(category -> category.getName()).collect(Collectors.joining(","));

		int preIndex = link.indexOf(STACKOVERFLOW_JOB_URL_PARTITION) + STACKOVERFLOW_JOB_URL_PARTITION.length();
		int postIndex = link.indexOf("/", preIndex);
		final Long postingId = Long.parseLong(link.substring(preIndex, postIndex));

		String company = "", country = "", location = "";
		for (Element element : payload.getForeignMarkup()) {
			if (element.getNamespaceURI().equals(W3_ORG_NAMESPACE_URL) && "author".equals(element.getName())) {
				company = element.getValue();
			} else if (element.getNamespaceURI().contains(STACKOVERFLOW_JOB_URL_PARTITION)) {
				String[] arrCountryAndLocation = element.getValue().split(", ");
				location = arrCountryAndLocation[0];
				if (arrCountryAndLocation.length == 2) {
					country = arrCountryAndLocation[1];
				} else if (arrCountryAndLocation.length == 3) {
					country = arrCountryAndLocation[2];
				}
			}
		}
		return new StackoverflowJobPosting(postingId, link, title, company, country, location, categories, publishDate);
	}

}
