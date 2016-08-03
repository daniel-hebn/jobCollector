package com.hebn.jobCollector.domain;

import lombok.Getter;

import javax.persistence.Column;
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
    private String categories;
    private Date publishDate;

    public StackoverflowJobPosting() {}

    public StackoverflowJobPosting(Long postingId, String link, String title, String categories, Date publishDate) {
        this.postingId = postingId;
        this.link = link;
        this.title = title;
        this.categories = categories;
        this.publishDate = publishDate;
    }

}
