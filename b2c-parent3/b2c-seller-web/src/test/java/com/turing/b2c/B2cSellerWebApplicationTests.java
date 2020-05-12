package com.turing.b2c;

import org.csource.fastdfs.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class B2cSellerWebApplicationTests {

    @Test
    public void contextLoads() throws Exception {
        ClientGlobal.init("fdfs_client.conf");
        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
        String s = storageClient.upload_file1("D:\\学习阶段\\S3\\分布式\\分布式架构03\\资料\\静态原型\\网站前台\\img\\banner1.png", "png", null);
        System.out.println(s);
    }

}
