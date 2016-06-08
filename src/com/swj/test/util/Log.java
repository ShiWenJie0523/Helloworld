package com.swj.test.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日志类
 * @author Administrator
 *
 */
public class Log {
	/**
	 * 构造函数
	 */
  public Log() {
  }
  /**
   * 写日志
   * @param flag
   * @param msg
   */
  public static void WriteToLogFile(int flag, String msg) {

    try {
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
      String directory = System.getProperty("user.dir") + "/log/";
      File dir = new File(directory);
      if (!dir.isDirectory()) {
        dir.mkdir();
      }
      String fileName1 = System.getProperty("user.dir") + "/log/" +
          sdf1.format(new Date()) + ".txt";
      File file = new File(fileName1);
      if (file.exists()) {
        if (file.length() > 400*1024) {
          SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd_HHmm");
          String fileName2 = System.getProperty("user.dir") + "/log/" +
              sdf2.format(new Date()) + ".txt";
          file.renameTo(new File(fileName2));
        }
      }
      else {
        file.createNewFile();
      }
      FileWriter fileWriter = new FileWriter(fileName1, true);
      fileWriter.write(WriteToLog(flag, msg));
      fileWriter.flush();
      fileWriter.close();
    }
    catch (Exception ex) {
      Log.WriteToLog(1, "Log WriteToLogFile error:" + ex.getMessage());
    }
  }
  /**
   * 写日志
   * @param flag
   * @param msg
   * @return
   */
  public static String WriteToLog(int flag, String msg) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String head = "[Info]";
    if (flag == 2) {
      head = "[Warn]";
    }
    msg = head + "[" + sdf.format(new Date()) + "]" + msg +
        System.getProperty("line.separator");
    System.out.print(msg);
    return msg;
  }
  /**
   * 打印日志。
   * @param str
   * @param data
   */
  public static void printDataLog(String str, byte[] data) {
    System.out.println("[Data] " + str);
    String aa = "";
    try {
      for (int i = 0; i < data.length; i++) {
        String temp = temp = Integer.toHexString(data[i]).toUpperCase();
        if (data[i] < 0) {
          temp = temp.substring(temp.length() - 2, temp.length());
        }
        if (temp.length() == 1) {
          temp = "0" + temp;
        }
        aa += temp + " ";
        if ( (i % 16 == 15) || (i == data.length - 1)) {
          System.out.println(aa);
          aa = "";
        }
      }
    }
    catch (Exception ex) {
      System.out.println("Log printDataLog error:" + ex.getMessage());
    }
  }
}
