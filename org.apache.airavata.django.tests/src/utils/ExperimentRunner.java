package utils;

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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import junit.framework.Assert;

/*
 *Experiment Runner Class 
 * 
 * created on 8/21/2020
 * last modified 11/13/2020
 * 
 * Parent class for all tests that submit jobs
 * 
 * 
 */


/*#######################################################################################################
 * Public Functions
 ########################################################################################################
 */

public abstract class ExperimentRunner extends DjangoTest{
	String exp_name, test_project, local_path, walltimeLimit, start_url, grp, expId;
	Boolean cancel_experiments, login;
	
	//runExperiment with login boolean
	public void runExperiment(WebDriver driver, Boolean login, By applicationBy, String name, String computeRes, String queue, String inputDir, String... inputFiles) throws Exception {
		this.login = login;
		runExperiment(driver, applicationBy, name, computeRes, queue, inputDir, inputFiles);
	}
	//runExperiment with wallTimeLimit argument
	public void runExperiment(WebDriver driver, By applicationBy, String name, String computeRes, String queue, int wallTimeLimit, String inputDir, String... inputFiles) throws Exception {
		this.walltimeLimit = Integer.toString(wallTimeLimit);
		runExperiment(driver, applicationBy, name, computeRes, queue, inputDir, inputFiles);
	}
	
	public void runExperiment(WebDriver driver, By applicationBy, String name, String computeRes, String queue, String inputDir, String... inputFiles) throws Exception {
		if (walltimeLimit==null){walltimeLimit = "15";}
		if (login == null) {login = true;}
		
		//load variables from properties file
		exp_name = readConfigFile("experiment_name");
		test_project = readConfigFile("test_project");
		local_path = readConfigFile("local_path");
		start_url = readConfigFile("start_url");
		cancel_experiments = Boolean.parseBoolean(readConfigFile("start_url"));
		grp = readConfigFile("exp_grp");
		
		if (login) {
			//go to the django portal
			driver.get(start_url);
			
			//login
			login(driver);
			addWait(300);
		}
		
		//go to applications
		while(true) {
			attemptClick(driver.findElement(applicationBy), driver);
			addWait(300);
			//make sure experiment page loads
			if(!doesElementExist(driver, By.id("experiment-name"))) {continue;}
			else {break;}
		}
		
		//set the experiment name
		setExperimentName(driver, name);
		addWait(300);
		
		//Share experiment
		shareExperiment(driver, 2);
		addWait(300);
		
		//choose the experiment project
		setProject(driver);
		addWait(300);
		
		//upload all input files
		for (String fileName : inputFiles) {
			uploadFile(driver, inputDir, fileName);
			addWait(300);
		}
		
		//choose group resource profile
		setGRP(driver, grp);
		addWait(300);
		
		//choose compute resource
		setComputeResource(driver, computeRes);
		addWait(300);
		
		//set queue
		setQueue(driver, queue);
		addWait(300);
	
		//launch experiment
		launchExperiment(driver);
		addWait(200);
		if (cancel_experiments) {
			cancelExperiment(driver);
		}
		//clone experiment
		cloneExperiment(driver);
		addWait(200);
		if (cancel_experiments) {
			cancelExperiment(driver);
		}
	}
	
	//clone experiment
	public void cloneExperiment(WebDriver driver) throws Exception {
		
		//Clone experiment from experiment summary page
		WebElement element = driver.findElement(By.xpath("//button[contains(text(), 'Clone')]"));
		attemptClick(element, driver);
		
		//launch experiment
		launchExperiment(driver);
		
	}
	
	//cancel experiment
	public void cancelExperiment(WebDriver driver) throws Exception {
		//Cancel experiment from experiment summary page
		WebElement element = driver.findElement(By.xpath("//button[contains(text(), 'Cancel')]"));
		attemptClick(element, driver);
		addWait(200);
		
		//confirm experiment status is changed to is canceled		
		if (!doesElementExist(driver, By.xpath("//*[contains(text(), 'CANCELED')]"))) {
			throw new Exception("Experiment status not changed to cancel");
		}
		addWait(200);
	}
	
