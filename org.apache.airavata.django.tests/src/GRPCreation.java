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
import java.util.Calendar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ExperimentRunner;

/*
 * GRP Creation Class
 * 
 * created on 9/16/2020
 * last modified 10/2/2020
 * 
 * Class to test the creation of a new Group Resource Profile
 * 
 */

class GRPCreation extends ExperimentRunner{
	WebDriver driver;
	
	@BeforeEach
	public void setUp() throws Exception{
		driver = setDriver();
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws Exception {
		WebElement element;
		String grp_name = readConfigFile("new_grp");
				
		//go to the django portal
		driver.get(readConfigFile("start_url"));
				
		//login
		login(driver);
		addWait(200);
		
		//go to settings
		attemptClick(driver.findElement(By.id("dropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.id("appDropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.cssSelector(".fa-cog")), driver);
	    
	    //go to GRP
	    attemptClick(By.linkText("Group Resource Profile"), driver);
	    
	    //click new GRP
	    attemptClick(By.xpath("//button[contains(text(), 'New Group Resource Profile')]"), driver);
	    
	    //set GRP name
	    driver.findElement(By.id("profile-name")).sendKeys(grp_name);
	    
	    //set SSH Credential
	    element = driver.findElement(By.xpath("//select[contains(text(), '')]"));
	    attemptClick(element.findElement(By.xpath("//option[. = 'deveroma2017 - New Gateway Key']")), driver);
	    	    
	    //add comet
	    addComputeResource(driver, "comet.sdsc.edu", "gridchem", "TG-CHE080068N", 
	    		"/oasis/scratch/comet/gridchem/temp_project/automated_test_workdirs");
	    addWait(200);
	    
	    //add stampede2
	    addComputeResource(driver, "stampede2.tacc.xsede.org", "ccguser", "TG-CHE080068N", 
	    		"/scratch/00421/ccguser/automated_test_workdirs");
	    addWait(200);
	    
	    //add bridges
	    addComputeResource(driver, "bridges.psc.edu", "gcommuni", "ch87lmp", 
	    		"/home/gcommuni/scratch/automated_test_workdirs");
	    addWait(200);
	    
	    //share
	    shareExperiment(driver, 1);
	    addWait(200);
	    
	    //save
	    attemptClick(By.xpath("//button[contains(text(), 'Save')]"), driver);
	    addWait(200);

	    //make sure GRP exists
	    if (!this.doesElementExist(driver, By.xpath("//td[contains(text(), '"+grp_name+"')]"))) {
	    	throw new Exception("Grp not created");
	    }
	}
	
	public void addComputeResource(WebDriver driver, String computeResource, String username, String allocationNumber, String scratchLocation) throws Exception {
		WebElement element;
		
	    //Click New Compute Preference
	    attemptClick(By.xpath("//button[contains(text(), 'New Compute Preference')]"), driver);
		
	    //add compute resource
	    element = driver.findElements(By.xpath("//select[contains(text(), '')]")).get(1);
	    attemptClick(element.findElement(By.xpath("//option[. = '"+computeResource+"']")), driver);
	    attemptClick(By.xpath("//button[contains(text(), 'OK')]"), driver);
	    addWait(200);
	    
	    //set username
	    driver.findElement(By.id("login-username")).sendKeys(username);
	    addWait(200);

	    //Allocation Project Number
	    driver.findElement(By.id("allocation-number")).sendKeys(allocationNumber);
	    addWait(200);
	    
	    //Scratch Location
	    driver.findElement(By.id("scratch-location")).sendKeys(scratchLocation);
	    addWait(200);

	    //add reservation that should work
	    addReservation(driver);
	    
	    //save compute resource
	    attemptClick(By.xpath("//button[contains(text(), 'Save')]"), driver);
	    addWait(200);
	    
	}
	
	public void addReservation(WebDriver driver) throws Exception {
		//add new reservation
	    attemptClick(By.xpath("//button[contains(text(), 'New Reservation')]"), driver);
	    addWait(200);
	    
	    //add reservation name
	    driver.findElement(By.id("reservation-name")).sendKeys("Automated Reservation Test");
	    addWait(200);
	    
	    //edit time
	    attemptClick(driver.findElements(By.cssSelector(".vdatetime-input")).get(1), driver);//edit the reservation end time
	    
	    addWait(200);
	    
	    
	    
	    //set end time for next day
	    //int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1;
	    //System.out.println(Integer.toString(day));
	    //attemptClick(By.xpath("//span[contains(text(), '"+Integer.toString(day)+"')]"), driver);
	    attemptClick(By.xpath("//div[contains(text(), 'Continue')]"), driver);
	    attemptClick(By.xpath("//div[contains(text(), 'Continue')]"), driver);
	    addWait(200);
	    
	    //save reservation
	    attemptClick(By.xpath("//button[contains(text(), 'Add')]"), driver);
	    addWait(200);
	}
}
