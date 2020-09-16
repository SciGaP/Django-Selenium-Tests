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
 *Auto Dock Vina Stampede2 Class 
 * 
 * created on 8/24/2020
 * last modified 9/15/2020
 * 
 * the Auto Dock Vina experiment run on the Stampede2 resource
 * 
 */

class AutoDockVinaStampede2 extends ExperimentRunner{
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
		//Auto Dock Vina Stampede 2
		
		//driver, application path, experiment name, compute resource, queue, input files...
		runExperiment(driver, 
				By.xpath("//h2[contains(text(), 'AutoDock_Vina')]"),
				"Auto Dock Vina Stampede2",  
				"stampede2.tacc.xsede.org",  
				"normal",
				PropertiesLoader.AUTODOCK_VINA_DIR,
				PropertiesLoader.AUTODOCK_VINA_INPUT1, 
				PropertiesLoader.AUTODOCK_VINA_INPUT2,  
				PropertiesLoader.AUTODOCK_VINA_INPUT3);
	}
}
