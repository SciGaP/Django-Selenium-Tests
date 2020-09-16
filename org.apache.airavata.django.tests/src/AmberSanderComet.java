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
 * created on 8/21/2020
 * last modified 9/15/2020
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
				"comet.sdsc.edu",
				"compute",
				PropertiesLoader.AMBER_SANDER_DIR,
				PropertiesLoader.AMBER_SANDER_INPUT1,
				PropertiesLoader.AMBER_SANDER_INPUT2,  
				PropertiesLoader.AMBER_SANDER_INPUT3);
	}


}
