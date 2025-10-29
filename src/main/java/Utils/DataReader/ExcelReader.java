package Utils.DataReader;

import Utils.Logs.LogsManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    private static final String Test_Data_path = "src/test/resources/test-data/";

    public static String getExcelData(String excelFileName, String sheetName, int rowNum, int colNum) {
        XSSFWorkbook workbook;
        XSSFSheet sheet;
        String cellData;

        try {
            workbook = new XSSFWorkbook(Test_Data_path + excelFileName);
            sheet = workbook.getSheet(sheetName);
            cellData = sheet.getRow(rowNum-1).getCell(colNum).getStringCellValue();
            return cellData;
        } catch (Exception e) {
            LogsManager.error("Error in reading excel file" + e.getMessage());
            return "";
        }
    }
}
