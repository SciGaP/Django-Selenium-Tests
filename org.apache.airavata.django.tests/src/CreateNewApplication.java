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
import utils.DjangoTest;


/*
 *Create New Application Class 
 * 
 * created on 8/31/2020
 * last modified 9/16/2020
 * 
 * class to test application creation on django portal
*/

class CreateNewApplication extends DjangoTest{
	WebDriver driver;
	String start_url, app_name;
	
	@BeforeEach
	public void setUp() throws Exception {
		driver = setDriver();
		start_url = readConfigFile("start_url");
		app_name = readConfigFile("app_name");
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws Exception {
		WebElement element;
		
		//go to the django portal
		driver.get(start_url);
				
		//login
		login(driver);
		addWait(200);
		
		//create a gaussian application
		
		//go to settings
	    attemptClick(driver.findElement(By.id("dropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.id("appDropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.cssSelector(".fa-cog")), driver);
	    
		//click create new application
	    attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'New Application')]")), driver);
		
		//enter application name
	    driver.findElement(By.id("application-name")).sendKeys(app_name);

	    //click save
		attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'Save')]")), driver);
	    
		addWait(200);
		
		//click interface tab
	    attemptClick(By.linkText("Interface"), driver);
		
		//click new input
	    attemptClick(By.xpath("//button[contains(text(), 'Add application input')]"), driver);
	    addWait(200);
	    
	    //add uri input file
	    //input name
	    element = driver.findElements(By.xpath("//input")).get(4);
	    element.sendKeys("Input-File");
	    addWait(200);
	    
	    //input type
	    element = driver.findElements(By.xpath("//select")).get(0);
	    attemptClick(element, driver);
	    
	    
		attemptClick(element.findElement(By.xpath("//option[. = 'URI']")), driver);
		addWait(200);
		
		//input argument
		element = driver.findElements(By.xpath("//input")).get(6);
		element.sendKeys("-intput");
		addWait(200);
		
		//add outputs
		//add first output
		attemptClick(By.xpath("//button[contains(text(), 'Add application output')]"), driver);
	    addWait(200);
		
	    //output name
	    element = driver.findElements(By.xpath("//input")).get(31);
		element.sendKeys("Gaussian-Application-Output");
		addWait(200);
		
		//output value
	    element = driver.findElements(By.xpath("//input")).get(32);
		element.sendKeys("gaussian.log");
		addWait(200);
		
		//output metadata
		element = driver.findElements(By.xpath("//textarea")).get(4);
		element.sendKeys("{\n"
				+ "\"output-view-providers\": [\n"
				+ "\"gaussian-parser\",\n"
				+ "\"gaussian-eigenvalues-plot\",\n"
				+ "\"dreg-genome-browser\",\n"
				+ "\"cplex-solution-link\"\n"
				+ "],\"file-metadata\": {\n"
				+ "\"mime-type\": \"text/plain\"\n"
				+ "}\n"
				+ "}");
		
		//set type to URI
		element = driver.findElements(By.className("custom-select")).get(3);
		attemptClick(element.findElements(By.xpath("//option[. = 'URI']")).get(3), driver);
		addWait(200);
		
		//add 2nd output
		attemptClick(By.xpath("//button[contains(text(), 'Add application output')]"), driver);
	    addWait(200);
	    
		//name 38
	    element = driver.findElements(By.xpath("//input")).get(38);
		element.sendKeys("Gaussian-Checkpoint-File");
		addWait(200);
		
		//value 39
	    element = driver.findElements(By.xpath("//input")).get(39);
		element.sendKeys("*.chk");
		addWait(200);
		
		//Set type to URI_COLLECTION
		element = driver.findElements(By.className("custom-select")).get(4);
		attemptClick(element.findElements(By.xpath("//option[. = 'URI_COLLECTION']")).get(4), driver);
		addWait(200);
		
		//click save
		attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'Save')]")), driver);
		addWait(200);
		
		//add comet deployment
		//go to deployment tab
		attemptClick(By.linkText("Deployments"), driver);
		addWait(200);
		
		//click add deployment
		attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'New Deployment')]")), driver);
		addWait(200);
		
		//add comet deployment
		element = driver.findElement(By.xpath("//select"));
		attemptClick(element.findElement(By.xpath("//option[. = 'comet.sdsc.edu']")), driver);
		addWait(200);
		attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'OK')]")), driver);
		addWait(200);
		
		//executable path
	    driver.findElement(By.id("executable-path")).sendKeys("g09");
	    addWait(200);
	    
	    //Application Parallelism Type
	    element = driver.findElement(By.id("parallelism-type"));
	    attemptClick(element.findElement(By.xpath("//option[. = 'SERIAL']")), driver);
	    addWait(200);
	    
	    //module load commands
	    attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'Add Module Load Command')]")), driver);
	    element = driver.switchTo().activeElement();
	    element.sendKeys("module load gaussian");
	    addWait(200);
	    
	    //pre job commands
	    attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'Add Pre Job Command')]")), driver);
	    element = driver.switchTo().activeElement();
	    element.sendKeys("cp $scratchLocation/$gatewayId/$gatewayUserName/$applicationName/checkpoint.chk $workingDirectory/ 2>>/dev/null");
	    addWait(200);
	    
	    //post job commands
	    attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'Add Post Job Command')]")), driver);
	    element = driver.switchTo().activeElement();
	    element.sendKeys("rm $workingDirectory/checkpoint.chk   2>>/dev/null");
	    addWait(200);
	    
	    attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'Add Post Job Command')]")), driver);
	    element = driver.switchTo().activeElement();
	    element.sendKeys("cp $workingDirectory/*.chk $scratchLocation/$gatewayId/$gatewayUserName/$applicationName/checkpoint.chk  2>>/dev/null");
	    addWait(200);
	    
	    //default queue
	    element = driver.findElement(By.id("default-queue-name"));
	    attemptClick(element.findElement(By.xpath("//option[. = 'compute']")), driver);
	    addWait(200);
	    
		//click save
		attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'Save')]")), driver);
	}
	
}
