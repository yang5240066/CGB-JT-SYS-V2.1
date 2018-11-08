package com.jt.common;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jt.prod.entity.ProdMenu;

public class ExcelOperate {
	/**
	 * 创建Excel
	 * 
	 * @param list
	 * @return 数据表
	 */
	public static void createExcel(List<ProdMenu> list,String excelUrl) {
		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet("商品菜单");
		// 添加表头行
		HSSFRow hssfRow = sheet.createRow(0);
		// 设置单元格格式居中
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		Class cls = ProdMenu.class;
		Field[] fields = cls.getDeclaredFields();
		
		//System.out.println(declaredFields[0].getName());
		System.out.println(Arrays.toString(fields));
		// 添加表头内容
//		HSSFCell headCell = hssfRow.createCell(0);
//		headCell.setCellValue(fields[1].getName());
//		headCell.setCellStyle(cellStyle);
//
//		headCell = hssfRow.createCell(1);
//		headCell.setCellValue(fields[2].getName());
//		headCell.setCellStyle(cellStyle);
//
//		headCell = hssfRow.createCell(2);
//		headCell.setCellValue(fields[3].getName());
//		headCell.setCellStyle(cellStyle);
//		
//		headCell = hssfRow.createCell(3);
//		headCell.setCellValue(fields[4].getName());
//		headCell.setCellStyle(cellStyle);
//		
//		headCell = hssfRow.createCell(4);
//		headCell.setCellValue(fields[5].getName());
//		headCell.setCellStyle(cellStyle);
//		
//		headCell = hssfRow.createCell(5);
//		headCell.setCellValue(fields[6].getName());
//		headCell.setCellStyle(cellStyle);

		// 添加数据内容
		for (int i = 0; i < list.size(); i++) {
			hssfRow = sheet.createRow((int) i + 1);
			ProdMenu prodMenu = list.get(i);

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(prodMenu.getId());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(1);
			cell.setCellValue(prodMenu.getName());
			cell.setCellStyle(cellStyle);

			cell = hssfRow.createCell(2);
			cell.setCellValue(prodMenu.getCategory());
			cell.setCellStyle(cellStyle);
			cell = hssfRow.createCell(3);
			cell.setCellValue(prodMenu.getPrice());
			cell.setCellStyle(cellStyle);
			cell = hssfRow.createCell(4);
			cell.setCellValue(prodMenu.getPnum());
			cell.setCellStyle(cellStyle);
			cell = hssfRow.createCell(5);
			cell.setCellValue(prodMenu.getDescription());
			cell.setCellStyle(cellStyle);
		}

		// 保存Excel文件
		try {
			OutputStream outputStream = new FileOutputStream(
					excelUrl);
			workbook.write(outputStream);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		
//		// 创建Excel表格
//		createExcel(getStudent());
//		System.out.println("创建xls成功");
//		// 读取Excel表格
//		//List<Student> list = readExcel();
//		//System.out.println(list.toString());
//	}
//
//	/**
//	 * 初始化数据
//	 * @return 数据
//	 */
//	private static List<Student> getStudent() {
//		List<Student> list = new ArrayList<Student>();
//		Student student1 = new Student("小明", 8, "二年级");
//		Student student2 = new Student("小光", 9, "三年级");
//		Student student3 = new Student("小花", 10, "四年级");
//		list.add(student1);
//		list.add(student2);
//		list.add(student3);
//		return list;
//	}
//
//	
//	
//
//	/**
//	 * 读取Excel
//	 * 
//	 * @return 数据集合
//	 */
//	private static List<Student> readExcel() {
//		List<Student> list = new ArrayList<Student>();
//		HSSFWorkbook workbook = null;
//
//		try {
//			// 读取Excel文件
//			InputStream inputStream = new FileInputStream("D:/students.xls");
//			workbook = new HSSFWorkbook(inputStream);
//			inputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// 循环工作表
//		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
//			HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
//			if (hssfSheet == null) {
//				continue;
//			}
//			// 循环行
//			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//				if (hssfRow == null) {
//					continue;
//				}
//
//				// 将单元格中的内容存入集合
//				Student student = new Student();
//
//				HSSFCell cell = hssfRow.getCell(0);
//				if (cell == null) {
//					continue;
//				}
//				student.setName(cell.getStringCellValue());
//
//				cell = hssfRow.getCell(1);
//				if (cell == null) {
//					continue;
//				}
//				student.setAge((int) cell.getNumericCellValue());
//
//				cell = hssfRow.getCell(2);
//				if (cell == null) {
//					continue;
//				}
//				student.setGrade(cell.getStringCellValue());
//
//				list.add(student);
//			}
//		}
//		return list;
//	}
}
