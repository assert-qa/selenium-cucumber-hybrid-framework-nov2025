package pages;

import factory.DriverFactory;
import org.openqa.selenium.By;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.*;

public class PaymentPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    PaymentPage() {

    }

    // 1. By Locators
    String nameOnCard = setUp.getProperty("NAME_ON_CARD");
    String cardNumber = setUp.getProperty("CARD_NUMBER");
    String cvc = setUp.getProperty("CVC");
    String expirationMonth = setUp.getProperty("EXPIRY_MONTH");
    String expirationYear = setUp.getProperty("EXPIRY_YEAR");
    String payAndConfirmOrderButton = setUp.getProperty("PAY_AND_CONFIRM_BUTTON");
    String verifyOrderPlacedLabel = setUp.getProperty("VERIFY_ORDER_PLACED_LABEL");
    String continueAfterSuccessOrderButton = setUp.getProperty("CONTINUE_AFTER_SUCCESS_ORDER_BUTTON");
    String downloadInvoiceButton = setUp.getProperty("DOWNLOAD_INVOICE_BUTTON");

    // 2. By Methods
    public void setNameOnCard(String name){
        setText(By.xpath(nameOnCard), name);
    }

    public void setCardNumber(String number){
        setText(By.xpath(cardNumber), number);
    }

    public void setCVC(String cvcNumber){
        setText(By.xpath(cvc), cvcNumber);
    }

    public void setExpirationMonth(String month){
        setText(By.xpath(expirationMonth), month);
    }

    public void setExpirationYear(String year){
        setText(By.xpath(expirationYear), year);
    }

    public void payAndConfirmButton(){
        clickElement(By.xpath(payAndConfirmOrderButton));
    }

    public String verifyOrderedPlaceLabel(){
        verifyElementVisible(By.cssSelector(verifyOrderPlacedLabel));

        return getElementText(By.cssSelector(verifyOrderPlacedLabel));
    }

    public void clickContinueButton(){
        clickElement(By.cssSelector(continueAfterSuccessOrderButton));
    }

    public void clickDownloadInvoiceButton(){
        clickElement(By.cssSelector(downloadInvoiceButton));
    }
}