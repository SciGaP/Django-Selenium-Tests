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
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 *Load Test class 
 * 
 * created on 8/27/2020
 * last modified 9/18/2020
 * 
 * java class for load testing of django portal
 * 
 */

class LoadTest extends RunTests{
	int load_test_iterations;
	String local_path;
	PrintStream console;

	@BeforeEach
	public void setUp() throws Exception{
		exceptions = 0;
		//get variables from properties file
		local_path = readConfigFile("local_path");
		load_test_iterations = Integer.parseInt(readConfigFile("load_test_iterations"));
		
		//set output to file instead of command line
		setOutput("\\load_test_outputs_", console);
	}

	@AfterEach
	public void tearDown() throws Exception {
		System.out.println("Exceptions Thrown: "+Integer.toString(exceptions));
		if (console!=null) {
			System.setOut(console);
			}
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
			runTest(new PhastaStampede2(), "Phasta Stampede2");
			//QChem Comet
			runTest(new QChemComet(), "QChem Comet");
			//Vina Multiple Comet
			runTest(new VinaMultipleComet(), "Vina Multiple Comet");
		}
		System.out.println("Load Test Complete");
	}
	
}
