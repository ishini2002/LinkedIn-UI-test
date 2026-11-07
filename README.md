# LinkedIn UI Test Suite (IT3153 Software Quality Assurance, Assignment 04)

Automated UI tests for LinkedIn using Java 17, Selenium WebDriver 4, TestNG and Maven.
The tests use the Page Object Model and explicit waits.

## Test cases
| ID | Test | Class |
|----|------|-------|
| TC01 | Valid login reaches the feed | LoginTest |
| TC02 | Invalid password shows an error | LoginTest |
| TC03 | Search opens a results page | SearchTest |
| TC04 | First result opens the correct page | SearchResultTest |

## Requirements
- JDK 17 or later
- Maven 3.8 or later
- Google Chrome (Selenium Manager downloads ChromeDriver automatically)
- Your own LinkedIn account, preferably a dedicated test account

## Configure credentials
Create `src/test/resources/config.properties` with:

```
linkedin.username=your_linkedin_email
linkedin.password=your_linkedin_password
```

No quotes, no spaces around `=`. This file is listed in `.gitignore` and must never be committed.
Never use anyone else's credentials.

## Run the tests
```
mvn test
```
A single test:
```
mvn test -Dtest=LoginTest#validLogin
```
Screenshots (PASS_ and FAIL_ files) are saved automatically to the `screenshots` folder.

## CAPTCHA and security checks
The tests never bypass a CAPTCHA or security check. If LinkedIn shows one, the test pauses
for up to 2 minutes so you can complete it manually in the browser, then continues.
Run the suite sparingly, because repeated automated logins can trigger more checks or restrict the account.

## Project structure
```
src/test/java/com/ishini/sqa
  base/   BaseTest (starts and closes the browser, saves screenshots)
  pages/  BasePage, LoginPage, HomePage, SearchResultsPage
  tests/  LoginTest, SearchTest, SearchResultTest
  utils/  Config (reads credentials)
testng.xml
```