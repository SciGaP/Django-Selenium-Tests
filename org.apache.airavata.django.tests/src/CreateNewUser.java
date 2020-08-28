import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.DjangoTest;

/*
 * create new user class
 * 
 * created on 8/27/2020 by Anna
 * last modifies 8/27/2020 by Anna
 * 
 * Test creating a new user function
 */


class CreateNewUser extends DjangoTest {
	WebDriver driver;
	String start_url;

	@BeforeEach
	public void setUp() throws Exception {
		driver = setDriver();
		start_url = readConfigFile("start_url");
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws Exception {
		WebElement element; 
		//go to the django portal
		driver.get(start_url);
		
		String new_username = readConfigFile("new_username");
		String new_password = readConfigFile("new_password");
		String new_email = readConfigFile("new_email");
		String first_name = readConfigFile("first_name");
		String last_name = readConfigFile("last_name");
		String fail_password = readConfigFile("fail_password");
		
		//click on create new user
		attemptClick(driver.findElement(By.linkText("Create Account")), driver);
		addWait(200);
		
		//Enter new username
		element = driver.findElement(By.id("id_username"));
		scrollToElement(element, driver);
		element.clear();
	    element.sendKeys(new_username);
	    addWait(200);

	    //Enter bad password
	    element = driver.findElement(By.id("id_password"));
		scrollToElement(element, driver);
		element.clear();
		element.sendKeys(new_password);
	    addWait(200);
	    
	    //Enter bad Password Again
	    element = driver.findElement(By.id("id_password_again"));
		scrollToElement(element, driver);
		element.clear();
		element.sendKeys(new_password);
	    addWait(200);
	    
	    //enter email
	    element = driver.findElement(By.id("id_email"));
	    scrollToElement(element, driver);
	    element.sendKeys(new_email);
	    addWait(200);
	    
	    //enter email again
	    element = driver.findElement(By.id("id_email_again"));
	    scrollToElement(element, driver);
	    element.sendKeys(new_email);
	    addWait(200);
	    
	    //enter first name
	    element = driver.findElement(By.id("id_first_name"));
	    element.sendKeys(first_name);
	    scrollToElement(element, driver);
	    addWait(200);
	    
	    //enter last name
	    element=driver.findElement(By.id("id_last_name"));
	    scrollToElement(element, driver);
	    element.sendKeys(last_name);
	    addWait(200);
	    
	    //Click Create
	    element = driver.findElement(By.xpath("//button[contains(text(), 'Create')]"));
	    scrollToElement(element, driver);
	    //attemptClick(element, driver);
	    addWait(200);
	    
	}

}
