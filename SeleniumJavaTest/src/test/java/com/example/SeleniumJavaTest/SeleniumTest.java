package com.example.SeleniumJavaTest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;


public class SeleniumTest {

    private User[] registeredUsers = createRegisteredUsers();
    private User[] unregisteredUsersWithValidCredentials = createUnregisteredUsersWithValidCredentials();
    private User[] usersWithInvalidCredentials = createUsersWithInvalidCredentials();

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
    @DisplayName("TC-3 Check whether a captcha element is displayed after multiple failed login attempts")
    public void multipleFailureCaptchaTest() {

    }

    @Test
    @DisplayName("TC-4 Check page routing")
    public void pageRoutingTest() {

    }


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
        registeredUsers[2] = new User("27", "admin27");

        return registeredUsers;
    }

    public User[] createUnregisteredUsersWithValidCredentials(){
        User[] unregisteredUsersWithValidCredentials = new User[21];

        // Off by a single character
        unregisteredUsersWithValidCredentials[0] = new User("20212021","pass2020");
        unregisteredUsersWithValidCredentials[1] = new User("20212020","pass2021");
        unregisteredUsersWithValidCredentials[2] = new User("20212020","pass2020");
        unregisteredUsersWithValidCredentials[3] = new User("20212021 ","pass2021");

        // Off by a special character
        unregisteredUsersWithValidCredentials[4] = new User("12345","pass12345?");
        unregisteredUsersWithValidCredentials[5] = new User("12345","pass123456%");
        unregisteredUsersWithValidCredentials[6] = new User("12345","pass123456&");
        unregisteredUsersWithValidCredentials[7] = new User("12345","pass123456_");
        unregisteredUsersWithValidCredentials[8] = new User("12345","pass123456*");

        // Password off by a white space
        unregisteredUsersWithValidCredentials[9] = new User("12345","pass12345 ");
        unregisteredUsersWithValidCredentials[10] = new User("12345","pass 12345");
        unregisteredUsersWithValidCredentials[11] = new User("12345"," pass12345");

        // White spaces on the ends of the bilkent_id field are trimmed
        unregisteredUsersWithValidCredentials[12] = new User(" 12345"," pass12345");
        unregisteredUsersWithValidCredentials[13] = new User("12345 "," pass12345");
        unregisteredUsersWithValidCredentials[14] = new User(" 12345 "," pass12345");
        unregisteredUsersWithValidCredentials[15] = new User("  12345"," pass12345");
        unregisteredUsersWithValidCredentials[16] = new User("12345  "," pass12345");
        unregisteredUsersWithValidCredentials[17] = new User("  12345  "," pass12345");

        // extreme input sizes
        unregisteredUsersWithValidCredentials[18] = new User("1","123456");
        unregisteredUsersWithValidCredentials[19] = new User("20332035","\\_(*_*)_/11111111111111111111111111111111111111");
        unregisteredUsersWithValidCredentials[20] = new User("3","141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825");

        return unregisteredUsersWithValidCredentials;
    }

    public User[] createUsersWithInvalidCredentials(){
        User[] usersWithInvalidCredentials = new User[20];

        // Invalid bilkent_id and valid password
        usersWithInvalidCredentials[0] = new User("12345a","12345a");
        usersWithInvalidCredentials[1] = new User("a12345","12345a");
        usersWithInvalidCredentials[2] = new User("123a45","12345a");
        usersWithInvalidCredentials[3] = new User("12345_","12345a");
        usersWithInvalidCredentials[4] = new User("123_45","12345a");
        usersWithInvalidCredentials[5] = new User("_12345","12345a");
        usersWithInvalidCredentials[6] = new User("12345*","12345a");
        usersWithInvalidCredentials[7] = new User("123*45","12345a");
        usersWithInvalidCredentials[8] = new User("*12345a","12345a");
        usersWithInvalidCredentials[9] = new User("12345a?","12345a");
        usersWithInvalidCredentials[10] = new User("123?45a","12345a");
        usersWithInvalidCredentials[11] = new User("?12345a","12345a");
        usersWithInvalidCredentials[12] = new User("123 45","12345a");

        // Valid bilkent_id and invalid password
        usersWithInvalidCredentials[13] = new User("12345","12345");
        usersWithInvalidCredentials[14] = new User("12345","1");
        usersWithInvalidCredentials[15] = new User("12345","");

        // Invalid bilkent_id and invalid password
        usersWithInvalidCredentials[16] = new User("12345a","12345");
        usersWithInvalidCredentials[17] = new User("123*45","1");
        usersWithInvalidCredentials[18] = new User("?12345","");
        usersWithInvalidCredentials[19] = new User("12 345"," ");


        return usersWithInvalidCredentials;
    }

}
