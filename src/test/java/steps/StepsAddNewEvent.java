package steps;

import hooks.TestContext;
import io.cucumber.java.en.Then;
import pages.EventPage;

public class StepsAddNewEvent {
    private TestContext testContext;
    private EventPage eventPage;

    public StepsAddNewEvent(TestContext testContext) {
        this.testContext = testContext;
        this.eventPage = new EventPage();
    }

    public StepsAddNewEvent() {
        this(new TestContext());
    }

    @Then("I should be redirected to event creation page")
    public void i_should_be_redirected_to_event_creation_page() {
        eventPage.createEventPage();
    }


}
