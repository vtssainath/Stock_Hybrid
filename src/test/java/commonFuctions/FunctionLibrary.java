package commonFuctions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;

public class FunctionLibrary
{
	public static WebDriver driver;
	public static String Expected="";
	public static String Actual="";

	public static WebDriver startBrowser() throws Throwable 
	{
		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")) 
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) 
		{
			driver = new FirefoxDriver();
			driver.manage().window().maximize();	
		}else 
		{
			System.out.println("Browser Value Not Matching");
		}
		return driver;
	}

	public static void openApplication(WebDriver driver) throws Throwable 
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}	

	public static void waitForElement(WebDriver driver,String Locator_Type, String Locator_value,String TestData) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(TestData));
		if (Locator_Type.equalsIgnoreCase("name")) 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_value)));
		}else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_value)));
		}else if (Locator_Type.equalsIgnoreCase("id")) 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Type)));
		}

	}

	public static void typeAction(WebDriver driver,String Locator_Type, String Locator_value,String TestData) 
	{
		if (Locator_Type.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(Locator_value)).clear();
			driver.findElement(By.name(Locator_value)).sendKeys(TestData);
		}else if (Locator_Type.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(Locator_value)).clear();
			driver.findElement(By.id(Locator_value)).sendKeys(TestData);
		}else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(Locator_value)).clear();
			driver.findElement(By.xpath(Locator_value)).sendKeys(TestData);
		}
	}

	public static void clickAction(WebDriver driver,String Locator_Type, String Locator_value)
	{
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Type)).click();
		}else if (Locator_Type.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(Locator_value)).sendKeys(Keys.ENTER);
		}else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(Locator_value)).click();
		}	
	}

	public static void validateTitle(WebDriver driver, String Exp_Title) 
	{
		String Act_Title = driver.getTitle();
		try {
			Assert.assertEquals(Exp_Title, Act_Title,"Title is Not Matching");
		}catch (Throwable t) 
		{
			System.out.println(t.getMessage());
		}
	}

	public static void closeBrowser(WebDriver driver) 
	{
		driver.close();
	}

	public static void selectAction(WebDriver driver,String Locator_Type, String Locator_value,String TestData) throws Throwable 
	{
		if (Locator_Type.equalsIgnoreCase("name")) 
		{ 
			WebElement element = driver.findElement(By.name(Locator_value));
			Thread.sleep(3000);
			Select catagory = new Select(element);
			catagory.selectByVisibleText(TestData);	
		}else if (Locator_Type.equalsIgnoreCase("id")) 
		{
			WebElement element = driver.findElement(By.id(Locator_value));
			Thread.sleep(3000);
			Select catagory = new Select(element);
			catagory.selectByVisibleText(TestData);	
		}else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			WebElement element = driver.findElement(By.xpath(Locator_value));
			Thread.sleep(3000);
			Select catagory = new Select(element);
			catagory.selectByVisibleText(TestData);	
		}
	}
	public static void mouseDrag(WebDriver driver,String Locator_Value) throws Throwable 
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath(Locator_Value))).perform();
		Thread.sleep(3000);
	}
	public static void mouseClick(WebDriver driver,String Locator_Type,String Locator_Value)
	{
		if (Locator_Type.equalsIgnoreCase("name")) 
		{
			Actions ac = new Actions(driver);
			ac.moveToElement(driver.findElement(By.name(Locator_Value))).click().perform();
		}else if (Locator_Type.equalsIgnoreCase("id")) 
		{
			Actions ac = new Actions(driver);
			ac.moveToElement(driver.findElement(By.id(Locator_Value))).click().perform();	
		}else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			Actions ac = new Actions(driver);
			ac.moveToElement(driver.findElement(By.xpath(Locator_Value))).click().perform();
		}
		
	}

	public static void tableData(WebDriver driver,String Locator_Value) throws Throwable 
	{
		if (!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed());
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Expected);
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
			Thread.sleep(3000);
			Actual= driver.findElement(By.xpath(Locator_Value)).getText();
			System.out.println(Expected+"   "+Actual);
			Assert.assertEquals(Expected, Actual,"Data not found in table");
				
	}
	public static void stockTable(WebDriver driver,String Locator_Value,String TestData) throws Throwable 
	{
		if (!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed());
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(TestData);
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
			Thread.sleep(3000);
			String ActualData = driver.findElement(By.xpath(Locator_Value)).getText();
			System.out.println(TestData+"   "+ActualData);
			Assert.assertEquals(ActualData,TestData,"Data Not Found in Table");
				
	}
	public static void captureData(WebDriver driver,String Locator_Type,String Locator_Value) throws Throwable 
	{
		if (Locator_Type.equalsIgnoreCase("id"))
		{
			Expected = driver.findElement(By.id(Locator_Value)).getAttribute("value");	
		}else if (Locator_Type.equalsIgnoreCase("name")) 
		{
			Expected = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			Expected = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
	}
	public static void mouseDbclick(WebDriver driver,String Locator_Type,String Locator_Value)
	{
		if (Locator_Type.equalsIgnoreCase("name")) 
		{
			Actions ac = new Actions(driver);
			ac.moveToElement(driver.findElement(By.name(Locator_Value))).doubleClick().perform();
		}else if (Locator_Type.equalsIgnoreCase("id")) 
		{
			Actions ac = new Actions(driver);
			ac.moveToElement(driver.findElement(By.id(Locator_Value))).doubleClick().perform();	
		}else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			Actions ac = new Actions(driver);
			ac.moveToElement(driver.findElement(By.xpath(Locator_Value))).doubleClick().perform();
		}
		
	}
	public static void deleteRecord(WebDriver driver,String TestData) throws Throwable 
	{
		try {
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("check-box"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Delete-Records"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Ok-Confirm"))).sendKeys(Keys.ENTER);
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(TestData));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PropertyFileUtil.getValueForKey("Ok-Alert"))));
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Ok-Alert"))).sendKeys(Keys.ENTER);
			driver.navigate().back();
			WebDriverWait wait1 = new WebDriverWait(driver, Integer.parseInt(TestData));
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PropertyFileUtil.getValueForKey("Ok-Alert"))));
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Ok-Alert"))).sendKeys(Keys.ENTER);
			driver.navigate().back();	
		} catch (Exception e)
		{
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(TestData));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PropertyFileUtil.getValueForKey("Ok-Alert"))));
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Ok-Alert"))).sendKeys(Keys.ENTER);
			driver.navigate().back();	
		}	
	}
	public static void searchData(WebDriver driver,String TestData) throws Throwable 
	{
		if (!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed());
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(TestData);
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(3000);
	}
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_DD");
		return df.format(date);
		
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
}
