/**
 * @{#} FtpTest.java Create on 2016年7月26日 下午1:24:48
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpTest {
	
	private static Logger logger = Logger.getLogger(FtpTest.class);
	public static FTPClient ftp = new FTPClient();;
	public static ArrayList<String> arFiles;
	static FTPClient ftpClient = new FTPClient();
	private static String server = "172.17.22.42";
	private static int port = 21;
	private static String userName = "usn";
	private static String userPassword = "pwd";

	
	/**
	 * 链接到服务器
	 * 
	 * @return
	 */
	public static boolean open() {
		if (ftpClient != null)
			return true;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(server);
			//ftpClient.login(userName, userPassword);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ftpClient = null;
			return false;
		}
	}


	
	/**
	 * FTP上传单个文件测试
	 */
	public static void testUpload() {
		FTPClient ftpClient = new FTPClient();
		FileInputStream fis = null;

		try {
			ftpClient.connect("192.168.14.117");
			ftpClient.login("admin", "123");

			File srcFile = new File("C:\\new.gif");
			fis = new FileInputStream(srcFile);
			// 设置上传目录
			ftpClient.changeWorkingDirectory("/admin/pic");
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.storeFile("3.gif", fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			try {
				fis.close();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	/**
	 * FTP下载单个文件测试
	 */
	public static void testDownload() {
		FTPClient ftpClient = new FTPClient();
		FileOutputStream fos = null;

		try {
			ftpClient.connect("172.17.22.42");
			// ftpClient.login("admin", "123");

			String remoteFileName = "1.txt";
			fos = new FileOutputStream("D:/");

			ftpClient.setBufferSize(1024);
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.retrieveFile(remoteFileName, fos);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			try {
				fos.close();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

		/**
		 * 重载构造函数
		 * @param isPrintCommmand 是否打印与FTPServer的交互命令
		 */
		public FtpTest(boolean isPrintCommmand){
			ftp = new FTPClient();
			arFiles = new ArrayList<String>();
			if(isPrintCommmand){
				ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
			}
		}
		
		/**
		 * 登陆FTP服务器
		 * @param host FTPServer IP地址
		 * @param port FTPServer 端口
		 * @param username FTPServer 登陆用户名
		 * @param password FTPServer 登陆密码
		 * @return 是否登录成功
		 * @throws IOException
		 */
		public boolean login(String host,int port,String username,String password) throws IOException{
			this.ftp.connect(host,port);
			if(FTPReply.isPositiveCompletion(this.ftp.getReplyCode())){
				/*if(this.ftp.login(username, password)){
					this.ftp.setControlEncoding("GBK");
					return true;
				}*/
				return true;
			}
			if(this.ftp.isConnected()){
				this.ftp.disconnect();
			}
			return false;
		}
		
		/**
		 * 关闭数据链接
		 * @throws IOException
		 */
		public void disConnection() throws IOException{
			if(this.ftp.isConnected()){
				this.ftp.disconnect();
			}
		}
		
		/**
		 * 递归遍历出目录下面所有文件
		 * @param pathName 需要遍历的目录，必须以"/"开始和结束
		 * @throws IOException
		 */
		public void List(String pathName) throws IOException{
			if(pathName.startsWith("/")&&pathName.endsWith("/")){
				String directory = pathName;
				//更换目录到当前目录
				this.ftp.changeWorkingDirectory(directory);
				FTPFile[] files = this.ftp.listFiles();
				for(FTPFile file:files){
					if(file.isFile()){
						arFiles.add(directory+file.getName());
					}else if(file.isDirectory()){
						List(directory+file.getName()+"/");
					}
				}
			}
		}
		
		/**
		 * 递归遍历目录下面指定的文件名
		 * @param pathName 需要遍历的目录，必须以"/"开始和结束
		 * @param ext 文件的扩展名
		 * @throws IOException 
		 */
		public void List(String pathName,String ext) throws IOException{
			if(pathName.startsWith("/")&&pathName.endsWith("/")){
				String directory = pathName;
				//更换目录到当前目录
				this.ftp.changeWorkingDirectory(directory);
				FTPFile[] files = this.ftp.listFiles();
				for(FTPFile file:files){
					if(file.isFile()){
						if(file.getName().endsWith(ext)){
							arFiles.add(directory+file.getName());
						}
					}else if(file.isDirectory()){
						List(directory+file.getName()+"/",ext);
					}
				}
			}
		}
		public static void main(String[] args) throws IOException {
			ftp.connect(server);
			String directory = "/";
			//更换目录到当前目录
			ftp.changeWorkingDirectory(directory);
			FTPFile[] files = ftp.listFiles();
			for(FTPFile file:files){
				if(file.isFile()){
					arFiles.add(directory+file.getName());
				}
			}
			System.out.println(arFiles.size());
			
		}
}
