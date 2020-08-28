# @Django-Selenium-Tests
Selenium automated test cases for Django

## Instructions for Running Selenium Tests

### Setup

1). Required browser

      -Chrome V. 73 or later
      or
      -Firefox V. 60 or later
      
2). Selenium version 3.141.59 or later is required as well as Chrome driver for use with chrome or Gecko Driver for use with firefox
      https://www.selenium.dev/downloads/ 
      
3). Input files: https://iu.box.com/s/9ztdby709kso8siachz16svn2y511nn7

### Running Selenium Tests:

#### Setup:

1). Clone and download the reposity and navigate to org.apache.airavata.django.tests/src/resources/config

2). Specify the following information in the config.properties file

      -django username
      -django password
      -share email, required for testing sharing
      -group name for testing sharing
      -new username, new password, new email, first name, and last name for testing new user creation
      -local path to directory containing input files
      -default driver: chrome, firefox, or safari

3). Navigate to org.apache.airavata.django.tests/src/utils make sure that the directorie and file names specified in PropertiesLoader.java match the directory and input file names in the specified local path

#### Run Tests:
 
1). All tests can be run at once with the AllTests file, or each test can be run individually.

2). Other tests include:
      
      -LoadTest: tests load capacity of the django portal
      -UserLogout: tests logging out of the django portal
      -CreateUserLogin: tests user creation in the django portal
