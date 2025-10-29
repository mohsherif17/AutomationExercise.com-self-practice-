package Validations;

import Utils.Actions.ElementActionManger;
import Utils.WaitManger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseAssertion {
    protected WebDriver webDriver;
    protected WaitManger waitManger;
    protected ElementActionManger elementActionManger;
public BaseAssertion(){

}
    public BaseAssertion(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.waitManger = new WaitManger(webDriver);
        this.elementActionManger = new ElementActionManger(webDriver);
    }

    protected abstract void AssertTrue(boolean condition, String message);

    protected abstract void AssertFalse(boolean condition, String message);

    protected abstract void AssertEquals(Object actual, Object expected, String message);

    public void equals(Object actual, Object expected, String message) {
        AssertEquals(actual, expected, message);
    }

    public void assertDisplayed(By locator) {
        waitManger.getFluentWait().until(d -> {
            try {
                d.findElement(locator).isDisplayed();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        AssertTrue(webDriver.findElement(locator).isDisplayed(), "Element located by " + locator.toString() + " is not displayed.");
    }
    //assert page Url
    public void assertPageUrl(String expectedUrl) {
        String actualUrl = webDriver.getCurrentUrl();
        AssertEquals(actualUrl, expectedUrl, "Expected URL: " + expectedUrl + ", but found: " + actualUrl);
    }
}
