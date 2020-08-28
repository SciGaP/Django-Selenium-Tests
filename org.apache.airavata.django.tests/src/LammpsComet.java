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
 * Lammps Comet Class
 * 
 * created on 8/24/2020 by Anna
 * last modifies 8/28/2020 by Anna
 * 
 * the Lammps experiment run on the Comet resource
 * 
 */


class LammpsComet extends ExperimentRunner{
	private WebDriver driver;

	@BeforeEach
	public void setUp() throws Exception {
		driver = setDriver();
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws Exception{
		//driver, application path, experiment name, project, GRP, compute resource, queue, input files...
		runExperiment(driver, 
				By.xpath("//h2[contains(text(), 'Lammps') and not(contains(text(), 'BR2')	) and not(contains(text(), 'Test'))and not(contains(text(), 'App'))]"),
				"Lammps Comet",  
				"Default", 
				"comet.sdsc.edu",  
				"compute",
				PropertiesLoader.LAMMPS_DIR,
				PropertiesLoader.LAMMPS_INPUT);
	}

}
