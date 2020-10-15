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

/*
 * Project Creation Class
 * 
 * created on 9/16/2020
 * last modified 9/23/2020
 * 
 * Class to test the creation of a new Project
 * 
 */

class ProjectCreation extends ExperimentRunner{
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
		//go to the django portal
		driver.get(readConfigFile("start_url"));
		
		//login
		login(driver);
		addWait(200);
		
	    //go to projects
		attemptClick(By.cssSelector(".fa-box"), driver);
		
		//add new project
		attemptClick(By.cssSelector(".btn"), driver);
		
		String name = readConfigFile("new_project")+"_"+currentDateAsString()+"_"+currentTimeAsString();//project name with date and time added on the end
		//name project
	    driver.findElement(By.id("project-name")).sendKeys(name);
	    
		//add project description
	    driver.findElement(By.id("project-description")).sendKeys("This project was created using an automated test");
		
		//click ok
	    attemptClick(By.xpath("//button[contains(text(), 'Ok')]"), driver);
	    
	    //click on edit
	    attemptClick(By.xpath("//*[contains(text(), 'Edit')]"), driver);//edits the newest project
	    
	    //edit sharing
		shareExperiment(driver, 1);
	    
	    //save project
		attemptClick(By.xpath("//button[contains(text(), 'Save')]"), driver);
	    
		//make sure project is saved
		if (!doesElementExist(driver, By.xpath("//h1[contains(text(), 'Browse Projects')]"))){
			throw new Exception("Project didn't save");
		}	    
	}
}
