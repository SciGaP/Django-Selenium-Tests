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
import utils.ExperimentRunner;
import utils.PropertiesLoader;

/*
 *Run New Application Class 
 * 
 * created on 9/2/2020
 * last modified 9/15/2020
 * 
 * class to test newly created application on django portal
*/


class RunNewApplication extends ExperimentRunner {
	WebDriver driver;
	String app_name;
	
	@BeforeEach
	public
	void setUp() throws Exception {
		driver = setDriver();
		app_name = readConfigFile("app_name");
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() {
		login(driver);
		By by = By.xpath("//h2[contains(text(), '"+app_name+"')]");
		
		//search for new application
		if (doesElementExist(driver, by)) {
			//application exists
			try {
				//driver, application path, experiment name, compute resource, queue, input files...
				runExperiment(driver, 
						by, 
						app_name+" Comet", 
						"comet.sdsc.edu",
						"compute",
						PropertiesLoader.GAUSSIAN_DIR,
						PropertiesLoader.GAUSSIAN_INPUT);
				addWait(200);
				}catch (Exception e) {
					System.out.println(e.toString());
					fail(e.toString());
				}
		}
		//application doesn't exist
		
	}

}
