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
 *Amber Sander Comet Class 
 * 
 * created on 8/21/2020 by Anna
 * last modifies 8/28/2020 by Anna
 * 
 * the Amber Sander experiment run on the Comet resource
 * 
 */

class AmberSanderComet extends ExperimentRunner{
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
		//Amber Sander Comet
		
		//driver, application path, experiment name, project, GRP, compute resource, queue, input files...
		//Amber Sander Files: 02_Heat.rst, 03_Prod.in,  prmtop
		runExperiment(driver, 
				By.xpath("//h2[contains(text(), 'Amber_Sander')]"),
				"Amber Sander Comet",
				"Default",
				"comet.sdsc.edu",
				"compute",
				PropertiesLoader.AMBER_SANDER_DIR,
				PropertiesLoader.AMBER_SANDER_INPUT1,
				PropertiesLoader.AMBER_SANDER_INPUT2,  
				PropertiesLoader.AMBER_SANDER_INPUT3);
	}


}
