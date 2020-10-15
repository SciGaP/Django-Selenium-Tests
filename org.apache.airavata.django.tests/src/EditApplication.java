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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.ExperimentRunner;
import utils.PropertiesLoader;

/*
 * Edit Application Class
 * 
 * created on 9/16/2020
 * last modified 10/6/2020
 * 
 * Class to test editing an existing application
 * 
 */

class EditApplication extends ExperimentRunner{

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
		
		//go to application catalog
	    attemptClick(driver.findElement(By.id("dropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.id("appDropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.cssSelector(".fa-cog")), driver);

		//click on application specified in properties file
	    attemptClick(By.xpath("//h2[contains(text(), '"+readConfigFile("test_app")+"')]"), driver);
	    
	    //click interface tab
	    attemptClick(By.linkText("Interface"), driver);
	    
		//add new input
	    addWait(200);
	    int inputIndex = driver.findElements(By.xpath("//input")).size()-14;
	    
	    addInput(inputIndex, "New-Input-File", "URI", "-intputfile", driver);
		inputIndex+=13;
		addInput(inputIndex, "New-String-Input", "STRING", "-intputstring", driver);
	    
	    //click save
	  	attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'Save')]")), driver);
  		addWait(200);
	  		
  		
  		try {
  		//add comet deployment
  	  		addCometDeployment();
  		}catch(NoSuchElementException e) {
  			//comet is already a deployment
  			attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'CANCLE')]")), driver);
  			addWait(200);
  			
  		}
		
  		//save
		attemptClick(driver.findElement(By.xpath("//button[contains(text(), 'Save')]")), driver);
	    addWait(300);
	    
	    //go back to dashboard
	    attemptClick(driver.findElement(By.id("dropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.id("appDropdownMenuButton")), driver);
	    attemptClick(driver.findElement(By.cssSelector(".fa-flask")), driver);
	    attemptClick(driver.findElement(By.cssSelector(".fa-flask")), driver);
	    addWait(300);
	    
		//run application on comet
	    String app_name = readConfigFile("test_app");
	    //driver, application path, experiment name, compute resource, queue, input files...	    
		runExperiment(driver, 
				By.xpath("//h2[contains(text(), '"+app_name+"')]"), 
				app_name+" Comet", 
				"comet.sdsc.edu",
				"compute",
				PropertiesLoader.GAUSSIAN_DIR,
				PropertiesLoader.GAUSSIAN_INPUT);
		addWait(200);
		
	}
	
	private void addCometDeployment() throws Exception {
		WebElement element;
		
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
	}
	
	private void addInput(int inputIndex, String inputName, String inputType, String inputArgument, WebDriver driver) throws Exception {
		WebElement element;
		
		//add new input
		attemptClick(By.xpath("//button[contains(text(), 'Add application input')]"), driver);
		
		//input name
		element = driver.findElements(By.xpath("//input")).get(inputIndex);
		element.sendKeys(inputName);
		addWait(200);
		
		//input type
		element = driver.findElements(By.xpath("//select")).get((inputIndex-4)/13);
		attemptClick(element, driver);
		attemptClick(element.findElement(By.xpath("//option[. = '"+inputType+"']")), driver);
		addWait(200);
		
		//input argument
		element = driver.findElements(By.xpath("//input")).get(inputIndex+2);
		element.sendKeys(inputArgument);
		addWait(200);
	}
}
