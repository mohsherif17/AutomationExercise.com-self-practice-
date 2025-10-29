package Tests.UItest;

import Api.UserManagementApi;
import ComponanatManger.LoginSignPage;
import ComponanatManger.NavigationBarComponant;
import Drivers.GUIDriver;
import Drivers.UITest;
import Tests.BaseTest;
import Utils.DataReader.JsonReader;
import Utils.Logs.LogsManager;
import Utils.Logs.TimeManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Test Cases for login page")
@Feature("Ui user Management")
@Story("log in")
@Severity(SeverityLevel.CRITICAL)
@Owner("Muhammed Sherif")
@UITest
public class LoginTest extends BaseTest {
    GUIDriver driver;
    NavigationBarComponant navBar;
    String extension = TimeManager.getSimpleTimeStamp();

@Description("Login with valid data")
@Test
public void validLoginTC(){
new UserManagementApi().createRegisterUserAcc(
        testData.getJsonData("name"),
        testData.getJsonData("email")+ extension+"@gmail.com",
        testData.getJsonData("password"),
        testData.getJsonData("FName"),
        testData.getJsonData("LName")).verifyAccCreated();
    navBar.navigateToHOmePAge()
            .clickOnLoginSignUpLink()
            .VerifyLoginSignPageDisplayed();
new LoginSignPage(driver).enterLoginEmail(testData.getJsonData("email")+ extension+"@gmail.com")
        .enterLoginPassword(testData.getJsonData("password")).submitLoginForm()
        .navBar.isUserLogged(testData.getJsonData("name"));
new UserManagementApi().deleteRegisterUserAcc(testData.getJsonData("email")+ extension+"@gmail.com",
        testData.getJsonData("password")).verifyAccDeleted();
}

@Description("validate using wrong password in login feature")
    @Test
    @Step("try login with wrong password")
    public void invalidLoginTC1(){
        navBar.navigateToHOmePAge()
                .clickOnLoginSignUpLink()
                .VerifyLoginSignPageDisplayed();
        new LoginSignPage(driver).enterLoginEmail(testData.getJsonData("email")+ extension+"@gmail.com")
                .enterLoginPassword(testData.getJsonData("password")+"1").submitLoginForm()
                .VerifyLoginFailed("Your email or password is incorrect!");
    }

    @Description("validate using wrong email in login feature")
    @Test
    @Step("try login with wrong email")
    public void invalidLoginTC2(){
        navBar.navigateToHOmePAge()
                .clickOnLoginSignUpLink()
                .VerifyLoginSignPageDisplayed();
        new LoginSignPage(driver).enterLoginEmail(testData.getJsonData("email")+ extension+"@gmamil.com")
                .enterLoginPassword(testData.getJsonData("password")).submitLoginForm()
                .VerifyLoginFailed("Your email or password is incorrect!");
    }






    //configuration
    @BeforeClass
    void beforeClassConditions(){
        testData = new JsonReader("Login-Data");
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
