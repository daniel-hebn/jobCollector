package com.hebn.jobCollector.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by greg.lee on 2016. 8. 1..
 */

@Entity
@Getter
public class StackoverflowJobPosting {

    @Id
    @GeneratedValue
    private Long id;
    private Long postingId;
    @Column(columnDefinition = "TEXT", length = Integer.MAX_VALUE)
    private String link;
    private String title;
    private String company;
    private String country;
    private String location;
    private String categories;
    private String publishDate;

    public StackoverflowJobPosting() {

    }

    public StackoverflowJobPosting(Long postingId, String link, String title, String company, String country,
                                   String location, String categories, String publishDate) {
        this.postingId = postingId;
        this.link = link;
        this.title = title;
        this.company = company;
        this.country = country;
        this.location = location;
        this.categories = categories;
        this.publishDate = publishDate;
    }

}
