package org.ace.insurance.common;

import java.util.ArrayList;
import java.util.List;

public class ListSplitor {
	public static <T extends Object> List<List<T>> split(List<T> list, int targetSize) {
	    List<List<T>> lists = new ArrayList<List<T>>();
	    for (int i = 0; i < list.size(); i += targetSize) {
	        lists.add(list.subList(i, Math.min(i + targetSize, list.size())));
	    }
	    return lists;
	}
	
	public static void main(String... args) {
	    List<Integer> list = new ArrayList<Integer>();
	    list.add(0);
	    list.add(1);
	    list.add(2);
	    list.add(3);
	    list.add(4);
	    list.add(5);
	    list.add(6);

	    int targetSize = 3;
	    List<List<Integer>> lists = split(list, targetSize);
	    System.out.println(lists); // [[0, 1, 2], [3, 4, 5], [6]]
	}	
}
