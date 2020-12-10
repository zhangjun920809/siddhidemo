package com.hongyi.siddhitest.service;

import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.output.StreamCallback;
import org.wso2.siddhi.core.util.EventPrinter;
import org.wso2.siddhi.core.util.transport.InMemoryBroker;

/**
 * @author User
 * @date 2020/11/25 17:01
 */
public class SourceInMemery {

    public static void main(String[] args) {
        testSourceInMe();
    }
    public static void testSourceInMe(){

        String outputstream1 ="@sink(type='http',publisher.url = 'http://192.168.2.120:8888/get/alert',@map(type = 'json'))" +
                "define stream outputstream (weight int,name string); ";

        String intpustream ="@source(type='inMemory', topic='Hongyi', @map(type='passThrough')) " +
                " define stream intpustream  (weight int,name string); ";

       // String query = "@info(name = 'query888') from intpustream select weight,name insert into outputstream; ";
        String query = "from intpustream#log(\"INFO\", \"Sample Event88 :\", true)\n" +
                "select *\n" +
                "insert into outputstream;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(outputstream1+intpustream + query );

        /*siddhiAppRuntime.addCallback("outputstream", new StreamCallback() {

            @Override
            public void receive(Event[] events) {
                System.out.println("**********main********receive");
                EventPrinter.print(events);
                //To convert and print event as a map
                //EventPrinter.print(toMap(events));
            }
        });*/
        siddhiAppRuntime.start();
        System.out.println("开始");
        InMemoryBroker.publish("Hongyi",new Object[]{20,"张俊"});
        System.out.println("结束");

    }


}