	//share experiment, takes a WebDriver and an Integer indicating the index of the save button
	public void shareExperiment(WebDriver driver, int saveIndex) throws Exception {
		String share_email = readConfigFile("share_email");
		String share_group = readConfigFile("share_group");
		WebElement element;
		
		//click share button
		element = driver.findElement(By.xpath("//button[contains(text(), 'Share')]"));
		attemptClick(element, driver);
		
		//share with a group
	    driver.findElement(By.xpath("//input[@placeholder='Type to get suggestions...']")).sendKeys(share_group);
	    attemptClick(driver.findElement(By.xpath("//*[contains(text(), '"+share_group+"')]")), driver);
	    
	    //edit sharing 	permissions
	    cyclePermissions(driver);
	    
		//type gateway users
	    driver.findElement(By.xpath("//input[@placeholder='Type to get suggestions...']")).sendKeys("Gateway Users");
	    attemptClick(driver.findElement(By.xpath("//*[contains(text(), 'Gateway Users')]")), driver);

	    //edit sharing 	permissions
	    cyclePermissions(driver);
		
	    //share with individual user
	    element =  driver.findElement(By.xpath("//input[@placeholder='Type to get suggestions...']"));
	    element.sendKeys(share_email);
	    attemptClick(driver.findElement(By.xpath("//*[contains(text(), '"+share_email+"')]")), driver);

	    //edit sharing 	permissions
	    cyclePermissions(driver);
	    
	    //click save (there are 3 save buttons present on the page; two are disabled)
	    element = driver.findElements(By.xpath("//button[contains(text(), 'Save')]")).get(saveIndex);
	    attemptClick(element, driver);
	}

	/*#######################################################################################################
	 * Private Functions
	 ########################################################################################################
	 */
	
	//set the experiment name
	private void setExperimentName(WebDriver driver, String name) {
		WebElement element = driver.findElement(By.id("experiment-name"));
		scrollToElement(element, driver);
		element.clear();
		element.sendKeys(exp_name+"_"+name+"_"+currentDateAsString()+"_"+currentTimeAsString());
	}
	
	//select a project
	private void setProject(WebDriver driver) throws Exception {
		WebElement element = driver.findElement(By.id("project"));
		attemptClick(element.findElement(By.xpath("//option[. = '"+test_project+"']")), driver);
	}
	
	//upload an input file
	private void uploadFile(WebDriver driver, String inputDir, String fileName) throws Exception {
		//upload file
		WebElement element = driver.findElement(By.cssSelector(".uppy-DragDrop-browse"));
		scrollToElement(element, driver);
		addWait(200);
		
		//upload file
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(local_path+"\\"+inputDir+"\\"+fileName);
		
		//confirm file uploaded
		if (!doesElementExist(driver, By.xpath("//a[contains(text(), '"+fileName+"')]"))) {
			throw new Exception ("file didn't uplaod: "+fileName);
		}
	}
	
	//set the group resource profile
	private void setGRP(WebDriver driver, String grp) throws Exception {
		WebElement element = driver.findElement(By.id("group-resource-profile"));
		attemptClick(element.findElement(By.xpath("//option[. = '"+grp+"']")), driver);
	}
	
	//set the compute resource
	private void setComputeResource(WebDriver driver, String computeRes) throws Exception {
		WebElement element = driver.findElement(By.id("compute-resource"));
		attemptClick(element.findElement(By.xpath("//option[. = '"+computeRes+"']")), driver);
	}
	
	//set queue
	private void setQueue(WebDriver driver, String queue) throws Exception {
		//go to queue options
		WebElement element = driver.findElement(By.xpath("//h5[contains(text(), 'queue')]"));
		attemptClick(element, driver);
		addWait(200);
		
		//select a queue
		element = driver.findElement(By.id("queue"));
		attemptClick(element.findElement(By.xpath("//option[. = '"+queue+"']")), driver);
		
		//set walltime limit
		element = driver.findElement(By.id("walltime-limit"));
		element.clear();
		element.sendKeys(walltimeLimit);
	}
	
	//save and launch experiment
	private void launchExperiment(WebDriver driver) throws Exception {
		WebElement element = driver.findElement(By.xpath("//button[contains(text(), 'Launch')]"));

		//click launch button
	    int timeout = 5;
	    while(timeout>0) {
	    	attemptClick(element, driver);
		    addWait(200);
			if(!doesElementExist(driver, By.xpath("//h1[contains(text(), 'Experiment Summary')]"))) {timeout--;}
			else {break;}
		}
	    
	    //Confirm that experiment summary page loads
	    if (!doesElementExist(driver, By.xpath("//h1[contains(text(), 'Experiment Summary')]"))) {
	    	throw new Exception("Experient summary page didn't laod");
	    }
	    addWait(200);
	    //Confirm that experiment Status is set to "Executing"
	    if (!doesElementExist(driver, By.xpath("//*[contains(text(), 'EXECUTING')]"))) {
	    	throw new Exception("Experiment status not changed to 'Executing'");
	    }
	    addWait(200);
	    
	    //get experiment id
	    expId = driver.findElement(By.cssSelector(".table > tr > td:nth-child(2)")).getText();
	    System.out.println("Experiment ID: "+expId);
	}

	//this function will cycle the permissions of the top most selector
	private void cyclePermissions(WebDriver driver)  throws Exception {
		WebElement element;
	    //edit sharing 	permissions
		element = driver.findElement(By.xpath("//select[@class='custom-select']"));
		attemptClick(element.findElement(By.xpath("//option[. = 'WRITE']")), driver);
		addWait(200);
		attemptClick(element.findElement(By.xpath("//option[. = 'READ']")), driver);
		addWait(200);
	}
}
