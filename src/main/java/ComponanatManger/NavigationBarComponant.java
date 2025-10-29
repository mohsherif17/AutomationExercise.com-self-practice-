package ComponanatManger;

import Drivers.GUIDriver;
import Utils.DataReader.PropertyReader;
import Utils.Logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponant {
    protected final GUIDriver driver;

    public NavigationBarComponant(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    protected final By HomeLink = new By.ByClassName("fa.fa-home");
    protected final By ProductsLink = new By.ByLinkText("/products");
    protected final By CartLink = new By.ByLinkText("/view_cart");
    protected final By LoginLink = new By.ByLinkText("Signup / Login");
    protected final By testCasesLink = new By.ByLinkText("/test_cases");
    protected final By TutorialsLink = new By.ByLinkText("https://www.youtube.com/c/AutomationExercise");
    protected final By ContactUsLink = new By.ByLinkText("/contact_us");
    protected final By APiLink = new By.ByLinkText("/api_list");
    protected final By LogOutLink = new By.ByLinkText("/logout");
    protected final By DeleteAccLink = new By.ByLinkText("/delete_account");
    protected final By HomePageLabel = new By.ByCssSelector("h1 > span");
    protected final By userLabel = new By.ByTagName("b");
    //actions
    @Step("navigate to home page")
    public NavigationBarComponant navigateToHOmePAge(){
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }
    @Step("Click on home link")
    public NavigationBarComponant clickOnHomeLink(){
        driver.element().click(HomeLink);
        return this;
    }
    @Step("click on products link")
    public ProductsPage clickOnProductLink(){
        driver.element().click(ProductsLink);
        return new ProductsPage(driver);
    }
    @Step("click on Cart Link")
    public CartPage clickOnCartLink(){
        driver.element().click(CartLink);
        return new CartPage(driver);
    }
    @Step("click on login/signup Link")
    public LoginSignPage clickOnLoginSignUpLink(){
        driver.element().click(LoginLink);
        return new LoginSignPage(driver);
    }
    @Step("click on logout link")
    public LogOutPage clickOnLogOutLink(){
        driver.element().click(LogOutLink);
        return new LogOutPage(driver);
    }
    @Step("click on contact us link")
    public ContactUsPage clickOnContactUsLink(){
        driver.element().click(ContactUsLink);
        return new ContactUsPage(driver);
    }
    @Step("click on test cases link")
    public TestCasesPage clickOnTestCasesLink(){
        driver.element().click(testCasesLink);
        return new TestCasesPage(driver);
    }
    @Step("click on api link")
    public APiPage clickOnApiLink() {
        driver.element().click(APiLink);
        return new APiPage(driver);
    }
    @Step("click on tutorials link")
    public TutorailsPage clickOnTutorialsLink(){
        driver.element().click(TutorialsLink);
        return new TutorailsPage(driver);
    }
    @Step("click on delete account link")
    public DeleteAccountPage clickOnDeleteAccountLink(){
        driver.element().click(DeleteAccLink);
        return new DeleteAccountPage(driver);
    }
    @Step("verify home page is displayed")
    public NavigationBarComponant isHomePageDisplayed(){
        driver.verification().assertDisplayed(HomePageLabel);
        return this;
    }
    @Step("user is logged in successfully ")
    public NavigationBarComponant isUserLogged(String userName){
        String actualUserName =  driver.element().getText(userLabel);
        LogsManager.info("Actual user name: ", actualUserName);
        driver.verification().equals(actualUserName, userName, "User is not logged in");
        return this;
    }
}
