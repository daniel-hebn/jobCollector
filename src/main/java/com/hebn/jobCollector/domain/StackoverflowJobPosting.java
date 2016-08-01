package com.hebn.jobCollector.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by greg.lee on 2016. 8. 1..
 */

@Entity
@Table(name = "stackoverflow_job_postings")
@Getter
public class StackoverflowJobPosting {

    @Id
    @GeneratedValue
    private Long id;
    private String link;
    private String title;
    private String categories;
    private String description;
    private Date publishDate;
}
