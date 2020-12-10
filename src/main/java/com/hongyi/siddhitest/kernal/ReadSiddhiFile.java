package com.hongyi.siddhitest.kernal;

import org.wso2.carbon.stream.processor.core.internal.StreamProcessorDeployer;

import java.io.File;

/**
 * @author User
 * @date 2020/11/25 17:35
 */
public class ReadSiddhiFile {

    public void StreamProcessorDeployerTest() throws Exception{
        String file = "//";
        StreamProcessorDeployer.deploySiddhiQLFile(new File(file));
    }


}
