package pages;

import factory.DriverFactory;
import keywords.WebUI;
import org.openqa.selenium.By;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;

public class MyBookingPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    public MyBookingPage(){
    }

    String bookingPage = setUp.getProperty("NAVIGATE_TO_MY_BOOKING_PAGE");

    public void goToMyBookingPage(){
        WebUI.clickElement(By.xpath(bookingPage));
    }




}
