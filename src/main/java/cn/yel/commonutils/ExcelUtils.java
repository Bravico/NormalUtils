package cn.yel.commonutils;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {
	// 默认单元格内容为数字时格式
	private static DecimalFormat df = new DecimalFormat("0");
	// 默认单元格格式化日期字符串
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 格式化数字
	private static DecimalFormat nf = new DecimalFormat("0.00");

	public static ArrayList<Object> readExcel(File file) {
		if (file == null) {
			return null;
		}
		if (file.getName().endsWith("xlsx")) {
			// 处理excel2007
			return readExcel2007Simple(file);
		} else {
			// 处理excel2003
			return readExcel2003Simple(file);
		}
	}

	/*
	 * @return 将返回结果存储在ArrayList内，存储结构与二维数组类似 lists.get(0).get(0)表示过去Excel中0行0列单元格
	 */
	public static ArrayList<ArrayList<Object>> readExcel2003(File file) {
		try {
			ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
			ArrayList<Object> colList;
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			Object value;
			for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				colList = new ArrayList<Object>();
				if (row == null) {
					// 当读取行为空时
					if (i != sheet.getPhysicalNumberOfRows()) {// 判断是否是最后一行
						rowList.add(colList);
					}
					continue;
				} else {
					rowCount++;
				}
				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
						// 当该单元格为空
						if (j != row.getLastCellNum()) {// 判断是否是该行中最后一个单元格
							colList.add("");
						}
						continue;
					}
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())) {
							value = df.format(cell.getNumericCellValue());
						} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
							value = nf.format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = Boolean.valueOf(cell.getBooleanCellValue());
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = cell.toString();
					}// end switch
					colList.add(value);
				} // end for j
				rowList.add(colList);
			} // end for i
			return rowList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * @return 将返回结果存储在ArrayList内，存储结构与二维数组类似 lists.get(0).get(0)表示过去Excel中0行0列单元格
	 */
	public static ArrayList<Object> readExcel2003Simple(File file) {
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			Object value;
			int sum_rows = sheet.getPhysicalNumberOfRows();
			//skip the first line
			for (int i = sheet.getFirstRowNum(); i < sum_rows; i++) {
				row = sheet.getRow(i);
				if (row == null) {
					// 当读取行为空时
					if (i == sum_rows) {// 判断是否是最后一行
						return list;
					}
					continue;
				} 
				int index = row.getFirstCellNum();
				cell = row.getCell(index);
				if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
					continue;
				}
//				System.out.println(cell.getCellType()+"------------------------");
				switch(cell.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					value = Boolean.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				default:
					value = cell.toString();
				}
				list.add(value);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static ArrayList<Object> readExcel2007Simple(File file) {
		XSSFWorkbook wb =null;
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			int sum_rows = sheet.getPhysicalNumberOfRows();
			Object value;
			//skip the first line
			for (int i = sheet.getFirstRowNum(); i < sum_rows; i++) {
				row = sheet.getRow(i);
				if (row == null) {
					// 当读取行为空时
					if (i == sum_rows) {// 判断是否是最后一行
						return list;
					}
					continue;
				} 
				int index = row.getFirstCellNum();
				cell = row.getCell(index);
				if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
					continue;
				}
				switch(cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = Boolean.valueOf(cell.getBooleanCellValue());
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				default:
					value = cell.toString();
				}
				list.add(value);
			}
			if(wb!=null) wb.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<ArrayList<Object>> readExcel2007(File file) {
		try {
			ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
			ArrayList<Object> colList;
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			Object value;
			for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				colList = new ArrayList<Object>();
				if (row == null) {
					// 当读取行为空时
					if (i != sheet.getPhysicalNumberOfRows()) {// 判断是否是最后一行
						rowList.add(colList);
					}
					continue;
				} else {
					rowCount++;
				}
				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
						// 当该单元格为空
						if (j != row.getLastCellNum()) {// 判断是否是该行中最后一个单元格
							colList.add("");
						}
						continue;
					}
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())) {
							value = df.format(cell.getNumericCellValue());
						} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
							value = nf.format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = Boolean.valueOf(cell.getBooleanCellValue());
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = cell.toString();
					}// end switch
					colList.add(value);
				} // end for j
				rowList.add(colList);
			} // end for i
			wb.close();
			return rowList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writeExcel(ArrayList<ArrayList<Object>> result, String path) {
		if (result == null) {
			return;
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		for (int i = 0; i < result.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			if (result.get(i) != null) {
				for (int j = 0; j < result.get(i).size(); j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellValue(result.get(i).get(j).toString());
				}
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		File file = new File(path);// Excel文件生成后存储的位置。
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(content);
			os.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 去除Excel中可能存在的小数
	 * @param beforeStr
	 * @return
	 */
	public static String removeDigits(String beforeStr) {
		if(beforeStr.contains(".")) {
			return beforeStr.split("\\.")[0];
		}else {
			return beforeStr;
		}
	}

	public static DecimalFormat getDf() {
		return df;
	}

	public static void setDf(DecimalFormat df) {
		ExcelUtils.df = df;
	}

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void setSdf(SimpleDateFormat sdf) {
		ExcelUtils.sdf = sdf;
	}

	public static DecimalFormat getNf() {
		return nf;
	}

	public static void setNf(DecimalFormat nf) {
		ExcelUtils.nf = nf;
	}

	public static void main(String args[]) throws Exception {
		
	}

}
