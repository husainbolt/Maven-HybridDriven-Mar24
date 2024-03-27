package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

public class ExcelOperations {
	

	public static Object[][] readExcel(String filePath, String sheetName) throws IOException {
		
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet(sheetName);
		int totalRowCount = sheet.getLastRowNum();
		int totalColCount = sheet.getRow(0).getLastCellNum();
		Object[][] data = new Object[totalRowCount][totalColCount];
		for(int i = 0; i< totalRowCount; i++) {
			for(int j = 0; j < totalColCount; j++) {
				Cell cell = sheet.getRow(i+1).getCell(j);
				if(cell.getCellType()==CellType.STRING)
					data[i][j]=cell.getStringCellValue();
				else if(cell.getCellType()==CellType.BOOLEAN)
					data[i][j]=cell.getBooleanCellValue();
				else if(cell.getCellType()==CellType.NUMERIC) {
					String temp = String.valueOf(cell.getNumericCellValue());
					data[i][j] = temp.substring(0, temp.length()-2);
				}
			}
		}
		return data;
	}

}
