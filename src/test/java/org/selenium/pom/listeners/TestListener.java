package org.selenium.pom.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log("Test failed: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Reporter.log("Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not implemented
    }

    @Override
    public void onStart(ITestContext context) {
        Reporter.log("Test suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        Reporter.log("Test suite finished: " + context.getName());
    }
}
