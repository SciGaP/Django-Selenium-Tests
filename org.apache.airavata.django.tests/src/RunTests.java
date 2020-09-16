/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.PrintStream;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DjangoTest;

/*
 * RunTests Class
 * 
 * created on 8/17/2020
 * last modified 9/16/2020
 * 
 * this file runs all of the django portal tests
 * 
 */

class RunTests extends DjangoTest {
	 PrintStream runTestsOutput, console;
	 String local_path, filename, directory;

	@BeforeEach
	public
	void setUp() throws Exception {
		local_path = readConfigFile("local_path");
		setOutput();//set output to file instead of command line
	}

	@AfterEach
	public void tearDown() throws Exception {
		if (console!=null) {
		System.setOut(console);
		}
	}

	@Test
	public void test() throws Exception{
		System.out.println("Running All Django Portal Tests");

		//User Logout
		runTest(new UserLogout(), "User Logout");
		
		//Create User
		runTest(new CreateNewUser(), "Create New User");
		
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
		runTest(new PhastaStampede2(), "Phasta Stampede2");
		//QChem Comet
		runTest(new QChemComet(), "QChem Comet");
		//Vina Multiple Comet
		runTest(new VinaMultipleComet(), "Vina Multiple Comet");
		
		//create and test new application
		runTest(new CreateNewApplication(), "Create New Application");
		runTest(new RunNewApplication(), "Run New Application");
		
		System.out.println("All Django Portal Tests Complete");
	}
	
	private void runTest(DjangoTest test, String testName) throws Exception {
		System.out.println("Starting "+testName);
		test.setUp();
		try {
			test.test();
		}catch (Exception e) {
			e.printStackTrace();
		}
        test.tearDown();
        System.out.println(testName+" Done");
	}
	
	private void setOutput() throws Exception {
		//if directory doesn't exist, create a new one
		
		String path = local_path+"\\TestLogs";
		String dir = "\\"+currentDateAsString();
		System.out.println(path);
			
		File file = new File(path);
		if(!file.mkdir()){
			//directory not created
		}
		file = new File(path+dir);
		if(!file.mkdir()){
			//directory not created
		}
		
		filename = path+dir+"\\run_tests_output_"+getRandomString(10)+".log";
		System.out.println(filename);
		file = new File(filename);
		if (!file.createNewFile()) {
			throw new Exception("output file not created");
		}

		//set system print output to file
		runTestsOutput = new PrintStream(new File(filename));
		console = System.out;//save the console 
		System.setOut(runTestsOutput);		
	}
	
	//returns a string of random letters and numbers
	private String getRandomString(int len) {
		Random r = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";//usable characters
        StringBuilder str = new StringBuilder();
        while (str.length() < len) { // length of the random string.
            int index = (int) (r.nextFloat() * chars.length());
            str.append(chars.charAt(index));
        }
        return str.toString();
    }
}