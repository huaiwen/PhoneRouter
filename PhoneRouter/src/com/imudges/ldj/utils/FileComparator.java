
package com.imudges.ldj.utils;

import java.io.File;
import java.util.Comparator;



public class FileComparator implements Comparator<File> {

	public int compare(File file1, File file2) {
	
		if (file1.isDirectory() && !file2.isDirectory()) {
			return -1000;
		} else if (!file1.isDirectory() && file2.isDirectory()) {
			return 1000;
		}
		
		return (file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
	}
}