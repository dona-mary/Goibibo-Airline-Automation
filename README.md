# Goibibo Airline Automation

This project is a Selenium-based automation suite for demonstrating flight booking flows on Goibibo. It uses Java, TestNG, Maven, and the Page Object Model (POM) design pattern. It also includes integration support for Docker and Jenkins-based CI/CD execution.

## Project Structure

```text
.
|-- pom.xml
|-- testng.xml
|-- src
|   |-- main
|   |   |-- java/com/airline/
|   |   |   |-- driverfactory/
|   |   |   |   |-- DriverFactory.java
|   |   |   |   |-- BasePage.java
|   |   |   |   `-- APIEndpoints.java
|   |   |   |-- listeners/
|   |   |   |   `-- TestListener.java
|   |   |   |-- pages/
|   |   |   `-- utils/
|   |   `-- resources/config/config.properties
|   `-- test
|       |-- java/com/airline/test/testcases/
|       `-- resources/
|
|-- target/extent
|-- target/screenshots
`-- test-output
```

## Prerequisites

- Java JDK 17
- Maven 3.x
- Google Chrome (or another supported browser)
- Docker (for containerized execution)
- Jenkins (for CI/CD)

## 1. Local Setup and Execution

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd Goibibo-Airline-Automation
```

### Step 2: Configure Properties

Update `src/main/resources/config/config.properties` with your environment details.

### Step 3: Execute Tests via Command Line

Run all tests using Maven:

```bash
mvn test -Dheadless=true
```

Note: Set `headless=false` if you want to see the browser execution.

### Step 4: View Reports

After execution, reports are generated in:

- Extent Report: `src/test/resources/ExtentReport/ExtentReport.html`
- TestNG XML: `test-output/testng-results.xml`
- Screenshots: `src/test/resources/screenshots`

## 2. Docker Containerization

The project includes a Dockerfile that sets up a Linux environment with Chrome and Maven to run tests.

### Build the Docker Image

```bash
docker build -t goibibo-automation .
```

### Run Tests in Docker

```bash
docker run --rm -v "%cd%:/app" goibibo-automation mvn test -Dheadless=true
```

Note: Using `-v` allows you to see generated reports on your local machine under the `target` folder after the container exits.

## 3. Jenkins Freestyle Pipeline Setup

If you prefer a Freestyle project over a scripted Pipeline:

1. Create a new item and select "Freestyle project".
2. Configure Git under Source Code Management and provide the repository URL.
3. Optional: Check "Provide Configuration files" for custom settings.
4. Add a build step: "Invoke top-level Maven targets".
   - Goals: `clean test -Dheadless=true`
5. Add post-build actions:
   - Publish JUnit test result report: set XML path to `src/test/resources/ExtentReport/ExtentReport.html`
   - Archive artifacts: `src/test/resources/screenshots`
6. Optional: If the HTML Publisher plugin is installed, publish HTML reports pointing to `target/extent`.

## 4. Jenkins Pipeline (Recommended)

The project includes a Jenkinsfile for a declarative pipeline.

1. Create a new item and select "Pipeline".
2. Set "Pipeline script from SCM".
3. Select "Git" and provide the repository URL.
4. Ensure the script path is set to `Jenkinsfile`.

The pipeline will automatically:

- Build the Docker image
- Run tests inside a Docker container
- Publish TestNG results
- Archive Extent reports and screenshots

## Troubleshooting

- Bot Detection: Goibibo uses anti-bot measures. The framework uses `--disable-blink-features=AutomationControlled` to mitigate this, but execution in non-headless mode is generally more reliable.
- Timeout Errors: If you encounter timeouts, check screenshots in `target/screenshots` to verify whether the site structure has changed or a CAPTCHA is appearing.
