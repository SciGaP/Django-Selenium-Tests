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
 * last modified 10/6/2020
 * 
 * this file runs all of the django portal tests
 * 
 */

class RunTests extends DjangoTest {
	 PrintStream console;
	 String local_path;
	 public int exceptions;

	@BeforeEach
	public
	void setUp() throws Exception {
		exceptions = 0;
		local_path = readConfigFile("local_path");
		
		//set output to file instead of command line
		setOutput("\\run_tests_output_", console);
	}

	@AfterEach
	public void tearDown() throws Exception {
		System.out.println("Exceptions Thrown: "+Integer.toString(exceptions));
		if (console!=null) {
		System.setOut(console);
		}
	}

	@Test
	public void test() throws Exception{
		System.out.println("Running All Django Portal Tests");

		//User Logout
		runTest(new UserLogout(), "User Logout");
		
		//Create New User
		runTest(new CreateNewUser(), "Create New User");

		//create a new GRP
		runTest(new GRPCreation(), "GRP Creation");
		
		//Edit existing GRP
		runTest(new EditGRP(), "Edit GRP");
		
		//Create a new Project
		runTest(new ProjectCreation(), "Project Creation");
		
		//create and test new application
		runTest(new CreateNewApplication(), "Create New Application");
		runTest(new RunNewApplication(), "Run New Application");
		
		//Edit an application
		runTest(new EditApplication(), "Edit Applicatoin");
		
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
		
		System.out.println("All Django Portal Tests Complete");
	}
	
	public void runTest(DjangoTest test, String testName) throws Exception {
		System.out.println("Starting "+testName);
		try {
			test.setUp();
			test.test();
			test.tearDown();
		}catch (Exception e) {
			e.printStackTrace();
			exceptions++;
		}
        System.out.println(testName+" Done");
	}
	
	public void setOutput(String filename, PrintStream console) throws Exception {
		//if directory doesn't exist, create a new one
		
		String path = local_path+"\\TestLogs";
		String dir = "\\"+currentDateAsString();
		
		//create the test log directory if it doesn't already exist
		File file = new File(path);
		
		//create a new directory based on the date if one hasn;t already been created
		file = new File(path+dir);
		
		//create the outputfile
		filename = path+dir+"\\"+filename+"_"+getRandomString(10)+".log";
		System.out.println(filename);
		file = new File(filename);
		if (!file.createNewFile()) {
			throw new Exception("output file not created");
		}

		//set system print output to file
		PrintStream testOutput = new PrintStream(new File(filename));
		console = System.out;//save the console 
		System.setOut(testOutput);		
	}
	
	//returns a string of random letters and numbers
	public String getRandomString(int len) {
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