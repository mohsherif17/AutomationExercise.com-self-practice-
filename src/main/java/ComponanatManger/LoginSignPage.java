package ComponanatManger;

import Drivers.GUIDriver;
import Utils.DataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginSignPage {
    private final GUIDriver driver;
public NavigationBarComponant navBar;
    public LoginSignPage(GUIDriver driver) {
        this.driver = driver;
        navBar = new NavigationBarComponant(driver);
    }

    private final String LoginSignupPageEndPoint = "/login";
    //locators
    private final By LoginEmailField = By.cssSelector("[data-qa=\"login-email\"]");
    private final By LoginPasswordField = By.cssSelector("[data-qa=\"login-password\"]");
    private final By LoginBtn = By.cssSelector("[data-qa=\"login-button\"]");
    private final By SignUpEmailField = By.cssSelector("[data-qa=\"signup-email\"]");
    private final By SignUpName = By.cssSelector("[data-qa=\"signup-name\"]");
    private final By SignUpBtn = By.cssSelector("[data-qa=\"signup-button\"]");
    private final By LoginField = By.cssSelector("[action='/login'] > p");
    private final By SignUpField = By.cssSelector("[action='/signup'] > p");


    //actions
    public LoginSignPage NavigateToLoginSignPage() {
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb") + LoginSignupPageEndPoint);
        return this;
    }

    @Step("Passing login email: {Email}")
    public LoginSignPage enterLoginEmail(String Email) {
        driver.element().type(LoginEmailField, Email);
        return this;
    }

    @Step("Passing login Password : {Password")
    public LoginSignPage enterLoginPassword(String Password) {
        driver.element().type(LoginPasswordField, Password);
        return this;
    }

    @Step("submit login Form")
    public LoginSignPage submitLoginForm() {
        driver.element().click(LoginBtn);
        return this;
    }


    @Step("Passing SignUp email: {Email}")
    public LoginSignPage enterSignUpEmail(String Email) {
        driver.element().type(SignUpEmailField, Email);
        return this;
    }

    @Step("Passing SignUp Password : {Name}")
    public LoginSignPage enterSignUpName(String Name) {
        driver.element().type(SignUpName, Name);
        return this;
    }

    @Step("submit SignUp Form")
    public LoginSignPage submitSignUpForm() {
        driver.element().click(SignUpBtn);
        return this;
    }

    //validations
    @Step("Validate Login Successful")
    public LoginSignPage VerifyLoginSuccessful() {
        driver.verification().assertDisplayed(By.cssSelector("a[href='/logout']"));
        return this;
    }

    @Step("Validate Login Sign in page")
    public LoginSignPage VerifyLoginSignPageDisplayed() {
        driver.verification().assertDisplayed(LoginBtn);
        driver.verification().assertDisplayed(SignUpBtn);
        return this;
    }

    @Step("Validate Login Failed ")
    public LoginSignPage VerifyLoginFailed(String expectedMessage) {
        String acctualMessage = driver.element().getText(LoginField);
        driver.verification().equals(acctualMessage, expectedMessage, "login error message is not correct");
        return this;
    }

    @Step("Validate sign up Failed ")
    public LoginSignPage VerifySignUpFailed(String expectedMessage) {
        String acctualMessage = driver.element().getText(SignUpField);
        driver.verification().equals(acctualMessage, expectedMessage, "sign up error message is not correct");
        return this;
    }
}
