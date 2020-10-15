import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
 * Edit GRP Class
 * 
 * created on 9/18/2020
 * last modified 10/2/2020
 * 
 * Class to test editing a Group Resource Profile
 * 
 */

class EditGRP extends GRPCreation {
	WebDriver driver;

	@BeforeEach
	public void setUp() throws Exception {
		driver = setDriver();
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws Exception {
		String edit_grp = readConfigFile("edit_grp");
		
		//go to the django portal
		driver.get(readConfigFile("start_url"));
				
		//login
		login(driver);
		addWait(200);
		
		//go to settings
		attemptClick(driver.findElement(By.id("dropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.id("appDropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.cssSelector(".fa-cog")), driver);
	    
	    //go to GRP
	    attemptClick(By.linkText("Group Resource Profile"), driver);
	    int index = -1;
	    
	    //determine the correct grp to edit
	    for (int i =0; i<driver.findElements(By.xpath("//td[contains(text(), '')]")).size(); i++ ) {
	    	if (driver.findElements(By.xpath("//td[contains(text(), '')]")).get(i).getText().equals(edit_grp)) {
	    		index = i;
	    		break;
	    	}
	    	
	    }
	    if (index == -1) {
	    	throw new Exception("specified GRP doesn't exist");
	    }
	    
	    //click on edit GRP
	    attemptClick(driver.findElements(By.xpath("//a[contains(text(), 'Edit')]")).get(index/3), driver);
	    addWait(300);

	    //edit existing compute resource
	    attemptClick(By.xpath("//a[contains(text(), 'Edit')]"), driver);
	    addWait(300);
	    
	    //add reservation
	    addReservation(driver);
	    
	    //save compute resource
	    attemptClick(By.xpath("//button[contains(text(), 'Save')]"), driver);
	    addWait(200);
	    
	    //add stampede2
	    addComputeResource(driver, "stampede2.tacc.xsede.org", "ccguser", "TG-CHE080068N", 
	    		"/scratch/00421/ccguser/automated_test_workdirs");
	    addWait(200);
	    	    
	    //share
	    shareExperiment(driver, 1);
	    addWait(200);
	    
	    //save
	    attemptClick(By.xpath("//button[contains(text(), 'Save')]"), driver);
	    addWait(200);
	}

}
