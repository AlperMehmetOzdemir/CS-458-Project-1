package com.mehmet.selenium.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class DemoTest {


    public static void main(String[] args)
    {
        System.setProperty("webdriver.chrome.driver", "D:\\School\\Summer 2021 CS-458\\Projects\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
//        driver.get("https://srs-login-page.herokuapp.com/");
        driver.get("https://stars.bilkent.edu.tr/accounts/login");
        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());
        driver.findElement(By.id("LoginForm_username")).sendKeys("21501955");
        driver.findElement(By.xpath("//*[contains(@id, 'LoginForm-p')]")).sendKeys("001997amo");
        WebElement loginButton = driver.findElement(By.className("btn-bilkent"));
        System.out.println( loginButton.getCssValue("color"));
        loginButton.click();
        //        driver.quit();
    }

}