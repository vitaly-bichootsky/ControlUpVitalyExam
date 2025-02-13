package com.automation.listeners;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Slf4j
public class TestListener implements ITestListener {

    // This method is invoked when a test starts.
    // It logs the test start and reports the step in Allure.
    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test Started: {}", result.getName());
        Allure.step("Test Started: " + result.getName());
    }

    // This method is invoked when a test is successful.
    // It logs the test success and reports the step in Allure.
    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test Passed: {}", result.getName());
        Allure.step("Test Passed: " + result.getName());
    }

    // This method is invoked when a test fails.
    // It logs the test failure and reports the step in Allure.
    @Override
    public void onTestFailure(ITestResult result) {
        log.error("Test Failed: {}", result.getName());
        Allure.step("Test Failed: " + result.getName());
    }

    // This method is invoked when a test is skipped.
    // It logs the skipped test and reports the step in Allure.
    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Test Skipped: {}", result.getName());
        Allure.step("Test Skipped: " + result.getName());
    }
}
