package com.example.SeleniumJavaTest;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;

import java.util.List;


public class SeleniumPlain {

    public static void main(String[] args) {

        final String WEB_URL = "https://srs-login-page.herokuapp.com/";
        final String LOCAL_URL = "http://localhost:5000";
        final int LOGIN_ATTEMPTS_UNTIL_CAPTCHA = 3;

        User[] registeredUsers = createRegisteredUsers();
        User[] unregisteredUsersWithValidCredentials = createUnregisteredUsersWithValidCredentials();
        User[] usersWithInvalidCredentials = createUsersWithInvalidCredentials();

        System.setProperty("webdriver.chrome.driver", "D:/School/Summer 2021 CS-458/Projects/CS-458-Project-1/SeleniumJavaTest/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "D:/School/Summer 2021 CS-458/Projects/CS-458-Project-1/SeleniumJavaTest/geckodriver.exe");
        System.setProperty("webdriver.opera.driver", "D:/School/Summer 2021 CS-458/Projects/CS-458-Project-1/SeleniumJavaTest/operadriver.exe");

        System.out.println("[TC-1] Input Validation Test Starting");
        inputValidationTest(WEB_URL, registeredUsers,unregisteredUsersWithValidCredentials,usersWithInvalidCredentials);

//        System.out.println("[TC-2] Browser Support Test Starting");
//        browserSupportTest(WEB_URL, registeredUsers);
//
//        System.out.println("[TC-3] Multiple Failed Login Attempts Test Starting");
//        multipleFailureCaptchaTest(WEB_URL, unregisteredUsersWithValidCredentials, LOGIN_ATTEMPTS_UNTIL_CAPTCHA);
//
//        System.out.println("[TC-4] Page Route Test Starting");
//        pageRoutingTest(WEB_URL,registeredUsers);



    }

    public static void inputValidationTest(String url, User[] registeredUsers, User[] unregisteredUsersWithValidCredentials, User[] usersWithInvalidCredentials) {
        WebDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();

        WebElement id_input;
        WebElement password_input;
        WebElement login_button;

        // Test if all registeredUser can successfully login
        for (User user : registeredUsers) {

            chromeDriver.get(url);

            id_input = chromeDriver.findElement(By.cssSelector("input[name='bilkent_id']"));
            password_input = chromeDriver.findElement(By.cssSelector("input[name='password']"));
            login_button = chromeDriver.findElement(By.cssSelector("button"));

            id_input.sendKeys(user.getBilkentId());
            password_input.sendKeys(user.getPassword());
            login_button.click();

            Assertions.assertTrue(chromeDriver.findElement(By.cssSelector("#loginSuccess")).isDisplayed(),
                    "[TC-1] Could not reach successfully login with registered users' credentials");

        }


        for (User user : unregisteredUsersWithValidCredentials) {

            chromeDriver.get(url);

            id_input = chromeDriver.findElement(By.cssSelector("input[name='bilkent_id']"));
            password_input = chromeDriver.findElement(By.cssSelector("input[name='password']"));
            login_button = chromeDriver.findElement(By.cssSelector("button"));

            id_input.sendKeys(user.getBilkentId());
            password_input.sendKeys(user.getPassword());
            login_button.click();

            Assertions.assertTrue(chromeDriver.findElement(By.xpath("//p[text()='Wrong password or Bilkent ID number.']")).isDisplayed(),
                    String.format("[TC-1] Did not show unregistered user error message for bilkent_id: %s, password: %s", user.getBilkentId(), user.getPassword()));
        }

        for (User user : usersWithInvalidCredentials) {

            chromeDriver.get(url);

            id_input = chromeDriver.findElement(By.cssSelector("input[name='bilkent_id']"));
            password_input = chromeDriver.findElement(By.cssSelector("input[name='password']"));
            login_button = chromeDriver.findElement(By.cssSelector("button"));

            id_input.sendKeys(user.getBilkentId());
            password_input.sendKeys(user.getPassword());
            login_button.click();

            List<WebElement> errorMessages = chromeDriver.findElements(By.cssSelector(".error_msg"));

            Assertions.assertNotEquals(0, errorMessages.size(),
                    String.format("[TC-1] Did not show error message for invalid input - bilkent_id: %s, password: %s", user.getBilkentId(), user.getPassword()));

            Assertions.assertNotEquals("Wrong password or Bilkent ID number.", errorMessages.get(errorMessages.size()-1).getText(),
                    String.format("[TC-1] Did not show correct error message for invalid input - bilkent_id: %s, password: %s", user.getBilkentId(), user.getPassword()));
        }

        chromeDriver.close();

        System.out.println("[TC-1] Input Validation Tests successfully completed");

    }

    public static void browserSupportTest(String url, User[] registeredUsers) {

        WebDriver[] webDrivers = new WebDriver[3];
        webDrivers[0] = new ChromeDriver();
        webDrivers[1] = new FirefoxDriver();
        webDrivers[2] = new OperaDriver();


        for (WebDriver driver : webDrivers) {
            for (User user : registeredUsers) {
                driver.get(url);

                // gather form elements
                WebElement id_input = driver.findElement(By.cssSelector("#bilkent_id"));
                WebElement password_input = driver.findElement(By.cssSelector("#password"));
                WebElement login_button = driver.findElement(By.cssSelector("button"));

                // fill out form with the registeredUser's info
                id_input.sendKeys(user.getBilkentId());
                password_input.sendKeys(user.getPassword());
                login_button.click();

                // check if we have arrived on the login success page
                Assertions.assertTrue(driver.findElement(By.cssSelector("#loginSuccess")).isDisplayed(),
                        String.format("Could not access login success page for %s", user.getBilkentId()));
            }

            driver.close();

        }

        System.out.println("[TC-2] Browser Support Tests successfully completed");
    }

    public static void multipleFailureCaptchaTest(String url, User[] unregisteredUsers, int loginAttemptsUntilCaptcha) {
        WebDriver chromeDriver = new ChromeDriver();

        for (int i = 0; i < loginAttemptsUntilCaptcha + 2 && i < unregisteredUsers.length; i++) {
            chromeDriver.get(url);

            // gather form elements
            WebElement id_input = chromeDriver.findElement(By.cssSelector("#bilkent_id"));
            WebElement password_input = chromeDriver.findElement(By.cssSelector("#password"));
            WebElement login_button = chromeDriver.findElement(By.cssSelector("button"));

            // fill out form with the unregisteredUser's info
            id_input.sendKeys(unregisteredUsers[i].getBilkentId());
            password_input.sendKeys(unregisteredUsers[i].getPassword());
            login_button.click();

//            System.out.println("Attempt " +  (i + 1));
            if (i + 1 < loginAttemptsUntilCaptcha) {
                Assertions.assertEquals(1, chromeDriver.findElements(By.cssSelector("#captcha.input__group.captcha.hidden")).size(),
                        String.format("Captcha is displayed at failed attempt %d instead of %d", i, loginAttemptsUntilCaptcha));
            } else {
                Assertions.assertEquals(0, chromeDriver.findElements(By.cssSelector("#captcha.input__group.hidden")).size(),
                        String.format("Captcha was not displayed at failed attempt %d when it should have beyond attempt %d", i + 1, loginAttemptsUntilCaptcha));
            }
        }

        chromeDriver.close();

        System.out.println("[TC-3] Multiple Failed Login Attempts Test successfully completed");
    }

    public static void pageRoutingTest(String url, User[] registeredUsers) {
        WebDriver chromeDriver = new ChromeDriver();

        chromeDriver.manage().window().maximize();

        chromeDriver.get(url);

        // Login page to successful login page
        WebElement id_input = chromeDriver.findElement(By.cssSelector("input[name='bilkent_id']"));
        WebElement password_input = chromeDriver.findElement(By.cssSelector("input[name='password']"));
        WebElement login_button = chromeDriver.findElement(By.cssSelector("button"));

        id_input.sendKeys(registeredUsers[0].getBilkentId());
        password_input.sendKeys(registeredUsers[0].getPassword());
        login_button.click();

        Assertions.assertTrue(chromeDriver.findElement(By.cssSelector("#loginSuccess")).isDisplayed(),
                "[TC-4] Could not reach successful login page from login page");

        // Successful login page to login page
        WebElement loginNavButton = chromeDriver.findElement(By.xpath("//*[text()='Login']"));
        loginNavButton.click();

        Assertions.assertTrue(chromeDriver.findElement(By.cssSelector("#login")).isDisplayed(),
                "[TC-4] Could not reach login page from login successful page");

        // Login page to successful login page
        id_input = chromeDriver.findElement(By.cssSelector("input[name='bilkent_id']"));
        password_input = chromeDriver.findElement(By.cssSelector("input[name='password']"));
        login_button = chromeDriver.findElement(By.cssSelector("button"));

        id_input.sendKeys(registeredUsers[0].getBilkentId());
        password_input.sendKeys(registeredUsers[0].getPassword());
        login_button.click();

        Assertions.assertTrue(chromeDriver.findElement(By.cssSelector("#loginSuccess")).isDisplayed(),
                "[TC-4] Could not reach successful login page from login page");

        // Succesful login page to reset password page
        WebElement resetPasswordNavButton = chromeDriver.findElement(By.xpath("//*[text()='Reset Password']"));
        resetPasswordNavButton.click();

        Assertions.assertTrue(chromeDriver.findElement(By.cssSelector("#resetPassword")).isDisplayed(),
                "[TC-4] Could not reach reset password page from successful login page");

        // Reset password page to login page with reset password nav link
        loginNavButton = chromeDriver.findElement(By.xpath("//*[text()='Login']"));
        loginNavButton.click();

        Assertions.assertTrue(chromeDriver.findElement(By.cssSelector("#login")).isDisplayed(),
                "[TC-4] Could not reach login page from login successful page");

        // Login page to reset password page with reset password text link
        WebElement resetPasswordLink = chromeDriver.findElement(By.cssSelector(".login__tooltip>a"));
        resetPasswordLink.click();

        Assertions.assertTrue(chromeDriver.findElement(By.cssSelector("#resetPassword")).isDisplayed(),
                "[TC-4] Could not reach reset password page from successful login page");

        chromeDriver.close();

        System.out.println("[TC-4] Page Routing Tests successfully completed");

    }

    public static void testCopyingPasswordField(String url, User testUser){
        WebDriver chromeDriver = new ChromeDriver();

        chromeDriver.get(url);

        WebElement password_input = chromeDriver.findElement(By.cssSelector("input[name='password']"));

        password_input.sendKeys(testUser.getPassword());

        Actions actions = new Actions(chromeDriver);

        String copyAction = Keys.chord(Keys.CONTROL, Keys.chord("c"));


    }

    public static User[] createRegisteredUsers() {
        User[] registeredUsers = new User[3];

        registeredUsers[0] = new User("20212021", "pass2021");
        registeredUsers[1] = new User("12345", "pass12345");
        registeredUsers[2] = new User("27", "admin27");

        return registeredUsers;
    }

    public static User[] createUnregisteredUsersWithValidCredentials() {
        User[] unregisteredUsersWithValidCredentials = new User[21];

        // Off by a single character
        unregisteredUsersWithValidCredentials[0] = new User("20212021", "pass2020");
        unregisteredUsersWithValidCredentials[1] = new User("20212020", "pass2021");
        unregisteredUsersWithValidCredentials[2] = new User("20212020", "pass2020");
        unregisteredUsersWithValidCredentials[3] = new User("20212022 ", "pass2021");

        // Off by a special character
        unregisteredUsersWithValidCredentials[4] = new User("12345", "pass12345?");
        unregisteredUsersWithValidCredentials[5] = new User("12345", "pass123456%");
        unregisteredUsersWithValidCredentials[6] = new User("12345", "pass123456&");
        unregisteredUsersWithValidCredentials[7] = new User("12345", "pass123456_");
        unregisteredUsersWithValidCredentials[8] = new User("12345", "pass123456*");

        // Password off by a white space
        unregisteredUsersWithValidCredentials[9] = new User("12345", "pass12345 ");
        unregisteredUsersWithValidCredentials[10] = new User("12345", "pass 12345");
        unregisteredUsersWithValidCredentials[11] = new User("12345", " pass12345");

        // White spaces on the ends of the bilkent_id field are trimmed
        unregisteredUsersWithValidCredentials[12] = new User(" 12345", " pass12345");
        unregisteredUsersWithValidCredentials[13] = new User("12345 ", " pass12345");
        unregisteredUsersWithValidCredentials[14] = new User(" 12345 ", " pass12345");
        unregisteredUsersWithValidCredentials[15] = new User("  12345", " pass12345");
        unregisteredUsersWithValidCredentials[16] = new User("12345  ", " pass12345");
        unregisteredUsersWithValidCredentials[17] = new User("  12345  ", " pass12345");

        // extreme input sizes
        unregisteredUsersWithValidCredentials[18] = new User("1", "123456");
        unregisteredUsersWithValidCredentials[19] = new User("20332035", "\\_(*_*)_/11111111111111111111111111111111111111");
        unregisteredUsersWithValidCredentials[20] = new User("3", "141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825");

        return unregisteredUsersWithValidCredentials;
    }

    public static User[] createUsersWithInvalidCredentials() {
        User[] usersWithInvalidCredentials = new User[19];

        // Invalid bilkent_id and valid password
        usersWithInvalidCredentials[0] = new User("12345a", "12345a");
        usersWithInvalidCredentials[1] = new User("a12345", "12345a");
        usersWithInvalidCredentials[2] = new User("123a45", "12345a");
        usersWithInvalidCredentials[3] = new User("12345_", "12345a");
        usersWithInvalidCredentials[4] = new User("123_45", "12345a");
        usersWithInvalidCredentials[5] = new User("_12345", "12345a");
        usersWithInvalidCredentials[6] = new User("12345*", "12345a");
        usersWithInvalidCredentials[7] = new User("123*45", "12345a");
        usersWithInvalidCredentials[8] = new User("*12345a", "12345a");
        usersWithInvalidCredentials[9] = new User("12345a?", "12345a");
        usersWithInvalidCredentials[10] = new User("123?45a", "12345a");
        usersWithInvalidCredentials[11] = new User("?12345a", "12345a");
        usersWithInvalidCredentials[12] = new User("123 45", "12345a");

        // Valid bilkent_id and invalid password
        usersWithInvalidCredentials[13] = new User("12345", "12345");
        usersWithInvalidCredentials[14] = new User("12345", "1");
        usersWithInvalidCredentials[15] = new User("12345", " ");

        // Invalid bilkent_id and invalid password
        usersWithInvalidCredentials[16] = new User("12345a", "12345");
        usersWithInvalidCredentials[17] = new User("123*45", "1");
        usersWithInvalidCredentials[18] = new User("12 345", " ");


        return usersWithInvalidCredentials;
    }

}