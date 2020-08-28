import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import utils.DjangoTest;

/*
 *Load Test class 
 * 
 * created on 8/27/2020 by Anna
 * last modifies 8/28/2020 by Anna
 * 
 * java class for load testing of django portal
 * 
 */

class LoadTest extends DjangoTest{
	WebDriver driver;
	int load_test_iterations;
	

	@BeforeEach
	public
	void setUp() throws Exception {
		//get variables from properties file
		try {
			load_test_iterations = Integer.parseInt(readConfigFile("load_test_iterations"));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		System.out.println("Starting Load Test");
		for (int i = 1; i<=load_test_iterations;i++) {
			System.out.println("Starting Iteration "+i);
			//Abinit Comet
			runTest(new AbinitComet(), "Abinit Comet");
			//Amber Sander Comet
			runTest(new AmberSanderComet(), "Amber Sander Comet");
			//Auto Dock Vina Comet
			runTest(new AutoDockVinaComet(), "AutoDock Vina Comet");
			//Auto Dock Vina Stampede2
			runTest(new AutoDockVinaStampede2(), "AutoDock Vina Stampede2");
			//CP2K Comet
			runTest(new CP2KComet(), "CP2K Comet");
			//CPMD Comet
			runTest(new CPMDComet(), "CPMD Comet");
			//DDSCat Comet
			runTest(new CPMDComet(), "CPMD Comet");
			//Echo Bridges
			runTest(new EchoBridges(), "Echo Bridges");				
			//Gamess Comet
			runTest(new GamessComet(), "Gamess Comet");
			//Gassuian Bridges
			runTest(new GaussianComet(), "Gaussian Bridges");
			//Gaussian Comet
			runTest(new GaussianComet(), "Gaussian Comet");
			//Gromacs Comet
			runTest(new GromacsComet(), "Gromacs Comet");
			//Lammps Comet
			runTest(new LammpsComet(), "Lammps Comet");
			//Phasta Stampede2
			//runTest(new PhastaStampede2(), "Phasta Stampede2");//TODO: find input files
			//QChem Comet
			runTest(new QChemComet(), "QChem Comet");
			//Vina Multiple Comet
			runTest(new VinaMultipleComet(), "Vina Multiple Comet");
		}
		System.out.println("Load Test Complete");
	}

	private void runTest(DjangoTest test, String testName) throws Exception {
		System.out.println("Starting "+testName);
		//setDriver();//reset driver
		test.setUp();
		try {
			test.test();
		}catch (Exception e) {
			e.printStackTrace();
			//throw new Exception(e);
		}
        test.tearDown();
        System.out.println(testName+" Done");
	}
}
