package org.ace.java.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class ListExample {

	  public static void main(String[] args) {
		initialLizeList();
	  }
	  
	  public static void initialLizeList() {
		List<BaseClass> orgList = new ArrayList<BaseClass>();
	    List<BaseClass> bList = new ArrayList<BaseClass>();
	    for (int i = 0; i < 9; i++) {
	    	bList.add(new BaseClass(i, i));
	    	orgList.add(new BaseClass(i, i));
	    }
	    
	    Iterator<BaseClass> iterator = bList.iterator();

	    if (iterator.hasNext()) {
	    	BaseClass b = iterator.next();
	    	b.y = 42;
	    }
	    
	    mainProcess(orgList, bList);
	  }
	  
	  public static List<BaseClass> getInsertedList(List list) {
		  ListIterator<BaseClass> listIterator = list.listIterator();
		  if (listIterator.hasNext()) {
		    	listIterator.next();
		    	listIterator.add(new BaseClass(99,99));
		    }
		  return list;
	  }
	  
	  public static List<BaseClass> getDeletedList(List list) {
		  ListIterator<BaseClass> listIterator = list.listIterator();
		  if (listIterator.hasNext()) {
		    	listIterator.next();
		    	listIterator.remove();
		    }
		  return list;
	  }
	  
	  public static void mainProcess(List list, List list1) {
		  int value = 0;
		  boolean keepGoing = true;
		  while (keepGoing) {
			  System.out.println("Enter '1' for Check Original List, '2' for Check Updated List, '3' for Check Deleted List, '4' for Check InsertedList List ");
			  Scanner sc = new Scanner(System.in);
			  value = sc.nextInt();
			  
			  switch (value) {
				case 1: System.out.print("Original List : "); printList(list); break;
					
				case 2:	System.out.print("Updated List : "); printList(list1); break;
					
				case 3:	System.out.print("Deleted List : "); printList(getDeletedList(list)); break;
					
				case 4:	System.out.print("Inserted List : "); printList(getInsertedList(list)); break;

				default:
					break;
			}
			  System.out.println("Do you want to continue ? Type : Yes or No ");
			  Scanner scanner = new Scanner(System.in);
			  String keepGo = scanner.nextLine();
			  if(keepGo.equalsIgnoreCase("No")) {
				  keepGoing = false;
			  }
		  }
		  
	  }

	  private static void printList(List list) {
	    Iterator iterator = list.iterator();
	    while (iterator.hasNext()) {
	      System.out.print(iterator.next());
	    }
	    System.out.println();
	  }

	  private static class BaseClass {
	    int x;
	    int y;

	    BaseClass(int x, int y) {
	      this.x = x;
	      this.y = y;
	    }

	    @Override
	    public String toString() {
	      return String.format("[%d, %d]", x, y);
	    }
	  }
	}