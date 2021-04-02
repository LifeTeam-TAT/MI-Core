package org.ace.java.web.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Scanner;

/**
 * This class generates insert sql script dedicated only for ggip project.
 * 
 * @author Ye Kyaw Kyaw Htoo
 * @since 1.0.0 beta
 * @date 2013/06/25
 */
public class GenerateNativeSQL {
	static StringBuffer script = new StringBuffer();
	public static String createScript = "CREATE TABLE ";
	public static String charScript = " CHAR (36) ";
	public static String notScript = " NOT ";
	public static String nullScript = " NULL ";
	public static String dateTimeScript = " DATETIME ";
	public static String numericScript = " NUMERIC (18, 4) ";
	public static String varChar_50 = " VARCHAR (50) ";
	public static String varChar_200 = " VARCHAR (50) ";
	public static String intScript = " INT ";
	public static String openBracket = " ( ";
	public static String closeBracket = " ) ";
	public static String comma = ", ";
	public static String ending = "CREATEDUSERID CHAR(36) NULL,  \n" + "CREATEDDATE DATETIME NULL,  \n" + "UPDATEDUSERID CHAR(36) NULL,  \n" + "UPDATEDDATE DATETIME NULL,  \n"
			+ "VERSION INT, \n" + "PRIMARY KEY  (ID) \n );";

	public static void main(String[] args) {

		String tableName;
		String path;
		Scanner myScanner = new Scanner(System.in);
		System.out.println("Enter full path for your java class");
		path = myScanner.nextLine();
		System.out.println("Enter TableName");
		tableName = myScanner.nextLine();
		Field[] fields;
		try {

			fields = Class.forName(path).getDeclaredFields();
			script.append(createScript);
			script.append(tableName.toUpperCase());
			script.append(openBracket);
			script.append("\n");

			generate(fields);

			script.append(ending);
			System.out.println(script);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void generate(Field[] fields) {

		for (Field f : fields) {

			if (!f.getName().equalsIgnoreCase("serialVersionUID")) {

				Annotation[] annos = f.getAnnotations();
				boolean flag = true;
				boolean embd = false;
				boolean enm = false;

				if (f.getName().equalsIgnoreCase("id")) {
					String field = f.getName().toUpperCase();
					script.append(field);
					script.append(charScript);
					script.append(notScript);
					script.append(nullScript);
					script.append(comma);
					script.append("\n");
				}

				if (annos.length == 0 || annos == null) {
					generatorTwo(f);

				} else {

					Anno: for (Annotation an : annos) {
						String a = an.annotationType().getSimpleName();
						if (a.equalsIgnoreCase("Transient")) {
							flag = false;
							break Anno;
						} else if (a.equalsIgnoreCase("Embedded")) {
							embd = true;
						} else if (a.equalsIgnoreCase("Enumerated")) {
							enm = true;
						}
					}

					if (flag) {

						if (embd) {
							Field[] fe;
							try {
								fe = Class.forName(f.getType().getName()).getDeclaredFields();
								generate(fe);
							} catch (ClassNotFoundException e) {
								System.err.println("Class Not Found");
								e.printStackTrace();
							}

						} else if (enm) {
							generateEnum(f);
						} else {
							generatorTwo(f);
						}
					}
				}
			}
		}
	}

	public static void generateEnum(Field f) {
		String field;
		field = f.getName().toUpperCase();
		script.append(field);
		script.append(varChar_50);
		script.append(nullScript);
		script.append(comma);
		script.append("\n");
	}

	public static void generatorTwo(Field f) {
		String field;
		if (f.getType().getSimpleName().equalsIgnoreCase("String")) {
			field = f.getName().toUpperCase();
			script.append(field);
			script.append(varChar_50);
			script.append(nullScript);
			script.append(comma);
			script.append("\n");
		} else if (f.getType().getSimpleName().equalsIgnoreCase("int")) {
			field = f.getName().toUpperCase();
			script.append(field);
			script.append(intScript);
			script.append(nullScript);
			script.append(comma);
			script.append("\n");
		} else if (f.getType().getSimpleName().equalsIgnoreCase("Date")) {
			field = f.getName().toUpperCase();
			script.append(field);
			script.append(dateTimeScript);
			script.append(nullScript);
			script.append(comma);
			script.append("\n");
		} else if (f.getType().getSimpleName().equalsIgnoreCase("double")) {
			field = f.getName().toUpperCase();
			script.append(field);
			script.append(numericScript);
			script.append(nullScript);
			script.append(comma);
			script.append("\n");
		} else {
			if (!f.getType().getSimpleName().equalsIgnoreCase("List")) {
				field = f.getName().toUpperCase() + "ID";
				script.append(field);
				script.append(charScript);
				script.append(nullScript);
				script.append(comma);
				script.append("\n");
			}
		}

	}
}
