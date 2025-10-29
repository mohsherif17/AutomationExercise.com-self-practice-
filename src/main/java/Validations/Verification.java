package Validations;

import Drivers.GUIDriver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Verification extends BaseAssertion{
    public Verification(WebDriver webDriver) {
        super(webDriver);
    }
    public Verification() {
        super();
    }

    @Override
    protected void AssertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    protected void AssertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Override
    protected void AssertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }
}
