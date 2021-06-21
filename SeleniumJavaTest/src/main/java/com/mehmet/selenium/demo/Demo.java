package com.mehmet.selenium.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Demo {

    public static void main(String[] args)
    {
        System.setProperty("webdriver.chrome.driver", "D:\\School\\Summer 2021 CS-458\\Projects\\chromedriver_win32\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("hhtp://www.google.com");
        webDriver.quit();
    }



}
