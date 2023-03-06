package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil 
{
public static Properties conprop;
public static String getValueForKey(String key) throws Throwable
{
	conprop = new Properties();
	conprop.load(new FileInputStream("C:\\Users\\tejas\\eclipse-workspace\\StockAccounting_ERP\\Properties\\Environment.properties"));
	return conprop.getProperty(key);


}	
	
}
