package steps;

import hooks.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import keywords.WebUI;
import pages.ProductPage;
import reports.ExtentTestManager;
import utils.LogUtils;

import java.util.List;

public class StepsProductDetailPage {
    private TestContext testContext;
    private ProductPage productPage;

    public StepsProductDetailPage(TestContext testContext) {
        this.testContext = testContext;
        this.productPage = testContext.getProductPage();
    }
    public StepsProductDetailPage() {
        this(new TestContext());
    }

    @Then("I verify user is navigated to All Products page successfully")
    public void iVerifyUserIsNavigatedToAllProductsPageSuccessfully() {
        String actualResult = productPage.verifyNavigateToAllProductsPage();
        WebUI.verifyEquals(actualResult, "All Products", "User is not navigated to All Products page");
        ExtentTestManager.logMessage("Verified: User is navigated to ALL PRODUCTS page");
    }

    @Then("The products list is visible")
    public void theProductsListIsVisible() {
        productPage.verifyProductListIsVisible();
        ExtentTestManager.logMessage("Verified: Products list is visible");
    }

    @When("I click on 'View Product' of first product")
    public void iClickOnViewProductOfFirstProduct() {
        // Get first product name (index 0)
        String firstProductName = productPage.getProductName(0);
        LogUtils.info("First product name: " + firstProductName);

        // Generate element for view product button
        String productIndex = productPage.generateElementViewProductButton(firstProductName);

        // Click view product button
        productPage.viewProductButton(productIndex);
        ExtentTestManager.logMessage("Clicked on 'View Product' of first product: " + firstProductName);
    }

    @Then("User is landed to product detail page")
    public void userIsLandedToProductDetailPage() {
        WebUI.verifyTrue(productPage.verifyProductDetailPageIsVisible(), "Product detail page is not visible");
        ExtentTestManager.logMessage("Verified: User is landed to product detail page");
    }

    @Then("I verify that product detail is visible:")
    public void iVerifyThatProductDetailIsVisible(DataTable dataTable) {
        List<String> details = dataTable.asList();

        for (String detail : details) {
            boolean isVisible = productPage.isProductDetailVisible(detail);
            WebUI.verifyTrue(isVisible, detail + " is not visible in product detail page");
        }

        ExtentTestManager.logMessage("Verified: All product details are visible");
    }
}
