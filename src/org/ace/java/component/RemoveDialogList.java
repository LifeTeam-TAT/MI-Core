package org.ace.java.component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToOne;

import org.apache.commons.io.FilenameUtils;

public class RemoveDialogList {
	private List<String> filePathList = new ArrayList<String>();

	public List<String> readByPath(File file) {
		if (file.isFile()) {
			if (!"Thumbs.db".equals(file.getName())) {
				filePathList.add(file.getAbsolutePath());
			}
		} else if (file.isDirectory()) {
			File[] listOfFiles = file.listFiles();
			if (listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++) {
					readByPath(listOfFiles[i]);
				}
			} else {
				System.out.println("[ACCESS DENIED]");
			}
		}
		return filePathList;
	}

	public static void main(String[] args) throws Exception {
		RemoveDialogList selectDialogClass = new RemoveDialogList();
		List<String> fileList = selectDialogClass.readByPath(new File("D:\\migrateSpace\\ggip\\src\\org\\ace\\insurance"));
		int count = 0;
		for (String file : fileList) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String strLine = "";
				while ((strLine = br.readLine()) != null) {
					if (strLine.contains("@Entity")) {
						System.out.println(file);
						count++;
					} 
			}
				
				br.close();
		}
		System.out.println(count);
	}
}
