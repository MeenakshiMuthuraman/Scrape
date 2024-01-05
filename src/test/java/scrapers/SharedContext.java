package scrapers;


import org.openqa.selenium.WebElement;

import com.qa.util.*;

import java.util.List;


public class SharedContext {

    private static List<WebElement> recipeTitles;
    private ExcelWriter excelWriter  = new ExcelWriter();
    private ExcelReader excelreader =new ExcelReader();
    private ConfigReader config;
    public SharedContext() {
        config = new ConfigReader();
        config.init_prop(); 
    }
    public static List<WebElement> getRecipeTitles() {
        return recipeTitles;
    }

    public static void setRecipeTitles(List<WebElement> recipeTitles) {
        SharedContext.recipeTitles = recipeTitles;
    }
    public void writeToExcel(String title) {
        String baseFilePath = config.getFilePathBase();
        String filename =config.getFileName();
        String excelpath = System.getProperty("user.dir") + "/" + baseFilePath + filename;
        String sheetname = config.getSheetName();
        int colno = 0;

        try {
        	
            int currentRow = excelreader.getCurrentRow(excelpath, sheetname);
            excelWriter.writeCellValue(excelpath, sheetname, currentRow, colno, title);
            excelWriter.updateRowNumber(excelpath, sheetname, currentRow + 1);
            
        	} catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}