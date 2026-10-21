#Goibibo Airline Automation 
This project is a Selenium-based automation suite for demonstrating flight booking flows on Goibibo. It uses Java, TestNG, Maven, and Page Object Model (POM) design pattern. It also includes integration for Extent Reports, Jenkins CI/CD, and Docker containerization.

##Project Structure
.
|-- pom.xml
|-- testng.xml
|-- src
|   |-- main
|   |   |-- java/com/airline/
|   |   |   |-- driverfactory
|   |   |   |       |--DriverFactory.java
|   |   |   |       |--BasePage.java
|   |   |   |       | -APIEndpoints.java
|   |   |   |-- listeners/TestListener.java
|   |   |   |-- pages/
|   |   |   `-- utils/
|   |   `-- resources/config/config.properties
|   `-- test
|       |-- java/com/airline/test/testcases/
|       |`-- resources
|          
|-- target/extent
|-- target/screenshots
`-- test-output

##Prerequisites
Java JDK 17
Maven 3.x
Google Chrome (or other supported browsers)
Docker (for containerized execution)
Jenkins (for CI/CD)

##1. Local Setup and Execution
###Step 1: Clone the Repository
git clone <repository-url>
cd goibiboframework

###Step 2: Configure Properties
Update src/main/resources/config/config.properties with your environment details.

###Step 3: Execute Tests via Command Line
Run all tests using Maven: `bash mvn test -Dheadless=true ` Note: Set `headless=false` if you want to see the browser execution.

###Step 4: View Reports
After execution, reports are generated in:

Extent Report: src/test/resources/ExtentReport/ExtentReport.html
TestNG XML: test-output/testng-results.xml
Screenshots: src/test/resources/screenshots

##2. Docker Containerization
The project includes a Dockerfile that sets up a Linux environment with Chrome and Maven to run tests.

###Build the Docker Image
docker build -t goibibo-automation .
Run Tests in Docker
docker run --rm -v "%cd%:/app" goibibo-automation mvn test -Dheadless=true
Note: Using `-v` (volume mount) allows you to see the generated reports on your local machine under the `target` folder after the container exits.

##3. Jenkins Freestyle Pipeline Setup
If you prefer a Freestyle project over the scripted Pipeline:

New Item: Select "Freestyle project".
Source Code Management: Select "Git" and provide the repository URL.
Build Environment: (Optional) Check "Provide Configuration files" if using custom settings.
Build Steps:
Add "Invoke top-level Maven targets".
Goals: clean test -Dheadless=true.
Post-build Actions:
Publish JUnit test result report: Set Test report XMLs to src/test/resources/ExtentReport/ExtentReport.html
Archive the artifacts: Set "Files to archive" to src/test/resources/screenshots
(Optional) If the HTML Publisher plugin is installed, add "Publish HTML reports" pointing to target/extent.
##4. Jenkins Pipeline (Recommended)
The project includes a Jenkinsfile for a declarative pipeline.

New Item: Select "Pipeline".
Pipeline: Select "Pipeline script from SCM".
SCM: Select "Git" and provide the repository URL.
Script Path: Ensure it is set to Jenkinsfile.
The pipeline will automatically:

Build the Docker image.
Run tests inside a Docker container.
Publish TestNG results and archive Extent reports/screenshots.

#Troubleshooting
Bot Detection: Goibibo uses anti-bot measures. The framework uses --disable-blink-features=AutomationControlled to mitigate this, but execution in non-headless mode is generally more reliable.
Timeout Errors: If you encounter timeouts, check the screenshots in target/screenshots to verify if the site structure has changed or a CAPTCHA appeared.