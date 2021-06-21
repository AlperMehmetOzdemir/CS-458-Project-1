package com.example.SeleniumJavaTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class SeleniumTest {

    private User[] registeredUsers = createRegisteredUsers();
    private User[] unregisteredUsersWithValidCredentials = createUnregisteredUsersWithValidCredentials();
    private User[] unregisteredUsersWithInvalidCredentials = createUnregisteredUsersWithInvalidCredentials();

    private WebDriver chromeDriver = new ChromeDriver();
    private WebDriver firefoxDriver = new FirefoxDriver();
    private WebDriver operaDriver = new OperaDriver();


    @BeforeAll
    public static void setUpAll() {

    }

    @BeforeEach
    public void setUp() {

    }

    @Test
    @DisplayName("TC-1 Check input validation")
    public void inputValidationTest() {

    }

    @Test
    @DisplayName("TC-2 Check browser support")
    public void browserSupportTest() {

    }

    @Test
    @DisplayName("TC-3 Check page routing ")
    public void pageRoutingTest() {

    }

    //    @Test
//    @DisplayName("TC-1 Check input validation")
//    public void inputValidationTest() {
//
//    }
//    @Test
//    @DisplayName("TC-1 Check input validation")
//    public void inputValidationTest() {
//
//    }

    @AfterEach
    public void tearDown(){

    }

    @AfterAll
    public static void tearDownAll(){

    }

    public User[] createRegisteredUsers(){
        User[] registeredUsers = new User[3];

        registeredUsers[0] = new User("20212021", "pass2021");
        registeredUsers[1] = new User("12345", "pass12345");
        registeredUsers[2] = new User("admin27", "pass27");

        return registeredUsers;
    }

    public User[] createUnregisteredUsersWithValidCredentials(){
        User[] unregisteredUsersWithValidCredentials = new User[10];

        unregisteredUsersWithValidCredentials[0] = new User("","");


        return unregisteredUsersWithValidCredentials;
    }

    public User[] createUnregisteredUsersWithInvalidCredentials(){
        User[] unregisteredUsersWithInvalidCredentials = new User[10];

        unregisteredUsersWithInvalidCredentials[0] = new User("","");


        return unregisteredUsersWithInvalidCredentials;
    }

}
