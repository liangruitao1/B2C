package com.turing.b2c.utils;

import org.csource.fastdfs.*;

public class FastDFSClient {

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient= null;

    public FastDFSClient(String conf) throws Exception {
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer,storageServer);
    }

    public  String uploadFile(byte[] fileContent,String exName)throws Exception{
        String result = storageClient.upload_file1(fileContent,exName,null);
        return  result;
    }
}
