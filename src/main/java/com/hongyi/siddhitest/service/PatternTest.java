package com.hongyi.siddhitest.service;

import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.util.transport.InMemoryBroker;

/**
 * @author User
 * @date 2020/11/26 14:50
 */
public class PatternTest {
    public static void main(String[] args) throws InterruptedException {
        String TempStream = " @source(type='inMemory', topic='Hongyi', @map(type='passThrough')) " +
                "  define stream intpustream  ( temp double,roomno int); ";

        String alertStream = "@sink(type='http',publisher.url = 'http://192.168.2.120:8888/get/alert',@map(type = 'json'))" +
                "define stream AlertStream  ( roomno int,initTemp double ,finalTemp double); ";


        String pattern = "from  every (e1=intpustream) -> e2=intpustream[e1.roomno == e2.roomno and (e1.temp+5) <= temp] " +
                "within 1 min " +
                "select e1.roomno,e1.temp as initTemp,e2.temp as finalTemp " +
                "insert into AlertStream " ;

        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(TempStream + alertStream + pattern );
        siddhiAppRuntime.start();

        InMemoryBroker.publish("Hongyi",new Object[]{20.3,10});
        InMemoryBroker.publish("Hongyi",new Object[]{21.3,10});

        InMemoryBroker.publish("Hongyi",new Object[]{22.3,10});
        Thread.sleep(5000);
        InMemoryBroker.publish("Hongyi",new Object[]{26.3,10});
        InMemoryBroker.publish("Hongyi",new Object[]{30.3,10});
    }
}
