/**
 * @{#} UnRarZip.java Create on 2016年7月26日 下午12:31:31
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test;

import java.io.File;
import java.util.Properties;

/**
  *
  * 
  * @history 
  * <PRE> 
  * --------------------------------------------------------- 
  * VERSION       DATE            BY       CHANGE/COMMENT 
  * --------------------------------------------------------- 
  * 1.0           2016年7月26日       wenjie.shi               create  
  * ---------------------------------------------------------
  * </PRE>
  *
  */

public class UnRarZip {
	public static void main(String[] args) {
		UnRarZip unrar = new UnRarZip();
		unrar.unRarFileCurDir(new File("d:\\gd5188.20160720.sms.Z"));
	}

	/**
	 * 解压文件
	 * 
	 * @param target
	 * @param oldFile
	 */
	public void unRarFile(File target, File oldFile) {
		try {
			if (isWindow()) {
				String path = target.getParentFile().getAbsolutePath();
				File tmp = target.getParentFile();
				if (path.charAt(path.length() - 1) != ((char) File.separatorChar)) {
					tmp = new File(path + File.separator);
					if (!tmp.exists())
						tmp.mkdirs();
				}
				String cmd2 = "cmd /c \"C:\\Program Files (x86)\\WinRAR\\WinRAR.exe\" x -r -o+ -ibck -y "
						+ oldFile + " *.* " + tmp;
				Runtime rt = Runtime.getRuntime();
				Process pre = rt.exec(cmd2);
				if (0 != pre.waitFor()) {
					pre.destroy();
				}
				rt.runFinalization();
				System.out.println(cmd2);
			} else {
				System.out.println("can't get rar command abort");
			}
		} catch (Exception e) {
			System.out.println("解压发生异常");
		}
	}

	/**
	 * 解压到当前目录
	 * 
	 * @param filePath
	 */
	public void unRarFileCurDir(File filePath) {
		unRarFile(filePath, filePath);
	}

	/**
	 * 是否是window
	 * 
	 * @return
	 */
	public boolean isWindow() {
		Properties properties = System.getProperties();
		String os = properties.getProperty("os.name");
		if (os != null && os.contains("Windows"))
			return true;
		return false;
	}
}
