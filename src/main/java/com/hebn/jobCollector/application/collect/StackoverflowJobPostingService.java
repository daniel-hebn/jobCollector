package com.hebn.jobCollector.application.collect;

import com.hebn.jobCollector.domain.StackoverflowJobPosting;
import org.springframework.data.domain.Page;

/**
 * Created by greg.lee on 2016. 8. 1..
 */
public interface StackoverflowJobPostingService {

    StackoverflowJobPosting findBy(Long id);

    Page<StackoverflowJobPosting> find();

}
