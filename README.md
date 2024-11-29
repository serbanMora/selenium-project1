•Selenium Project Overview

This personal project is focused on automating test cases for saucedemo.com website, a demo e-commerce website using Selenium WebDriver with the Page Object Model (POM) design pattern. It includes various page objects for interacting with different sections of the application, a configuration class for setting up the WebDriver, and test cases for executing the automated tests.

•Test Cases:
https://docs.google.com/spreadsheets/d/1FUSAcneXHMrAfhirHQXsYZekvQnfc3wjYR6raoeiOE0/edit?gid=0#gid=0

	•/SeleniumProject/src/main/java/pageObject
		This directory contains the page object classes representing various pages of the application. These classes encapsulate the logic for interacting with page elements (buttons, fields, etc.) and provide methods to perform actions.
		CartPage.java: Contains methods for interacting with the cart page.
		LoginPage.java: Contains methods for logging in to the application.
		ProductCatalog.java: Contains methods for interacting with the product catalog page.

	•/SeleniumProject/src/main/java/Project2/Config
		This directory contains configuration files and base test classes needed for setting up the testing environment.
		BaseTest.java: A base test class that initializes the WebDriver and provides common test setup and teardown methods.
		ExtentReporter.java: Configures and generates Extent Reports with a standardized format for test results. 
		Listeners.java: Implements TestNG listeners to track test events and log results into the Extent Report.	
		data.properties: A properties file where you can select the browser in which the test cases will run (Chrome, Firefox, Edge), the url and the browser driver paths.

	•/SeleniumProject/src/main/java/TestCases
		This directory contains the test case classes responsible for executing the test logic. Each class corresponds to specific scenarios and uses the page objects to carry out the tests.
		TestExecution.java: The main class that contains the execution logic for running the tests. It integrates the page objects and performs actions in sequence to test the application.

	•/SeleniumProject/REPORTS
		The index.html file will be generated here  will be generated here with test execution results, including pass/fail status and screenshots for failed tests.