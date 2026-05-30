package steps.ui;

import hooks.TestContext;
import io.cucumber.java.en.Then;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.EventPage;
import utils.LogUtils;

import java.util.List;

public class StepsFilterEventCombination {
    private TestContext testContext;
    private EventPage eventPage;

    public StepsFilterEventCombination(TestContext testContext) {
        this.testContext = testContext;
        this.eventPage = new EventPage();
    }

    public StepsFilterEventCombination() {
        this(new TestContext());
    }

    @Then("I should see {string} events in {string}")
    public void i_should_see_events_in(String expectedCategory, String expectedCity) {
        List<WebElement> eventList = eventPage.getEvents();

        for (WebElement event : eventList) {
            String actualCategory = event.findElement(By.cssSelector("span.inline-flex")).getText().trim();

            String actualLocation = event.findElement(By.xpath(".//*[contains(text(),'" + expectedCity + "')]")).getText().trim();

            WebUI.verifyEquals(actualCategory, expectedCategory);
            WebUI.verifyEquals(actualLocation, expectedCity);
        }
    }
}
