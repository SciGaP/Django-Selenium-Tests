import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.ExperimentRunner;
import utils.PropertiesLoader;

/*
 * Vina Multiple Comet Class
 * 
 * created on 8/27/2020 by Anna
 * last modifies 8/28/2020 by Anna
 * 
 * the Vina Multiple experiment run on the comet resource
 * 
 */


class VinaMultipleComet extends ExperimentRunner{
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
		
		//driver, application path, experiment name, project, GRP, compute resource, queue, input files...
		runExperiment(driver, 
				By.xpath("//h2[contains(text(), 'Vina_Multiple')]"),
				"Vina Multiple Comet",
				"Default",
				"comet.sdsc.edu",
				"compute",
				PropertiesLoader.VINA_MULTIPLE_DIR,
				PropertiesLoader.VINA_MULTIPLE_INPUT1,
				PropertiesLoader.VINA_MULTIPLE_INPUT2,
				PropertiesLoader.VINA_MULTIPLE_INPUT3);
	}
}
