package pages;

import factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LogUtils;

import java.util.List;
import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.getWebElements;

public class CheckoutPage extends DriverFactory {

    private static Properties setUp = loadAllFiles();

    // 1. By Locators
    String deliveryAddress = setUp.getProperty("DELIVERY_ADDRESS");

    // 2. Page actions: features (behaviors) of the page in the form of methods
    public boolean verifyTheDeliveryAddressFilledAtTheTimeRegistration(
            String title, String first_name, String last_name, String company,
            String address, String address2, String country, String state, String city,
            String zipCode, String mobile_phone
    ){
        String full_name = title + " " + first_name + " " + last_name;
        String full_address3 = city + " " + state + " " + zipCode;

        List<WebElement> addressElements = getWebElements(By.xpath(deliveryAddress));

        if (addressElements.isEmpty()){
            LogUtils.error("Delivery address section not found!");
            return false;
        }

        List<WebElement> dataPerLine = addressElements.getFirst().findElements(By.tagName("li"));

        if (dataPerLine.size() < 8){
            LogUtils.error("Not enough address fields found!");
            return false;
        }

        String[] expectedValues = {full_name, company, address, address2, full_address3, country, mobile_phone};

        for (int i = 1; i <= 7; i++){
            if (!dataPerLine.get(i).getText().trim().contains(expectedValues[i-1])){
                LogUtils.error("Mismatch found in line " + i + ": Expected '" + expectedValues[i - 1] +
                        "', Found '" + dataPerLine.get(i).getText().trim() + "'");
                return false;
            }
        }
        return true;
    }

    public boolean verifyTheBillingAddressFilledAtTheTimeRegistration(
            String title, String first_name, String last_name, String company,
            String address, String address2, String country, String state, String city,
            String zipCode, String mobile_phone
    ){
        String full_name = title + " " + first_name + " " + last_name;
        String full_address3 = city + " " + state + " " + zipCode;

        List<WebElement> addressElements = getWebElements(By.xpath(deliveryAddress));

        if (addressElements.isEmpty()){
            LogUtils.error("Delivery address section not found!");
            return false;
        }

        List<WebElement> dataPerLine = addressElements.getFirst().findElements(By.tagName("li"));

        if (dataPerLine.size() < 8){
            LogUtils.error("Not enough address fields found!");
            return false;
        }

        String [] expectedValues = {full_name, company, address, address2, full_address3, country, mobile_phone};

        for (int i = 1; i <= 7; i++){
            if (!dataPerLine.get(i).getText().trim().contains(expectedValues[i-1])){
                LogUtils.error("Mismatch found in line " + i + ": Expected '" + expectedValues[i - 1] +
                        "', Found '" + dataPerLine.get(i).getText().trim() + "'");
                return false;
            }
        }
        return true;
    }

}
