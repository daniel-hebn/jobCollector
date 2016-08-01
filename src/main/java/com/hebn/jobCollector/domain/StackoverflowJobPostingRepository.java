package com.hebn.jobCollector.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by greg.lee on 2016. 8. 1..
 */

@Repository
public interface StackoverflowJobPostingRepository extends PagingAndSortingRepository<StackoverflowJobPosting, Long> {

}
