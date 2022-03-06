package cn.yel.commonutils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class CsvUtils {

	public static void main(String[] args) {
		

	}
	
	public static <T> List<T> readCsvReturnBean(String filename,Class<T> object) throws Exception {
		List<T> beans = new CsvToBeanBuilder(new FileReader(filename))
			       .withType(object).build().parse();
		return beans;
	}
	
	public static List<String[]> readCsvReturnArray(String filename) throws Exception {
		CSVReader reader = new CSVReader(new FileReader(filename));
		List<String[]> result = reader.readAll();
		return result;
	}
	
	public static List<String[]> readCsvBasic(String filename) throws Exception {
		ArrayList<String[]> result = new ArrayList<String[]>();
		CSVReader reader = new CSVReader(new FileReader(filename));
		String [] nextLine;
	     while ((nextLine = reader.readNext()) != null) {
	        result.add(nextLine);
	     }
	     return result;
	}
	
//	public List<String[]> readCsvSmallLoad(String filename) throws Exception {
//		ArrayList<String[]> result = new ArrayList<String[]>();
//		CSVIterator iterator = new CSVIterator(new CSVReader(new FileReader(filename)));
//	    for(String[] nextLine : iterator.iterator()) {
//	       // nextLine[] is an array of values from the line
//	       System.out.println(nextLine[0] + nextLine[1] + "etc...");
//	    }
//	}
	
	public static <T> void writeCsvwithBean(List<T> beans,String filePath) throws Exception {
	     Writer writer = new FileWriter(filePath);
	     StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
	     beanToCsv.write(beans);
	     writer.close();
	}
	


}
