# Selenium Project Overview

This personal project is focused on automating test cases for saucedemo.com website, a demo e-commerce website using Selenium WebDriver with the Page Object Model (POM) design pattern. It includes various page objects for interacting with different sections of the application, a configuration class for setting up the WebDriver and test cases for executing the automated tests.

To run the tests, navigate to project directory and execute the following command:
	```mvn test```, this will trigger Maven to compile the project and run the test cases defined in the project.

* Test Cases execution demo in Chrome:
[![automation run in chrome](assets/demo.png)](https://www.youtube.com/watch?v=MfKdWw7zQjM)

# Test Cases containing test case IDs, test scenarios, test steps, expected results and statuses can be found here:
* [Test Cases Spreadsheet Document](https://docs.google.com/spreadsheets/d/1FUSAcneXHMrAfhirHQXsYZekvQnfc3wjYR6raoeiOE0/edit?gid=0#gid=0)

## \selenium-project1\src\main\java\saucedemo\pageObject
This directory contains the page object classes representing various pages of the application. These classes encapsulate the logic for interacting with page elements (buttons, fields, etc.) and provide methods to perform actions.

* ```CartPage.java```: Contains methods for interacting with the cart page.

* ```LoginPage.java```: Contains methods for logging in to the application.

* ```ProductCatalog.java```: Contains methods for interacting with the product catalog page.

## \selenium-project1\src\main\java\saucedemo\config
This directory contains configuration files and base test classes needed for setting up the testing environment.

* ```BaseTest.java```: A base test class that initializes the WebDriver and provides common test setup and teardown methods.

* ```ExtentReporter.java```: Configures and generates Extent Reports with a standardized format for test results. 

* ```Listeners.java```: Implements TestNG listeners to track test events and log results into the Extent Report.
	
* ```data.properties```: A properties file where you can select the browser in which the test cases will run (chrome, firefox or edge) and the URL. Test execution can also be set to run headless, to run in headless mode, append ```headless``` to chrome, firefox or edge.

## \selenium-project1\src\test\java\saucedemo\testCases
This directory contains the test case classes responsible for executing the test logic. Each class corresponds to specific scenarios and uses the page objects to carry out the tests.

* ```TestExecution.java```: The main class that contains the execution logic for running the tests. It integrates the page objects and performs actions in sequence to test the application.

## \selenium-project1\REPORTS\index.html
Extent Reports library is used to generate a report document, the ```index.html``` file will be generated with test execution results, including pass/fail status and screenshots for failed tests.
![Extent Report Screenshot](assets/extent-1.png)
![Extent Report Screenshot](assets/extent-2.png)