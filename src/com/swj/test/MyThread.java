/**
 * @{#} MyThread.java Create on 2016年5月12日 上午10:47:26
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test;

import com.swj.test.util.Log;

import redis.clients.jedis.Jedis;

/**
  *
  * 
  * @history 
  * <PRE> 
  * --------------------------------------------------------- 
  * VERSION       DATE            BY       CHANGE/COMMENT 
  * --------------------------------------------------------- 
  * 1.0           2016年5月12日       wenjie.shi               create  
  * ---------------------------------------------------------
  * </PRE>
  *
  */

public class MyThread extends Thread {

	@Override
	public void run() {
		//Jedis jedis = null;
		try {
			//jedis = new Jedis("127.0.0.1", 6379);
			for (int i = 0; i < 100; i++) {
				Log.WriteToLogFile(1, "现在的数值为：" + i);
				if(i%2 == 0){
					//jedis.set("username" + i, "value" + i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			/*if(jedis != null){
				jedis.close();
			}*/
		}
		
	}
}
