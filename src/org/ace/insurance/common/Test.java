package org.ace.insurance.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Test {

	public static void main(String[] args) {
		try {
			File file = new File("D://heinkoko/temp/123456/LF00000");
			File desfile = new File("D://heinkoko/upload/123456/UP00000");
			FileUtils.copyDirectory(file, desfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
