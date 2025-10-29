package Tests.UItest;

import Api.UserManagementApi;
import ComponanatManger.LoginSignPage;
import ComponanatManger.NavigationBarComponant;
import ComponanatManger.SignUpPage;
import Drivers.GUIDriver;

import Drivers.UITest;
import Tests.BaseTest;
import Utils.DataReader.JsonReader;

import Utils.Logs.LogsManager;
import Utils.Logs.TimeManager;
import io.qameta.allure.*;
import org.testng.annotations.*;


@Epic("Test Cases for sign up page")
@Feature("Ui user Management")
@Story("register")
@Severity(SeverityLevel.CRITICAL)
@Owner("Muhammed Sherif")
@UITest
public class RegisterTest extends BaseTest {
    GUIDriver driver;
    NavigationBarComponant navBar;
    String extension =TimeManager.getSimpleTimeStamp();
    @Test
    public void validSignupTc() {
        LogsManager.info("sign up tc started");
        new LoginSignPage(driver)
                .NavigateToLoginSignPage()
                .enterSignUpName(testData.getJsonData("name"))
                .enterSignUpEmail(testData.getJsonData("email")+ TimeManager.getSimpleTimeStamp()+"@gmail.com")
                .submitSignUpForm();
        new SignUpPage(driver).fillRegistrationForm(
                        testData.getJsonData("title"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("FName"),
                        testData.getJsonData("LName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("mobileNumber"))
                .ClickCreateAccButton().ValidateAccountCreated("ACCOUNT CREATED!");

        new UserManagementApi().deleteRegisterUserAcc(testData.getJsonData("email")+ extension+"@gmail.com",
                testData.getJsonData("password")).verifyAccDeleted();
    }

    @Test
    public void verifyErrorMessageWhenAccountCreatedBefore(){
        new UserManagementApi().createRegisterUserAcc(
                testData.getJsonData("name"),
                testData.getJsonData("email")+ extension+"@gmail.com",
                testData.getJsonData("title"),
                testData.getJsonData("password"),
                testData.getJsonData("day"),
                testData.getJsonData("month"),
                testData.getJsonData("year"),
                testData.getJsonData("FName"),
                testData.getJsonData("LName"),
                testData.getJsonData("company"),
                testData.getJsonData("address1"),
                testData.getJsonData("address2"),
                testData.getJsonData("country"),
                testData.getJsonData("state"),
                testData.getJsonData("city"),
                testData.getJsonData("zipCode"),
                testData.getJsonData("mobileNumber")).verifyAccCreated();

        new LoginSignPage(driver)
                .NavigateToLoginSignPage()
                .enterSignUpName(testData.getJsonData("name"))
                .enterSignUpEmail(testData.getJsonData("email")+ extension+"@gmail.com")
                .submitSignUpForm().VerifySignUpFailed("Email Address already exist!");;

        new UserManagementApi().deleteRegisterUserAcc(testData.getJsonData("email")+ extension+"@gmail.com",
                testData.getJsonData("password")).verifyAccDeleted();
    }



    //configuration
    @BeforeClass
    void beforeClassConditions(){
        testData = new JsonReader("Register-Data");
    }
    @BeforeMethod
    public void SetUp(){
        driver= new GUIDriver();
        navBar = new NavigationBarComponant(driver);
        navBar.navigateToHOmePAge();
        LogsManager.info("before test register test");
    }
    @AfterMethod
    public void TearDown(){
        driver.quitDriver();
        LogsManager.info("after test register test");
    }
}
