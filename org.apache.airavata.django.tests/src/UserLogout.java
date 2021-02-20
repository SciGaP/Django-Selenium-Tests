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
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DjangoTest;

/*
 *User Logout Class 
 * 
 * created on 8/26/2020
 * last modified 9/15/2020
 * 
 * class to test user logout on django portal
*/

class UserLogout   extends DjangoTest{
	WebDriver driver;
	String start_url;

	@BeforeEach
	public void setUp() throws Exception {
		driver = setDriver();
		start_url = readConfigFile("start_url");
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
		
		//logout
		element = driver.findElement(By.id("dropdownMenuButton"));
	    attemptClick(element, driver);
	    addWait(200);
	    attemptClick(driver.findElement(By.linkText("Logout")), driver);
	    addWait(200);
	    
	    //confirm logout
	    Assert.assertTrue(doesElementExist(driver, By.linkText("Log in")));
	    addWait(200);
	}

}
