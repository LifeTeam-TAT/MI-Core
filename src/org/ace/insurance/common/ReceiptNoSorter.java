package org.ace.insurance.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class ReceiptNoSorter<T extends IReceiptNoSorter> {
	private List<T> sortedList;

	public ReceiptNoSorter(List<T> entitylist) {
		if (entitylist != null && !entitylist.isEmpty()) {
			sortedList = new ArrayList<T>();
			Collections.sort(entitylist, new Comparator<T>() {
				public int compare(T obj1, T obj2) {
					StringTokenizer st1 = new StringTokenizer(obj1.getReceiptNo(), "/");
					String id1 = null;
					while (st1.hasMoreTokens()) {
						id1 = st1.nextToken();
						if (id1.length() == 10)
							break;

					}
					StringTokenizer st2 = new StringTokenizer(obj2.getReceiptNo(), "/");
					String id2 = null;
					while (st2.hasMoreTokens()) {
						id2 = st2.nextToken();
						if (id2.length() == 10)
							break;

					}
					return id1.compareTo(id2);
				}
			});
			for (T t : entitylist) {
				sortedList.add(t);
			}

		}

	}

	public List<T> getSortedList() {
		return sortedList;
	}

	/* Example Usage */
	public static void main(String[] args) {
		Person p1 = new Person("May Zin Phyo", "FNI-HO/LP/RT/0000000016/6-2019");
		Person p2 = new Person("Yin Yin Min Naing", "FNI-HO/LP/RT/0000000011/6-2019");
		Person p3 = new Person("Kyaw Lin Tun", "FNI-HO/LP/RT/0000000020/6-2019");
		Person p4 = new Person("Hein Ko", "FNI-HO/LP/RT/0000000040/6-2019");
		List<Person> list = new ArrayList<Person>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		ReceiptNoSorter<Person> recSorter = new ReceiptNoSorter<Person>(list);
		List<Person> sortedPersonList = recSorter.getSortedList();
		for (Person p : sortedPersonList) {
			System.out.println(p.getName() + " \t: " + p.getProposalNo());
		}
	}

}
