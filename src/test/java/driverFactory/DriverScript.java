package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFuctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript 
{
	public static WebDriver driver;
	String inputpath = "C:\\Users\\tejas\\git\\Stock_Hybrid\\StockAccounting_ERP\\FileInput\\DataEngine.xlsx";
	String outputpath = "C:\\Users\\tejas\\git\\Stock_Hybrid\\StockAccounting_ERP\\FileInput\\HybridResults.xlsx";
	ExtentReports reports;
	ExtentTest test;
	String Description;
	public void startTest() throws Throwable
	{
		String Modulestatus="";
		//Create reference object
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//iterate all rows in master test cases sheet
		for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
		{
			if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("y"))
			{
				//store corresponding sheet into TCmodule variable
				String TCmodule = xl.getCellData("MasterTestCases", i, 1);
				reports =  new ExtentReports("./Extentreports/"+TCmodule+FunctionLibrary.generateDate()+".html");
				//iterate all rows in TCmodule sheet
				for(int j=1; j<=xl.rowCount(TCmodule);j++)
				{
					Description = xl.getCellData(TCmodule, j, 0);
					String Object_Type = xl.getCellData(TCmodule, j, 1);
					String Locator_Type = xl.getCellData(TCmodule, j, 2);
					String Locator_Value = xl.getCellData(TCmodule, j, 3);
					String TestData = xl.getCellData(TCmodule, j, 4);
					
					test = reports.startTest(TCmodule);
					test.assignAuthor("Sainath");
					test.assignCategory("Functional");
					
					try {
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser();
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("openApplication")) 
						{
							FunctionLibrary.openApplication(driver);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("typeAction")) 
						{
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("clickAction")) 
						{
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("validateTitle")) 
						{
							FunctionLibrary.validateTitle(driver, TestData);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("closeBrowser")) 
						{
							FunctionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("selectAction")) 
						{
							FunctionLibrary.selectAction(driver, Locator_Type, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("mouseClick")) 
						{
							FunctionLibrary.mouseClick(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("mouseDrag"))
						{
							FunctionLibrary.mouseDrag(driver, Locator_Value);
							test.log(LogStatus.INFO, Description);
						
						}else if (Object_Type.equalsIgnoreCase("mouseDbclick")) 
						{
							FunctionLibrary.mouseDbclick(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("tableData")) 
						{
							FunctionLibrary.tableData(driver, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("captureData")) 
						{
							FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
							xl.writeData(TCmodule, j, 4, TestData, outputpath);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("stockTable")) 
						{
							FunctionLibrary.stockTable(driver, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("searchData")) 
						{
							FunctionLibrary.searchData(driver, TestData);
							test.log(LogStatus.INFO, Description);
						}else if (Object_Type.equalsIgnoreCase("deleteRecord")) 
						{
							FunctionLibrary.deleteRecord(driver, TestData);
							test.log(LogStatus.INFO, Description);
						}
						//write as pass in status cell
						xl.setCellData(TCmodule, j, 5, "pass", outputpath);
						test.log(LogStatus.PASS, Description);
						Modulestatus="True";
					}catch (Exception e) 
					{
						System.out.println(e.getMessage());
						xl.setCellData(TCmodule, j, 5, "fail", outputpath);
						test.log(LogStatus.FAIL, Description);
						Modulestatus = "False";
						File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						File desfile = new File("./Screenshots/"+Description+"_"+FunctionLibrary.generateDate()+".jpg");
						FileUtils.copyFile(srcfile, desfile);
						String image = test.addScreenCapture("./Screenshots/"+Description+"_"+FunctionLibrary.generateDate()+".jpg");
						test.log(LogStatus.FAIL, image);
						break;
					}catch (AssertionError e) 
					{
						System.out.println(e.getMessage());
						xl.setCellData(TCmodule, j, 5, "fail", outputpath);
						test.log(LogStatus.FAIL, Description);
						Modulestatus = "False";
						File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						File desfile = new File("./Screenshots/"+Description+"_"+FunctionLibrary.generateDate()+".jpg");
						FileUtils.copyFile(srcfile, desfile);
						String image = test.addScreenCapture("./Screenshots/"+Description+"_"+FunctionLibrary.generateDate()+".jpg");
						test.log(LogStatus.FAIL, image);
						break;
					}
				}
				if (Modulestatus.equalsIgnoreCase("True")) 
				{
					xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
				}else
				{
					xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
				}
					
				reports.endTest(test);
				reports.flush();				
			}
			else 
			{
				//which test case flag to N write as Blocked into status cell
				xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
			}  
	
		}
	}


}
   