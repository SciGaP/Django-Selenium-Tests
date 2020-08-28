import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import junit.framework.Assert;
import utils.ExperimentRunner;
import utils.PropertiesLoader;

/*
 * Abinit Comet Class
 * 
 * created on 8/21/2020 by Anna
 * last modifies 8/28/2020 by Anna
 * 
 * the Abinit experiment run on the Comet resource
 * 
 */


class AbinitComet  extends ExperimentRunner{
	WebDriver driver;
	
	@BeforeEach
	public void setUp() throws Exception {
		driver = setDriver();
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
	}

//	@SuppressWarnings("deprecation")
	@Test
	public void test() throws Exception {
		
		//driver, application path, experiment name, project, GRP, compute resource, queue, input files...
		runExperiment(driver, 
				By.xpath("//h2[contains(text(), 'Abinit')]"), 
				"Abinit Comet", 
				"Default",
				"comet.sdsc.edu",
				"compute",
				PropertiesLoader.ABINIT_DIR,
				PropertiesLoader.ABINIT_INPUT1,
				PropertiesLoader.ABINIT_INPUT2,  
				PropertiesLoader.ABINIT_INPUT3);
		addWait(200);
	}
	
}
