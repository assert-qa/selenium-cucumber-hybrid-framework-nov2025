package hooks;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import utils.LogUtils;

public class CucumberReportListener implements ConcurrentEventListener {
    private static final String SEPARATOR = "=".repeat(40);
    private static final String SUB_SEPARATOR = "-".repeat(40);

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, this::handleTestRunStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
    }

    private void handleTestRunStarted(TestRunStarted event) {
        LogUtils.info("\n" + SEPARATOR);
        LogUtils.info("TEST RUN STARTED");
        LogUtils.info("Time: " + event.getInstant());
        LogUtils.info(SEPARATOR);
    }

    private void handleTestRunFinished(TestRunFinished event) {
        LogUtils.info("\n" + SEPARATOR);
        LogUtils.info("TEST RUN FINISHED");
        LogUtils.info("Time: " + event.getInstant());
        LogUtils.info(SEPARATOR + "\n");
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        TestCase testCase = event.getTestCase();
        LogUtils.info("\nTEST CASE STARTED: " + testCase.getName());
        LogUtils.info("  Feature: " + testCase.getUri());
        LogUtils.info("  Line: " + testCase.getLocation().getLine());
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        TestCase testCase = event.getTestCase();
        Status status = event.getResult().getStatus();

        LogUtils.info("\nTEST CASE FINISHED: " + testCase.getName());
        LogUtils.info("  Status: " + status.name());
        LogUtils.info("  Duration: " + event.getResult().getDuration().toMillis() + " ms");

        if (status == Status.FAILED) {
            Throwable error = event.getResult().getError();
            if (error != null) {
                LogUtils.error("  Error: " + error.getMessage());
            }
        }
        LogUtils.info(SUB_SEPARATOR);
    }

    private void handleTestStepStarted(TestStepStarted event) {
        TestStep testStep = event.getTestStep();
        if (testStep instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) testStep;
            LogUtils.info("  Step: " + pickleStep.getStep().getText());
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        TestStep testStep = event.getTestStep();
        Status status = event.getResult().getStatus();

        if (testStep instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) testStep;
            String stepText = pickleStep.getStep().getText();

            LogUtils.info("  Step Completed: " + stepText);
            LogUtils.info("    Status: " + status.name());
            LogUtils.info("    Duration: " + event.getResult().getDuration().toMillis() + " ms");

            if (status == Status.FAILED) {
                Throwable error = event.getResult().getError();
                if (error != null) {
                    LogUtils.error("    Error: " + error.getMessage());
                }
            }
        }
    }
}
