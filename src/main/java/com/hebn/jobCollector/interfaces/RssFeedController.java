package com.hebn.jobCollector.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by greg.lee on 2016. 8. 1..
 */
@Controller
public class RssFeedController {

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello - job Collector";
    }

    @RequestMapping(value = "/naver-map", method = RequestMethod.GET)
    public String naverMapTest() {
        return "naver-test";
    }
}
