package common;

import constants.ConstantGlobal;
import factory.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.LogUtils;

public class BaseTest {

    public static void createDriver(){
        WebDriver driver = setupBrowser(ConstantGlobal.BROWSER);
        DriverManager.setDriver(driver);
    }

    public static void createDriver(String browser){
        WebDriver driver = setupBrowser(browser);
        DriverManager.setDriver(driver);
    }

    public static WebDriver setupBrowser(String browserName){
        return switch (browserName.trim().toLowerCase()) {
            case "chrome" -> initChromeDriver();
            case "firefox" -> initFirefoxDriver();
            case "edge" -> initEdgeDriver();
            default -> throw new IllegalArgumentException("Browser " + browserName + " not supported");
        };
    }

    public static WebDriver initChromeDriver(){
        WebDriver driver;
        LogUtils.info("Launching Chrome browser...");

        ChromeOptions options = new ChromeOptions();
        if (ConstantGlobal.HEADLESS){
            options.addArguments("--headless=new");
            options.addArguments("window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new ChromeDriver(options);
        return driver;
    }

    public static WebDriver initFirefoxDriver(){
        WebDriver driver;
        LogUtils.info("Launching Firefox browser...");

        FirefoxOptions options = new FirefoxOptions();
        if (ConstantGlobal.HEADLESS){
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new FirefoxDriver(options);
        return driver;
    }

    public static WebDriver initEdgeDriver(){
        WebDriver driver;
        LogUtils.info("Launching Edge browser...");

        EdgeOptions options = new EdgeOptions();
        if (ConstantGlobal.HEADLESS){
            options.addArguments("--headless=new");
            options.addArguments("window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new EdgeDriver(options);
        return driver;
    }

}
