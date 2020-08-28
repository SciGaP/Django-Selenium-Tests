import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.DjangoTest;

/*
 *User Logout Class 
 * 
 * created on 8/26/2020 by Anna
 * last modifies 8/27/2020 by Anna
 * 
 * class to test user logout on django portal
*/

class UserLogout   extends DjangoTest{
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
		
		//login
		login(driver);
		addWait(200);
		
		//logout
		element = driver.findElement(By.id("dropdownMenuButton"));
	    attemptClick(element, driver);
	    addWait(200);
	    attemptClick(driver.findElement(By.linkText("Logout")), driver);
	    addWait(200);
	    
	    //confirm logout
	    Assert.assertTrue(doesElementExist(driver, By.linkText("Log in")));
	    addWait(200);
	}

}
