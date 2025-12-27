package pages;

import factory.DriverFactory;

import java.util.Properties;

import static helpers.PropertiesHelper.loadAllFiles;

public class ProductPage extends DriverFactory {
    Properties setUp = loadAllFiles();

    ProductPage() {

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

}
