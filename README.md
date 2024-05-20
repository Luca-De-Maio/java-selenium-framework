# Selenium Page Object Model with Java

This project demonstrates the Selenium Page Object Model pattern using Java, Maven, and TestNG. It includes an example of how to handle web elements, perform actions, and assert conditions using the Page Object Model (POM) approach.

## Project Structure

The project is organized into the following packages:
- **base**: Contains base classes for the tests and pages.
- **factory**: Contains classes for the factory pattern.
- **listeners**: Contains TestNG listeners for reporting.
- **pages**: Contains the page object classes representing web pages.
- **tests**: Contains the test classes.
- **utils**: Contains utility classes for common actions.

# Getting Started

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- ChromeDriver or other WebDriver binaries

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Luca-De-Maio/java-selenium-framework.git
2. Navigate to the project directory:
    ```sh
    cd selenium-pageobjectmodel
3. Install dependencies:
    ```sh
    mvn clean install
   
## Project Configuration
1. Driver Configuration:
Ensure you have the appropriate WebDriver binaries (e.g., chromedriver, geckodriver) available in your system PATH or configure their paths in your tests.

2. Test Configuration:
Modify the testng.xml file to include your test classes and any additional configurations.

## Running Tests
To run the tests, use the following command:

      mvn test

## Reporter Configuration
The project includes a TestNG listener for reporting. To enable the reporter, use the following command:

      mvn test -DsuiteXmlFile=testng.xml
