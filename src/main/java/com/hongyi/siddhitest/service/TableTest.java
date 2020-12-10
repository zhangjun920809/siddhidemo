package com.hongyi.siddhitest.service;

import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.util.transport.InMemoryBroker;

/**
 * @author User
 * @date 2020/11/26 15:48
 */
public class TableTest {
    public static void main(String[] args) {
        test();
    }

    /**
     * 从stream和table中，筛选符合条件的记录
     */
    public static void test() {
        String stroe = "@store(type='rdbms' , jdbc.url='jdbc:mysql://localhost:3306/siddhitest?useUnicode=true&serverTimezone=UTC&characterEncoding=utf8&useSSL=false'," +
                "username = 'root', " +
                "password = 'zhang@123'," +
                "jdbc.driver.name='com.mysql.jdbc.Driver' )" +
                "@primaryKey('id') " +
                "@index('name') " +
                "define table Tabletest (id int ,name string, age int);";

        String inputstream  = "@source(type='inMemory', topic='Hongyi', @map(type='passThrough')) " +
                " define stream Intpustream (name string, age int); " ;

        String query ="@sink(type='http',publisher.url = 'http://192.168.2.120:8888/get/alert',@map(type = 'json')) "+
                "define stream TempStream (name string, age int,uname string);";

        //从Tabletest表中筛选Tabletest.age == age 的记录
        String rdbms = "from Intpustream[Tabletest.age == age in Tabletest]" +
                "select  Tabletest.name,Tabletest.age,Intpustream.name as uname " +
                "insert into TempStream;";


        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stroe + inputstream +query+rdbms);
        siddhiAppRuntime.start();

        InMemoryBroker.publish("Hongyi",new Object[]{"张俊",35});
        siddhiAppRuntime.shutdown();
        siddhiManager.shutdown();
    }
}
