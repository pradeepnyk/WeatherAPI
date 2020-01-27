# Content

## Test Execution in a glance:
### This is a REST API Automation framework to demonstrate the Automation testing of a GET requests and their response body, status code, schema and so on. Here, I have used REST Assured dependency of Java programming which is built on BDD structure itself. TestNG is used for running all test methods

## Below are some guidelines how to use this repository and few details about the work

- Folder structure

![Image of Yaktocat](https://github.com/pradeepnyk/WeatherAPI/blob/master/Folder_structure.png)

## To use this project:
Clone: https://github.com/pradeepnyk/WeatherAPI.git
cd WeatherAPI/



- Dependencies
    - Java (JDK 8)
    - Selenium
    - Maven
    - TestNG
    - REST Assured
    - Surefire reporting, Extent reporting
    - Log4J

- Assumption
    - For privacy prupose, I am not using my own API key
    - xml file is setup according to take input to the code


- Possible Improvements
    - Integrating to CI tool
    - Addition of more endpoints and methods
    
- Report generation after execution
    - Extent: WeatherAPI/target/surefire-reports/Extent.html
    - Surefire HTML: WeatherAPI/target/surefire-reports/index.html

Execution command prompt:
`mvn clean test`

Notes:
- Test case re-usablity is possible which will drive the further execution steps
- Maintainability - is possible by adding few more help methods
- Parallel execution saved time
