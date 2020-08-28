import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.ExperimentRunner;
import utils.PropertiesLoader;

/*
 * Gaussian 16 Carbonate Class
 * 
 * created on 8/27/2020 by Anna
 * last modifies 8/28/2020 by Anna
 * 
 * the Gaussian 16 experiment run on the carbonate resource
 * 
 */

class Gaussian16Carbonate extends ExperimentRunner {
	WebDriver driver;
	
	@BeforeEach
	public
	void setUp() throws Exception {
		driver = setDriver();
	}

	@AfterEach
	public
	void tearDown() throws Exception {
		
	}

	@Test
	public
	void test() throws Exception {
		//driver, application path, experiment name, project, GRP, compute resource, queue, input files...
		runExperiment(driver, 
				By.xpath("//h2[contains(text(), 'Gaussian16')]"),
				"Gaussian 16 Carbonate",  
				"Default", 
				"carbonate.uits.iu.edu",  
				"debug",
				PropertiesLoader.GAUSSIAN_DIR,
				PropertiesLoader.GAUSSIAN_INPUT);
	}
}
