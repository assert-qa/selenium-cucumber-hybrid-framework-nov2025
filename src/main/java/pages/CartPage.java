package pages;

import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.LogUtils;

import java.util.*;

import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.*;

public class CartPage extends DriverFactory {
    WebDriver driver;

    private static Properties setUp = loadAllFiles();

    // 1. Constructor of the page class
    public CartPage(WebDriver driver){
        this.driver = driver;
    }

    // 2. By Locators
    String cartPage= setUp.getProperty("CART_PAGE");
    String footer = setUp.getProperty("CART_FOOTER");
    String subscriptionLabel = setUp.getProperty("CART_SUBSCRIPTION_LABEL");
    String inputSubscribeEmail = setUp.getProperty("INPUT_SUBSCRIBE_EMAIL");
    String subscribeButton = setUp.getProperty("CART_SUBSCRIBE_BUTTON");
    String successAlertMessage = setUp.getProperty("SUCCESS_ALERT_MESSAGE");
    String cartTableData = setUp.getProperty("CART_TABLE_DATA");
    String verifyShoppingCartPage = setUp.getProperty("VERIFY_SHOPPING_CART_PAGE");
    String proceedToCheckoutButton = setUp.getProperty("PROCEED_TO_CHECKOUT");
    String verifyCheckOutPopUp = setUp.getProperty("VERIFY_CHECKOUT_POP_UP");
    String registerOrLogin = setUp.getProperty("REGISTER_OR_LOGIN");
    String verifyAddressDetail = setUp.getProperty("VERIFY_ADDRESS_DETAIL");
    String verifyReviewYourOrder = setUp.getProperty("VERIFY_REVIEW_YOUR_ORDER");
    String typeCommentBeforeCheckout = setUp.getProperty("TYPE_COMMENT_BEFORE_CHECKOUT");
    String placeOrderButton = setUp.getProperty("PLACE_ORDER_BUTTON");
    String deleteProductButton = setUp.getProperty("DELETE_PRODUCT_BUTTON");
    String cartProductRow = setUp.getProperty("CART_PRODUCT_NOW");

    // 3. Page actions: features (behaviors) of the page in the form of methods
    public void goToCartPage(){
        clickElement(By.xpath(cartPage));
    }

    public void scrollDown(){
        scrollToElement(By.cssSelector(footer));
    }

    public String verifySubscriptionLabelIsVisible(){
        return getElementText(By.xpath(subscriptionLabel));
    }

    public void enterEmail(String userEmail){
        setText(By.id(inputSubscribeEmail), userEmail);
    }

    public void subscribeButton(){
        clickElement(By.xpath(subscribeButton));
    }

    public String verifySuccessMessage(){
        verifyElementVisible(By.xpath(successAlertMessage));
        return getElementText(By.xpath(successAlertMessage));
    }

    // VERIFY ADDED PRODUCTS IN CART
    public void verifyAddedProductInCartVisible(String[] addedProduct){
        Set<String> foundProducts = new HashSet<>();

        List<WebElement> cartTable = getWebElements(By.xpath(cartTableData));

        if (cartTable.isEmpty()){
            LogUtils.warn("❌ Cart is empty! No products found.");
            return;
        }

        LogUtils.info("\uD83D\uDD0D Checking products in the cart...");

        for (WebElement rows : cartTable){
            List<WebElement> rowData = rows.findElements(By.tagName("td"));

            if (rowData.size() > 1){
                String productDescription = rowData.get(1).getText().trim();
                for (String product : addedProduct){
                    if (productDescription.contains(product)){
                        foundProducts.add(product);
                    }
                }
            }
        }

        List<String> missingProducts = Arrays.stream(addedProduct)
                .filter(product -> !foundProducts.contains(product))
                .toList();

        if (missingProducts.isEmpty()){
            LogUtils.info("✅ All added products are present in the cart: " + String.join(", ", addedProduct));
        } else {
            LogUtils.error("❌ Missing products in the cart: " + String.join(", ", missingProducts));
        }
    }

    // VERIFY PRICES, QUANTITY, and TOTAL PRICE ADDED PRODUCT
    // (PRICES * QUANTITY = TOTAL PRICE)
    public void verifyPriceQuantityTotalProduct(){
        List<WebElement> cartTable = getWebElements(By.xpath(cartTableData));

        for (WebElement data : cartTable){
            List<WebElement> dataPerRows = data.findElements(By.tagName("td"));

            String columnPrice = dataPerRows.get(2).getText().trim().replace("Rs. ", "");
            String columnQuantity = dataPerRows.get(3).getText().trim();
            String columnTotal = dataPerRows.get(4).getText().trim().replace("Rs. ", "");

            int expectedTotal = Integer.parseInt(columnPrice) * Integer.parseInt(columnQuantity);

            assert Integer.parseInt(columnTotal) == expectedTotal;
            LogUtils.info("✅ Price, Quantity, and Total for the product are correct: " +
                    "Price = Rs. " + columnPrice +
                    ", Quantity = " + columnQuantity +
                    ", Total = Rs. " + columnTotal);
        }
    }

    // VERIFY THE PRODUCT DISPLAYED IN CART WITH EXACT QUANTITY
    public int verifyDisplayedQuantityInCart(){
        String columnQuantity = "";
        List<WebElement> cartTable = getWebElements(By.xpath(cartTableData));

        for (WebElement data : cartTable){
            List<WebElement> dataPerRows = data.findElements(By.tagName("td"));
            columnQuantity = dataPerRows.get(3).getText().trim();
        }
        return Integer.parseInt(columnQuantity);
    }

    // PLACE ORDER REGISTER WHILE CHECKOUT
    public String verifyCartPage(){
       waitForElementVisible(By.cssSelector(verifyShoppingCartPage));
       return getElementText(By.cssSelector(verifyShoppingCartPage));
    }

    public void proceedToCheckout(){
        clickElement(By.xpath(proceedToCheckoutButton));
    }

    public String verifyCheckoutPopUpIsVisible(){
        waitForElementVisible(By.xpath(verifyCheckOutPopUp));
        return getElementText(By.xpath(verifyCheckOutPopUp));
    }

    public void registerOrLogin(){
        clickElement(By.xpath(registerOrLogin));
    }

    public String verifyAddressDetailIsVisible(){
        waitForElementVisible(By.xpath(verifyAddressDetail));
        return getElementText(By.xpath(verifyAddressDetail));
    }

    public String verifyReviewYourOrderIsVisible(){
        waitForElementVisible(By.xpath(verifyReviewYourOrder));
        return getElementText(By.xpath(verifyReviewYourOrder));
    }

    public void setTypeCommentBeforeCheckout(String comment){
        setText(By.xpath(typeCommentBeforeCheckout), comment);
    }

    public void placeOrderButton(){
        clickElement(By.cssSelector(placeOrderButton));
    }


    // REMOVE PRODUCT FROM CART
    public void clickRemoveBackgroundFromCart(){
        clickElement(By.cssSelector(deleteProductButton));
    }

    // ADD ITEM FROM RECOMMENDED ITEM
    public boolean verifyIfAddedItemFromRecommendedItemsInCart(){
        return isElementDisplayed(By.cssSelector(cartProductRow));
    }

}
