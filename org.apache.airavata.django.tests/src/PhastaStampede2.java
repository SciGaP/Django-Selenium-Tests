import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.ExperimentRunner;
import utils.PropertiesLoader;

/*
 *Phasta Stampede2 Class 
 * 
 * created on 8/27/2020 by Anna
 * last modifies 8/28/2020 by Anna
 * 
 * the Phasta experiment run on the Stampede2 resource
 * 
 */

class PhastaStampede2  extends ExperimentRunner{
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
		WebElement element;
		
		//driver, application path, experiment name, project, GRP, compute resource, queue, input files...
		runExperiment(driver,
				By.xpath("//h2[contains(text(), 'Phasta')]"),
				"Phasta Stampede2",
				"Default", 
				"stampede2.tacc.xsede.org",  
				"normal",
				PropertiesLoader.AUTODOCK_VINA_DIR, //TODO: change to Phasta files
				PropertiesLoader.AUTODOCK_VINA_INPUT1, 
				PropertiesLoader.AUTODOCK_VINA_INPUT2,  
				PropertiesLoader.AUTODOCK_VINA_INPUT3);
	}
}
