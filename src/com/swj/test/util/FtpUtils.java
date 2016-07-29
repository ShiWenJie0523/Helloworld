/**
 * @{#} FtpUtils.java Create on 2016年7月26日 下午2:37:51
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;


public class FtpUtils {
    private static Logger logger = Logger.getLogger(FtpUtils.class);
    FTPClient ftpClient = null;
    private String path = ""; 
    /**
     * 获取FTPClient对象
     * 
     * @param ftpHost
     *            FTP主机服务器
     * @param ftpPassword
     *            FTP 登录密码
     * @param ftpUserName
     *            FTP登录用户名
     * @param ftpPort
     *            FTP端口 默认为21
     * @return
     */
    public  FTPClient getFTPClient(String ftpHost, String ftpPassword,
            String ftpUserName,int port) {
        try {
            ftpClient = new FTPClient();
           
            ftpClient.setDefaultPort(port); //设置默认端口
            ftpClient.configure(getFtpConfig()); 
            ftpClient.connect(ftpHost);// 连接FTP服务器
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode(); 
//			ftpClient.setConnectTimeout(60000);
            boolean isSuccess = ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if(isSuccess){
                logger.info("FTP登录成功。");
            }
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                logger.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            logger.info("FTP的IP地址可能错误，请正确配置。");
            e.printStackTrace();
        } catch (IOException e) {
            logger.info("FTP的端口错误,请正确配置。");
            e.printStackTrace();
        }
        return ftpClient;
    }
     //设置FTP客服端的配置--一般可以不设置 
    private   FTPClientConfig getFtpConfig() { 
            FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX); 
            ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING); 
            return ftpConfig; 
    } 
    public static void  recursion(String root){
    	 File file = new File(root);
    	 File[] subFile = file.listFiles();
    	 for (int i = 0; i < subFile.length; i++) {
    	  if (subFile[i].isDirectory()) {
    	   System.out.println("目录: " + subFile[i].getName());
    	   recursion(subFile[i].getAbsolutePath());
    	  }else{
    	   System.out.println("文件: " + subFile[i].getName());
    	  }
    	 }
    	}


    /** 
     * 列出Ftp服务器上的所有文件和目录 
     */ 
    public  void listRemoteAllFiles(String remotePath) { 
    	FtpUtils util=new FtpUtils();
    	ftpClient = util.getFTPClient("218.200.244.123", "5188", "a975320",21);//21是端口，根据自己情况而定
        try { 
                //String[] names = ftpClient.listNames(); 
        		//更换目录到当前目录
        		ftpClient.changeWorkingDirectory(remotePath);
                FTPFile[] files = ftpClient.listFiles(); 
                for (int i = 0; i < files.length; i++) {   
                	if (files[i].isFile()) {   
                		System.out.println(files[i].getName());   
                	} else if (files[i].isDirectory()) { 
                		System.out.println("目录"+files[i].getName());
                		listRemoteAllFiles(remotePath + files[i].getName() + "/"); 
                		System.out.println("遍历结束");
                    }
                }
        } catch (Exception e) { 
                e.printStackTrace(); 
        } 
    } 
	public static void main(String args[]){
		FtpUtils util=new FtpUtils();
		util.listRemoteAllFiles("/");
    }
}
