package org.nhnnext.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Mylog {
	public static final String NO_DOCUMENT = "No Document";
	public static final String ERROR = "";

	public static void printError(Exception e) {

		try {
			Writer writer = new BufferedWriter(new FileWriter("log.txt", true));
			File f = new File("l.txt");
			System.out.println(f.getCanonicalPath());
			System.out.println("\n\n" + "Error : " + e.getMessage() + "\n\n");

			writer.write("!!!!Error : " + e.getMessage() + "\n");
			writer.write(e.getLocalizedMessage() + "\n");

			for (int i = 0; i < e.getStackTrace().length; i++) {
				StackTraceElement ste = e.getStackTrace()[i];
				if(!ste.getClassName().contains("org.nhnnext"))
					continue;
				
				writer.write(ste.getClassName() + " : " + ste.getMethodName()
						+ "  :  " + ste.getLineNumber() + "\n");

				if (i == 5)
					break;
			}
			writer.write("\n\n");
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
