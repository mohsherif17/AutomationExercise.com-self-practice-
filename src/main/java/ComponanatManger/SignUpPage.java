package ComponanatManger;

import Drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignUpPage {
    private final GUIDriver driver;

    public SignUpPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators

    private final By PasswordField = By.id("password");
    private final By BirthDayField = By.id("days");
    private final By BirthMonthField = By.id("months");
    private final By BirthYearField = By.id("years");
    private final By firstNameField = By.id("first_name");
    private final By lastNameField = By.id("last_name");
    private final By addressField = By.id("address1");
    private final By countryField = By.id("country");
    private final By CityField = By.id("city");
    private final By stateField = By.id("state");
    private final By zipcodeField = By.id("zipcode");
    private final By mobileNumberField = By.id("mobile_number");
    private final By CreateAccountBtn = By.cssSelector("[data-qa=\"create-account\"]");
    private final By CreateAccLabel = By.tagName("b");
    private final By ContinueBtn = By.cssSelector("[data-qa=\"continue-button\"]");

    //actions
    @Step("Choose Title : {title}")
    private SignUpPage selectTitle(String title) {
        By TitleLocator = By.xpath("//input[@value='" + title + "']");
        driver.element().click(TitleLocator);
        return this;
    }

    @Step("fill registration form with name:  password: {password}, birthday: {day}-{month}-{year}, first name: {firstName}, last name: {lastName}, address: {address}, country: {country}, state: {state}, zipcode: {zipcode}, mobile number: {mobileNumber}")
    public SignUpPage fillRegistrationForm(String title,
                                           String password,
                                           String day,
                                           String month,
                                           String year,
                                           String firstName,
                                           String lastName,
                                           String address,
                                           String country,
                                           String state,
                                           String city,
                                           String zipcode,
                                           String mobileNumber) {
        selectTitle(title);

        driver.element().type(PasswordField, password);
        driver.element().selectFromDropDown(BirthDayField, day);
        driver.element().selectFromDropDown(BirthMonthField, month);
        driver.element().selectFromDropDown(BirthYearField, year);
        driver.element().type(firstNameField, firstName);
        driver.element().type(lastNameField, lastName);
        driver.element().type(addressField, address);
        driver.element().selectFromDropDown(countryField, country);
        driver.element().type(stateField, state);
        driver.element().type(CityField, city);

        driver.element().type(zipcodeField, zipcode);
        driver.element().type(mobileNumberField, mobileNumber);
        return this;
    }

    @Step("click sign up button")
    public SignUpPage ClickCreateAccButton() {
        driver.element().click(CreateAccountBtn);
        return this;
    }
@Step("click continue button")
public NavigationBarComponant clickContinue(){
        driver.element().click(ContinueBtn);
        return new NavigationBarComponant(driver);
}
    @Step("validate signup")
    public SignUpPage ValidateAccountCreated(String excpectedMsg) {
        String acctualMsg= driver.element().getText(CreateAccLabel);
        driver.verification().equals(acctualMsg,excpectedMsg,"not found succssfully");
        return this;
    }
}
