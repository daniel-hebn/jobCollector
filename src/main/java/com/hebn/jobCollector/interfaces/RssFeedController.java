package com.hebn.jobCollector.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by greg.lee on 2016. 8. 1..
 */
@RestController
public class RssFeedController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello - job Collector";
    }
}
