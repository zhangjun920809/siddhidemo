package com.hongyi.siddhitest.service;

import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.util.transport.InMemoryBroker;

/**
 * @author User
 * @date 2020/11/26 13:51
 */
public class JoinTest {
    public static void main(String[] args) {
        String outputstream1 ="@sink(type='http',publisher.url = 'http://192.168.2.120:8888/get/alert',@map(type = 'json'))" +
                "define stream outputstream (weight int,name string,number int,levelname string,isboy bool); ";

        String intpustream ="@source(type='inMemory', topic='Hongyi', @map(type='passThrough')) " +
                " define stream intpustream  (weight int,name string); ";

        String inpustream1 ="@source(type='inMemory', topic='Hongyi1', @map(type='passThrough')) " +
                "   define stream intpustream1  (number int,levelname string,isboy bool);";

        String query = "from intpustream#window.time(10 min) as A " +
                " full outer join intpustream1#window.time(10 min) as B " +
                " on A.name == B.levelname " +
                "select A.weight, A.name, B.number, B.levelname, B.isboy " +
                "insert into outputstream;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(outputstream1 + intpustream + inpustream1 + query);
        siddhiAppRuntime.start();

        InMemoryBroker.publish("Hongyi",new Object[]{20,"张俊"});
        InMemoryBroker.publish("Hongyi1",new Object[]{189,"田泳",true});
        InMemoryBroker.publish("Hongyi1",new Object[]{110,"张俊",true});
        InMemoryBroker.publish("Hongyi",new Object[]{99,"芳芳"});

    }
}
