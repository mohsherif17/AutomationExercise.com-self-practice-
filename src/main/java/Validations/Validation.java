package Validations;

import Utils.Logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

public class Validation extends BaseAssertion{
    private static SoftAssert softAssert = new SoftAssert();
    private static boolean isAssertAllCalled = false;
    public Validation(WebDriver webDriver) {
        super(webDriver);
    }
    public Validation() {
        super();
    }


    @Override
    protected void AssertTrue(boolean condition, String message) {
        isAssertAllCalled=true;
        softAssert.assertTrue(condition, message);
    }

    @Override
    protected void AssertFalse(boolean condition, String message) {
        isAssertAllCalled=true;
        softAssert.assertFalse(condition, message);
    }

    @Override
    protected void AssertEquals(Object actual, Object expected, String message) {
        isAssertAllCalled=true;
        softAssert.assertEquals(actual, expected, message);

    }
    public static void assertAll(ITestResult result){
        if(!isAssertAllCalled){
            LogsManager.warn("No soft assertions were made. assertAll() will not be called.");
            return;
        }
        try {
            softAssert.assertAll();

        }
        catch (AssertionError e){
            LogsManager.error( "Soft Assertion failed: ", e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        }
        finally {
            softAssert = new SoftAssert();
        }
    }
}
