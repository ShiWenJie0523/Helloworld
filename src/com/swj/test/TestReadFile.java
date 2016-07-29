/**
 * @{#} TestReadFile.java Create on 2016年7月26日 上午11:19:25
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.chilkatsoft.CkUnixCompress;

public class TestReadFile {

	
	static {
	    try {
	        System.loadLibrary("chilkat");
	    } catch (UnsatisfiedLinkError e) {
	      System.err.println("Native code library failed to load.\n" + e);
	      System.exit(1);
	    }
	  }
	
	public static void main(String[] args) {
		CkUnixCompress uc = new CkUnixCompress();

	    boolean success;

	    //  Any string unlocks the component for the 1st 30-days.
	    success = uc.UnlockComponent("Anything for 30-day trial");
	    if (success != true) {
	        System.out.println(uc.lastErrorText());
	        return;
	    }

	    success = uc.CompressFile("hamlet.xml","hamlet.xml.Z");
	    if (success != true) {
	        System.out.println(uc.lastErrorText());
	    }
	    else {
	        System.out.println("Success.");
	    }

		
	}
}
