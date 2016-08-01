package com.hebn.jobCollector.application;

import com.hebn.jobCollector.domain.StackoverflowJobPosting;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by coupang on 2016. 8. 2..
 */
@Service
public class StackoverflowJobPostingServiceImpl implements StackoverflowJobPostingService {

    @Override
    public StackoverflowJobPosting findBy(Long id) {
        return null;
    }

    @Override
    public Page<StackoverflowJobPosting> find() {
        return null;
    }

}
