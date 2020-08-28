package utils;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

/*
 * DjangoTest Class
 * 
 * created on 8/14/2020 by Anna
 * last modifies 8/28/2020 by Anna
 * 
 * the parent class of Django portal tests
 * 
 * 
 */

public abstract class DjangoTest {
	
	//function that logs into the django portal
	public void login (WebDriver driver) {
		String username, password;
		try {
			//get values from properties file
			username = readConfigFile("django_username");
			password = readConfigFile("django_password");
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		//check that none of the values are null
		if (username == null) {
			throw new RuntimeException("Django username is not in properties file");
		}
		else if (password == null) {
			throw new RuntimeException("Django password is not in properties file");
		}
		//trim any whitespace from properties file strings
		username = username.trim();
		password = password.trim();
		
		
		driver.findElement(By.linkText("Log in")).click();//go to login screen
		
		//enter username and password
		addWait(500);
	    driver.findElement(By.id("username")).sendKeys(username);//enter username
	    addWait(500);
	    driver.findElement(By.id("password")).sendKeys(password);//enter password
	    addWait(500);
	    
	    //click submit button
	    driver.findElement(By.cssSelector(".btn:nth-child(4)")).click();
	}

	//Function that reads properties from config file
	public String readConfigFile(String propField) throws Exception{	
		return PropertiesLoader.readConfigFile(propField);
	}
	
	//Function that adds wait time
	public void addWait(int i) {
		try {
			Thread.sleep(i);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}		
	}
	
	//function that scrolls the visible range to the specified element
	public void scrollToElement(WebElement element, WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", element);
	}
	
	//function that attempts to click on the specified element
	public void attemptClick(WebElement element, WebDriver driver) throws Exception {
		int time = 0;
		while (true) {
			try {
				scrollToElement(element, driver);
				element.click();
				break;
			}catch (ElementClickInterceptedException e) {
				if (time>100) {
					throw new Exception (e);
				}
				time++;
				addWait(300);
				continue;
			}
		}
	}
	
	//returns current date and time as a string
	public String currentTimeAsString() {
		Calendar calendar = Calendar.getInstance();
		int min = calendar.get(Calendar.MINUTE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);;
		return Integer.toString(month)+"/"+Integer.toString(day)+" "+Integer.toString(hour)+":"+Integer.toString(min);
	}
	
	//check if element exists
	public Boolean doesElementExist(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		}catch(NoSuchElementException e) {
			return false;
		}
	}
	

	//function that reads the specified WebDriver type from the properties file and sets the driver to that type
	public WebDriver setDriver() throws Exception{
		WebDriver driver;
		String default_driver, local_path;
		//get variables from properties file
		try {
			default_driver = readConfigFile("default_driver");
			local_path = readConfigFile("local_path");
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		//check for null values
		if (default_driver == null) {
			throw new RuntimeException("default driver is not specified in properties file");
		}
		if (local_path == null) {
			throw new RuntimeException("local path is not specified in properties file");
		}
		default_driver = default_driver.trim();
		default_driver= default_driver.toLowerCase();
		
		if (default_driver.contentEquals("chrome")) {
			//System.setProperty("webdriver.chrome.driver", local_path+"\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if (default_driver.contentEquals("firefox")) {
			//System.setProperty("webdriver.gecko.driver", local_path+"\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else {
			throw new RuntimeException("default driver specified in properties file is not recognised");
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}


	//junit test functions
	public void setUp(WebDriver driver, Boolean clone, Boolean cancel) throws Exception{}
	public void setUp() throws  Exception{}
	public void tearDown() throws Exception{}
	public void test() throws Exception{}
}