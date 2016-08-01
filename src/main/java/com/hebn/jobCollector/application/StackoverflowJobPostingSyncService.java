package com.hebn.jobCollector.application;

import com.hebn.jobCollector.domain.StackoverflowJobPosting;

/**
 * Created by greg.lee on 2016. 8. 1..
 */
public interface StackoverflowJobPostingSyncService {

    void save(StackoverflowJobPosting jobPosting);
}
