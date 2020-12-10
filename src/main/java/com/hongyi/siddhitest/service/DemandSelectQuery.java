package com.hongyi.siddhitest.service;

import org.junit.Test;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;

import java.util.Arrays;

/**
 * @author User
 * @date 2020/11/26 18:29
 */
public class DemandSelectQuery {
    public static void main(String[] args) {
        demandquery();
    }

    @Test
    public static void demandquery() {
        String stroe = "@store(type='rdbms' , jdbc.url='jdbc:mysql://localhost:3306/siddhitest?useUnicode=true&serverTimezone=UTC&characterEncoding=utf8&useSSL=false'," +
                "username = 'root', " +
                "password = 'zhang@123'," +
                "jdbc.driver.name='com.mysql.jdbc.Driver' )" +
                "@primaryKey('id') " +
                "@index('name') " +
                "define table Tabletest (id int ,name string, age int);";

        String stroe1 = "@store(type='rdbms' , jdbc.url='jdbc:mysql://localhost:3306/siddhitest?useUnicode=true&serverTimezone=UTC&characterEncoding=utf8&useSSL=false'," +
                "username = 'root', " +
                "password = 'zhang@123'," +
                "jdbc.driver.name='com.mysql.jdbc.Driver' )" +
                "@primaryKey('id') " +
                "@index('name') " +
                "define table Tabletest1 (id int ,name string, age int);";
        String inputstream  = "@source(type='inMemory', topic='Hongyi', @map(type='passThrough')) " +
                " define stream Intpustream (name string, age int); " ;

        String query ="@sink(type='http',publisher.url = 'http://192.168.2.120:8888/get/alert',@map(type = 'json')) "+
                "define stream TempStream (name string, age int,uname string);";


        //定义查询语句
        String query1=" from Tabletest select name,age ";
        //定义插入语句
        String query2 ="select 4 as id, '方月梦' as name,8 as age insert into Tabletest1;" ;

        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stroe+stroe1 + inputstream +query);
        siddhiAppRuntime.start();

        //执行查询语句
        Event[] result1 = siddhiAppRuntime.query(query1);
        //执行插入语句
        Event[] result2 = siddhiAppRuntime.query(query2);
        System.out.println(Arrays.toString(result1));
        //结果：[Event{timestamp=-1, data=[jun, 40], isExpired=false}, Event{timestamp=-1, data=[tian, 35], isExpired=false}]
        System.out.println(Arrays.toString(result2));
        //结果[]
    }
}
