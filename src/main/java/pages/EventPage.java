package pages;

import factory.DriverFactory;
import keywords.WebUI;
import org.openqa.selenium.By;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;

public class EventPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    public EventPage(){
    }

    String eventPage = setUp.getProperty("NAVIGATE_TO_EVENT_PAGE");
    String inputEvent = setUp.getProperty("SEARCH_EVENT_INPUT");


    public void goToEventPage(){
        WebUI.clickElement(By.xpath(eventPage));
    }

    public void searchEvent(String eventName){
        WebUI.setText(By.xpath(inputEvent), eventName);
    }





}
