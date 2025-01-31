# Selenium Project Overview

This personal project is focused on automating test cases for saucedemo.com website, a demo e-commerce website using Selenium WebDriver with the Page Object Model (POM) design pattern. It includes various page objects for interacting with different sections of the application, a configuration class for setting up the WebDriver, and test cases for executing the automated tests.

To run the tests, navigate to your project directory and execute the following command:
	```mvn test```
 This will trigger Maven to compile the project and run the test cases defined in the project.

# Test Cases containing scenarios, steps, automation data, ER/AR and statuses can be found here:
* [Test Cases Spreadsheet Document](https://docs.google.com/spreadsheets/d/1FUSAcneXHMrAfhirHQXsYZekvQnfc3wjYR6raoeiOE0/edit?gid=0#gid=0)

## \SeleniumProject\src\main\java\saucedemo\pageObject
This directory contains the page object classes representing various pages of the application. These classes encapsulate the logic for interacting with page elements (buttons, fields, etc.) and provide methods to perform actions.

* ```CartPage.java```: Contains methods for interacting with the cart page.

* ```LoginPage.java```: Contains methods for logging in to the application.

* ```ProductCatalog.java```: Contains methods for interacting with the product catalog page.

## \SeleniumProject\src\main\java\saucedemo\config
This directory contains configuration files and base test classes needed for setting up the testing environment.

* ```BaseTest.java```: A base test class that initializes the WebDriver and provides common test setup and teardown methods.

* ```ExtentReporter.java```: Configures and generates Extent Reports with a standardized format for test results. 

* ```Listeners.java```: Implements TestNG listeners to track test events and log results into the Extent Report.
	
* ```data.properties```: A properties file where you can select the browser in which the test cases will run (chrome, firefox or edge), the url and the browser driver paths. The chrome browser test execution can also be set to run headless, to run Chrome in headless mode, append ```headless``` to ```chrome```.

## \SeleniumProject\src\test\java\saucedemo\testCases
This directory contains the test case classes responsible for executing the test logic. Each class corresponds to specific scenarios and uses the page objects to carry out the tests.

* ```TestExecution.java```: The main class that contains the execution logic for running the tests. It integrates the page objects and performs actions in sequence to test the application.

## \SeleniumProject\REPORTS\index.html
Extent Reports library is used to generate a report document, the ```index.html``` file will be generated with test execution results, including pass/fail status and screenshots for failed tests.
![ScreenShot](https://previews.dropbox.com/p/thumb/ACg12fI53I6JPvr-x7MmjHsTd4oxnKeiE4Azi_S31Rq8i3g9UFQFTArsROjgp09pmfvOw_cctuFo6uTcz_qQgJsHx3pNAk2JB2h38SCa4nXtCEcqo8igoCaus5lhO7elxs_nBUuoST1WabxRo5ZXTfC6q-ia8Maz22wYBo6DgXoGwB8FlwYukYCkFMHq10TRmBtz7mKXk11aAcWLwF1hQ4CXoDyGL_Czuvzrxf7N0AktPiXwnPPFhlPTyg0tZC3dJtAorX6F7Kl4BoCLXRhy8AmfHeVCb47OXxPY7DgQya4c4PhcNekrhjM2OM_pDZFAbKaUJF-CKl0OPL1mI4WGra_j/p.png?is_prewarmed=true)
![ScreenShot](https://previews.dropbox.com/p/thumb/AChaVBcQHFDt76qugBGUCbdN4apaVVZhnmbtkO8cEr2DQlgiEo2HlXOcifIOvEDNOqagdKCab8xSe7YgPcgNATL586qmVxWTXRkKJA76vrRp-uNHZLlz2ZF2fMmGMUHMomOte1MsTgBfZ_CtKYtCKSr6JNazlEqtL2hRsIfjqJvi10IEjnazPCIyLhNKK8A9OuIexv-1lbslTAM51nLurdLc7gnzkDL9_Cb5OTKZiQncKE2tfzuZrc-l6FE2cwGh8VPcVyobXEQNq8ci6q4wvaxtywDrgGBNXFIOIxYwpRNjabtV0ZCV2hxtRCUsv89e9_7JCLDhwUCnYbRHSBT3Gtfa/p.png?is_prewarmed=true)

