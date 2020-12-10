package com.hongyi.siddhitest.controller;

import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wso2.siddhi.core.util.transport.InMemoryBroker;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author User
 * @date 2020/11/25 17:12
 */
@RestController
@RequestMapping("/siddhi")
public class TestSiddhi {

    /**
     * 从内存中发布 数据
     * @param request
     * @param weight
     * @param name
     * @return
     */
    @RequestMapping("/sourceInMemory")
    public String sourceInMemory(HttpServletRequest request,
                                 @RequestParam int weight,
                                 @RequestParam String name) {
        InMemoryBroker.publish("hongyi",new Object[]{weight,name});
        return getBody(request);
    }

    @RequestMapping
    public String SinkTest(){
        return "";
    }



    /**
     * 获取请求参数
     * @param request
     * @return
     */
    private String getBody(HttpServletRequest request){
        String reqBbody = "";
        try {
            reqBbody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reqBbody;
    }

}
