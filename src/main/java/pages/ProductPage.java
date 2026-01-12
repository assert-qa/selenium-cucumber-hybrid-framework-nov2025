package pages;

import factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LogUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;
import static keywords.WebUI.*;

public class ProductPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    public ProductPage() {

    }

    // 1. By Locators
    String landingPageImage = setUp.getProperty("LANDING_PAGE_IMAGE");
    String productButton = setUp.getProperty("PRODUCT_BUTTON");
    String allProductsPage = setUp.getProperty("ALL_PRODUCTS_PAGE");
    String productList = setUp.getProperty("PRODUCT_LIST");
    String productInformation = setUp.getProperty("PRODUCT_INFORMATION");
    String productNameToSearch = setUp.getProperty("PRODUCT_NAME_TO_SEARCH");
    String productSearchButton = setUp.getProperty("PRODUCT_SEARCH_BUTTON");
    String searchProductLabel = setUp.getProperty("SEARCHED_PRODUCT_LABEL");
    String verifySearchedProductIsVisible = setUp.getProperty("VERIFY_SEARCHED_PRODUCT_IS_VISIBLE");
    String continueShoppingButton = setUp.getProperty("CONTINUE_SHOPPING_BUTTON");
    String addQuantity = setUp.getProperty("ADD_QUANTITY");
    String addToCartButton = setUp.getProperty("ADD_PRODUCT_TO_CART_BUTTON");
    String viewCartButton_1 = setUp.getProperty("VIEW_CART_1");
    String viewCartButton_2 = setUp.getProperty("VIEW_CART_2");
    String brandLabel = setUp.getProperty("BRAND_LABEL");
    String brandProducts = setUp.getProperty("BRAND_PRODUCT");
    String brandPageAndProductIsVisible = setUp.getProperty("BRAND_PAGE_AND_PRODUCT_IS_VISIBLE");
    String brandAndProductListPage = setUp.getProperty("BRAND_AND_PRODUCT_LIST_PAGE");
    String writeYourReviewLabelIsVisible = setUp.getProperty("WRITE_YOUR_REVIEW_LABEL_IS_VISIBLE");
    String reviewerName_1 = setUp.getProperty("REVIEWER_NAME_1");
    String reviewerName_2 = setUp.getProperty("REVIEWER_NAME_2");
    String reviewerEmail_1 = setUp.getProperty("REVIEWER_EMAIL_1");
    String reviewerEmail_2 = setUp.getProperty("REVIEWER_EMAIL_2");
    String addReview_1 = setUp.getProperty("ADD_REVIEW_1");
    String addReview_2 = setUp.getProperty("ADD_REVIEW_2");
    String submitReview_1 = setUp.getProperty("SUBMIT_REVIEW_BUTTON_1");
    String submitReview_2 = setUp.getProperty("SUBMIT_REVIEW_BUTTON_2");
    String reviewSuccessMessage = setUp.getProperty("REVIEW_SUCCESS_MESSAGE");

    // 2. By Methods
    public boolean verifyLandingPage(){
        return isElementDisplayed(By.xpath(landingPageImage));
    }

    public void goToProductPage(){
        clickElement(By.xpath(productButton));
    }

    public String verifyNavigateToAllProductsPage(){
        if(isElementDisplayed(By.xpath(allProductsPage))){
            return getElementText(By.xpath(allProductsPage));
        }else{
            return "ALL PRODUCTS is not visible";
        }
    }

    public void verifyProductListIsVisible(){
        verifyElementVisible(By.cssSelector(productList));
        List<WebElement> products = getWebElements(By.cssSelector(productList));

        if (!products.isEmpty()){
            LogUtils.info("Products list is visible, total of products: " + products.size());
        }else{
            LogUtils.info("Product list is not visible");
            throw new AssertionError("Product list is not visible.");
        }
    }

    public int getProductOrderNumber(){
        verifyElementVisible(By.cssSelector(productList));
        List<WebElement> products = getWebElements(By.cssSelector(productList));

        return products.size();
    }

    public String getProductName(int productId) {
        List<WebElement> list = getWebElements(By.cssSelector(productList));

        for(int i = 0; i < list.size(); i++){
            if(i == productId){
                String productDetails = list.get(i).getText();
                String [] lines = productDetails.split("\n");

                if(lines.length > 1){
                    LogUtils.info("#DEBUG -> ProductID order: " + i + " - Product name: " + lines[1]);
                    return lines[1]; // take product's name
                }
            }
        }
        return "Product not found";
    }

    public String generateElementViewProductButton(String productName) {
        String hrefValue = "";
        List<WebElement> products = getWebElements(By.cssSelector(productList));

        for (int i = 0; i < products.size(); i++) {
            String productDetails = products.get(i).getText(); // try to get product name
            String[] lines = productDetails.split("\n"); // split data

            if (lines.length > 1 && lines[1].equalsIgnoreCase(productName)) {
                // Try to get element <a> directly from element product to get View Product button
                List<WebElement> linkElements = products.get(i).findElements(By.cssSelector(".choose a"));

                if (linkElements.isEmpty()) {
                    LogUtils.info("No link found for product: " + lines[1]);
                    continue;
                }

                hrefValue = linkElements.getFirst().getAttribute("href"); // get view product element

                // get element (product_details/product index)
                assert hrefValue != null;
                String [] parts = hrefValue.split("product_details/");
                if (parts.length > 1){
                    hrefValue = "product_details/" + parts[1];
                }

                LogUtils.info("{Product number: " + i + " - Product name: " + lines[1] + " - Extracted href: " + hrefValue + "}\n");
                break;
            }
        }
        return hrefValue;
    }

    public void viewProductButton(String productIndex){
        String css = String.format("[href='/%s']", productIndex); // dynamically find element view product based on product index (extracted href)
        LogUtils.info("css generated: " + css);
        verifyElementVisible(By.cssSelector(css));
        WebElement element = getWebElement(By.cssSelector(css));

        scrollToElement(element, "false"); // true means scroll to top, false means scroll to bottom
        clickElement(By.cssSelector(css));
    }

    public boolean verifyProductInformationIsVisible(){
        return isElementDisplayed(By.xpath(productInformation));
    }

    public void findProduct(String productName){
        setText(By.cssSelector(productNameToSearch), productName);

        clickElement(By.cssSelector(productSearchButton));
    }

    public String verifyIfSearchProductIsVisible(){
        verifyElementVisible(By.cssSelector(searchProductLabel));
        return getElementText(By.cssSelector(searchProductLabel));
    }

    public void verifySearchProductIsRelatedToSearchAreVisible(String searchKeyword){
        List<WebElement> products = getWebElements(By.xpath(verifySearchedProductIsVisible));
        String message = "";
        boolean isValid = true;

        if(products.isEmpty()){
            message = "❌ There are no product found";
            LogUtils.info("Verify search product's message: " + message);
            return;
        }

        String normalizeSearchKeyword = searchKeyword.toLowerCase().replaceAll("[-\\s]", ""); //t-shirt, t shirt, tshirt

        for (WebElement product :products){
            String productText = product.getText().toLowerCase();
            LogUtils.info("Product: " + productText);

            // delete "-" and space
            String normalizeProductText = productText.replaceAll("[-\\s]", "");

            if(!normalizeProductText.contains(normalizeSearchKeyword)){
                isValid = false;
            }
        }

        if(isValid){
            message = "✔ All products are displayed according to the keywords: " + searchKeyword;
        }else{
            message = "❌ The products that appear do not match the search keywords!";
        }

        LogUtils.info("Verify search product's message: " + message);
    }

    public String getProductIndex(String productName){
        // get product index / id by product name
        String productIndex = "";
        List<WebElement> products = getWebElements(By.cssSelector(productList));

        for (int i = 0; i < products.size(); i++) {
            String productDetails = products.get(i).getText(); // try to get product name
            String[] lines = productDetails.split("\n"); // split data

            if (lines.length > 1 && lines[1].equalsIgnoreCase(productName)) {
                // Try to get element <a> directly from element product to get Add to Cart button
                List<WebElement> linkElements = products.get(i).findElements(By.cssSelector(".single-products a"));

                if (linkElements.isEmpty()) {
                    LogUtils.error("No link found for product: " + lines[1]);
                    continue;
                }

                productIndex = linkElements.getFirst().getAttribute("data-product-id"); // get data-product-id

                assert productIndex != null;

                LogUtils.info("{Product number: " + i + " - Product name: " + lines[1] + " - Extracted data-product-id: " + productIndex + "}\n");
                break;
            }
        }
        return productIndex;
    }

    public void addToCartButton(String productIndex){
        String xpath = String.format("(//a[@data-product-id='%s'])[1]", productIndex);
        LogUtils.info("xpath generated: " + xpath);

        verifyElementVisible(By.xpath(xpath));
        WebElement element = getWebElement(By.xpath(xpath));

        scrollToElement(element, "true");

        clickElement(By.xpath(xpath));
    }

    public void successfullyAddedModalButton(){
        clickElement(By.xpath(continueShoppingButton));
    }

    // ADD QUANTITY IN PRODUCT DETAIL TEST STEP
    public void addQuantityProduct(String value){
        setText(By.id(addQuantity), value);
    }

    public void addToCartButton(){
        clickElement(By.xpath(addToCartButton));
    }

    public void viewCart(){
        if (isElementDisplayed(By.xpath(viewCartButton_1))){
            clickElement(By.xpath(viewCartButton_1));
        }else{
            clickElement(By.xpath(viewCartButton_2));
        }
    }

    // VIEW & CART BRAND PRODUCTS TEST STEP
    public String verifyIfBrandLabelIsVisible(){
        verifyElementVisible(By.xpath(brandLabel));
        return getElementText(By.xpath(brandLabel));
    }

    public int getBrandProductCountNumber(){
        verifyElementVisible(By.xpath(brandProducts));
        List<WebElement> products = getWebElements(By.xpath(brandProducts));

        return products.size();
    }

    public String getProductBrandsName(int productBrandsOrder){
        List<WebElement> list = getWebElements(By.xpath(brandProducts));

        for(int i = 0; i < list.size(); i++){
            if(i == productBrandsOrder){
                String productDetails = list.get(i).getText();
                String [] lines = productDetails.split("\n");

                if(lines.length > 1){
                    LogUtils.info("Brand ID order: " + i);
                    return lines[1]; // take brand's name
                }
                //LogUtils.info(i + ". " + list.get(i).getText());
            }
        }
        return "Product not found";
    }

    public String generateElementClickBrandProduct(String brandName){
        String hrefValue = "";
        List<WebElement> brands = getWebElements(By.xpath(brandProducts));

        for (int i = 0; i < brands.size(); i++) {
            String brandDetails = capitalizeFirstLetter(brands.get(i).getText()); // try to get brand name
            // LogUtils.info("#DEBUG (BRAND DETAILS) => " + brandDetails);
            String[] lines = brandDetails.split("\n"); // split data

            if (lines.length > 1 && capitalizeFirstLetter(lines[1]).equalsIgnoreCase(brandName)) {
                // LogUtils.info("#DEBUG (BRAND NAME) => " + lines[1]);

                // Try to get element <a> directly from element brand to get element per brands
                List<WebElement> linkElements = brands.get(i).findElements(By.cssSelector(".brands-name a"));

                if (linkElements.isEmpty()) {
                    LogUtils.info("No link found for product: " + lines[1]);
                    continue;
                }

                hrefValue = linkElements.getFirst().getAttribute("href"); // get href element

                // get element (/brand_products/brand_index)
                assert hrefValue != null;
                String [] parts = hrefValue.split("/brand_products/");
                if (parts.length > 1){
                    hrefValue = "/brand_products/" + parts[1];
                }

                LogUtils.info("#DEBUG => {Brand number: " + i + " - Brand name: " + lines[1] + " - Extracted href: " + hrefValue + "}\n");
                break;
            }
        }
        return decodeURL(hrefValue);
    }

    public void clickBrandProduct(String brandProduct){
        String xpath = String.format("//a[@href='%s']", brandProduct);
        verifyElementVisible(By.xpath(xpath));

        WebElement element = getWebElement(By.xpath(xpath));
        scrollToElement(element, "true");

        clickElement(By.xpath(xpath));
    }

    public String verifyBrandPageAndProductsIsVisible(){
        verifyElementVisible(By.xpath(brandPageAndProductIsVisible));

        return getElementText(By.xpath(brandPageAndProductIsVisible));
    }

    public void verifyThatUserIsNavigatedToBrandPageAndCanSeeProducts(String brandName) {
        List<WebElement> productList = getWebElements(By.cssSelector(brandAndProductListPage));

        if (productList.isEmpty()) {
            LogUtils.error("No products found for brand: " + brandName);
            return;
        }

        int productCount = 0;
        LogUtils.info("\nThere are " + productList.size() + " product(s) with brand " + brandName + ":");

        for (WebElement product : productList) {
            String productName = product.getText().trim();
            String[] lines = productName.split("\n"); // split data

            if (!productName.isEmpty()) {
                productCount++;
                LogUtils.info("Product #" + productCount + ": " + lines[1]);
            }
        }

        if (productCount == 0) {
            LogUtils.info("No valid products found for brand: " + brandName);
        }
    }

    // ADD REVIEW TO PRODUCT TEST STEP
    public String verifyWriteYourReviewIsVisible(){
        verifyElementVisible(By.xpath(writeYourReviewLabelIsVisible));

        return getElementText(By.xpath(writeYourReviewLabelIsVisible));
    }

    public void setReviewerName(String name){
        if (isElementDisplayed(By.xpath(reviewerName_1))){
            setText(By.xpath(reviewerName_1), name);
        }else{
            setText(By.xpath(reviewerName_2), name);
        }
    }

    public void setReviewerEmail(String email){
        if (isElementDisplayed(By.xpath(reviewerEmail_1))){
            setText(By.xpath(reviewerEmail_1), email);
        }else{
            setText(By.xpath(reviewerEmail_2), email);
        }
    }

    public void setReview(String review){
        if (isElementDisplayed(By.xpath(addReview_1))){
            setText(By.xpath(addReview_1), review);
        }else{
            setText(By.xpath(addReview_2), review);
        }
    }

    public void submitReviewButton(){
        if (isElementDisplayed(By.xpath(submitReview_1))){
            clickElement(By.xpath(submitReview_1));
        }else{
            clickElement(By.xpath(submitReview_2));
        }
    }

    public String verifySuccessfullySubmitReview() {
        verifyElementVisible(By.xpath(reviewSuccessMessage));

        sleep(1000);
        return getElementText(By.xpath(reviewSuccessMessage));
    }

    // ROUGH (SUPPORT METHODS)
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String[] words = str.split(" ");
        StringBuilder capitalizedStr = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedStr.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" "); // Add space back
            }
        }
        return capitalizedStr.toString().trim(); // Trim trailing space
    }

    /*to decode href result e.g Mast%20&%20Harbour -> Mast & Harbour */
    public String decodeURL(String encodedString){
        return URLDecoder.decode(encodedString, StandardCharsets.UTF_8);
    }
}