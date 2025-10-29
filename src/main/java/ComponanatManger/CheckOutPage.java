package ComponanatManger;

import Drivers.GUIDriver;
import Utils.DataReader.PropertyReader;
import Validations.Validation;
import Validations.Verification;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckOutPage {
    private final GUIDriver driver;

    public CheckOutPage(GUIDriver driver) {
        this.driver = driver;
    }

    //vars
    String CheckoutPageEndPoint = "/checkout";
    //locators
    private final By totalCart = By.xpath("//b[.='Total Amount']/following::p[@class='cart_total_price']");
    private final By proceedToPayment = By.linkText("Place Order");
    private final By deliveryAddress = By.xpath("//h3[.='Your delivery address']");
    private final By deliverAddressName = By.xpath("//ul[@id='address_delivery']/li[@class='address_firstname address_lastname']");
    private final By deliverAddressCompany = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][1]");
    private final By deliverAddress1 = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][2]");
    private final By deliverAddress2 = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][3]");
    private final By deliverCityStatCode = By.xpath("//ul[@id='address_delivery']/li[@class='address_city address_state_name address_postcode']");
    private final By deliverCountry = By.xpath("//ul[@id='address_delivery']/li[@class='address_country_name']");
    private final By deliverPhone = By.xpath("//ul[@id='address_delivery']/li[@class='address_phone']");

    private final By BillingAddress = By.xpath("//h3[.='Your billing address']");
    private final By BillingAddressName = By.xpath("//ul[@id='address_invoice']/li[@class='address_firstname address_lastname']");
    private final By BillingAddressCompany = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][1]");
    private final By BillingAddress1 = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][2]");
    private final By BillingAddress2 = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][3]");
    private final By BillingCityStatCode = By.xpath("//ul[@id='address_invoice']/li[@class='address_city address_state_name address_postcode']");
    private final By BillingCountry = By.xpath("//ul[@id='address_invoice']/li[@class='address_country_name']");
    private final By BillingPhone = By.xpath("//ul[@id='address_invoice']/li[@class='address_phone']");

    //dynamic locators
    private By ProductName(String pName) {
        return By.xpath("//td[@class='cart_description']//a[normalize-space(text())='" + pName + "']");
    }

    private By ProductPrice(String pName) {
        return By.xpath("//td[@class='cart_description']//a[normalize-space(text())='" + pName + "']/ancestor::td/following-sibling::td[@class='cart_price']/p");
    }

    private By ProductQuantity(String pName) {
        return By.xpath("//td[@class='cart_description']//a[normalize-space(text())='" + pName + "']/ancestor::td/following-sibling::td[@class='cart_quantity']/button");
    }

    private By ProductTotal(String pName) {
        return By.xpath("//td[@class='cart_description']//a[normalize-space(text())='" + pName + "']/ancestor::td/following-sibling::td[@class='cart_total']/p");
    }

    //actions
    @Step("navigate to the checkout page")
    public CheckOutPage NavigateToCheckoutPAge() {
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb") + CheckoutPageEndPoint);
        return this;
    }
@Step("navigate to the payment page")
    public PaymentPage clickProceedTOPayment() {
        driver.element().click(proceedToPayment);
        return new PaymentPage(driver);
    }


    //validations
    @Step("validate correct element in  cart ")
    public CheckOutPage validateElementInCart(String expectedName,
                                              String expectedPrice,
                                              String expectedQuantity,
                                              String expectedTotal) {
        String elementName = driver.element().getText(ProductName(expectedName));
        String elementPrice = driver.element().getText(ProductPrice(expectedName));
        String elementQuantity = driver.element().getText(ProductQuantity(expectedName));
        String elementTotal = driver.element().getText(ProductTotal(expectedName));

        new Validation().equals(elementName, expectedName, "Product name mismatch");
        new Validation().equals(elementPrice, expectedPrice, "Product price mismatch");
        new Validation().equals(elementQuantity, expectedQuantity, "Product quantity mismatch");
        new Validation().equals(elementTotal, expectedTotal, "Product total mismatch");

        return this;
    }
@Step("validate correct cart total price")
    public CheckOutPage validateCartTotal(String expectedTotal) {
        String actualTotal = driver.element().getText(totalCart);
        new Verification().equals(actualTotal, expectedTotal, "Total Miss Match");
        return this;
    }
    @Step("validate Correct user data in Delivery Address")
    public CheckOutPage validateDeliveryAddress(String title,String FName,String lName,
                                                String Company,
                                                String address1,String address2,
                                                String City,String State,String Code,
                                                String country,
                                                String Phone){

        String acctualName=driver.element().getText(deliverAddressName);
        String acctualCompany=driver.element().getText(deliverAddressCompany);
        String acctualAddress1=driver.element().getText(deliverAddress1);
        String acctualAddress2=driver.element().getText(deliverAddress2);
        String acctualCityStateCode=driver.element().getText(deliverCityStatCode);
        String acctualCountry=driver.element().getText(deliverCountry);
        String acctualPhone=driver.element().getText(deliverPhone);
        new Validation().equals(acctualName,(title+". "+FName+" "+lName),"delivery Name and title miss Match");
        new Validation().equals(acctualCompany,Company,"delivery Company miss Match");
        new Validation().equals(acctualAddress1,address1,"delivery address1 miss Match");
        new Validation().equals(acctualAddress2,address2,"delivery address2 miss Match");
        new Validation().equals(acctualCityStateCode,(City+" "+State+" "+Code),"delivery City/State/code miss Match");
        new Validation().equals(acctualCountry,country,"delivery country miss Match");
        new Validation().equals(acctualPhone,Phone,"delivery phone miss Match");
        return this;
    }
@Step("validate correct data in billing address")
    public CheckOutPage validateBillingAddress(String title,String FName,String lName,
                                               String Company,
                                               String address1,String address2,
                                               String City,String State,String Code,
                                               String country,
                                               String Phone){

        String acctualName=driver.element().getText(BillingAddressName);
        String acctualCompany=driver.element().getText(BillingAddressCompany);
        String acctualAddress1=driver.element().getText(BillingAddress1);
        String acctualAddress2=driver.element().getText(BillingAddress2);
        String acctualCityStateCode=driver.element().getText(BillingCityStatCode);
        String acctualCountry=driver.element().getText(BillingCountry);
        String acctualPhone=driver.element().getText(BillingPhone);
        new Validation().equals(acctualName,(title+". "+FName+" "+lName),"Billing Name and title miss Match");
        new Validation().equals(acctualCompany,Company,"Billing Company miss Match");
        new Validation().equals(acctualAddress1,address1,"Billing address1 miss Match");
        new Validation().equals(acctualAddress2,address2,"Billing address2 miss Match");
        new Validation().equals(acctualCityStateCode,(City+" "+State+" "+Code),"Billing City/State/code miss Match");
        new Validation().equals(acctualCountry,country,"Billing country miss Match");
        new Validation().equals(acctualPhone,Phone,"Billing phone miss Match");
        return this;
    }

}
