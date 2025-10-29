package ComponanatManger;

import Drivers.GUIDriver;
import Utils.DataReader.PropertyReader;
import Validations.Validation;
import Validations.Verification;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {

    private final GUIDriver driver;
    private final String PaymentPageEndPoint="/payment";
    public PaymentPage(GUIDriver driver) {
        this.driver = driver;
    }

    private final By cardName= By.xpath("//label[.='Name on Card']/following::input[@name='name_on_card']");
private final By cardNumber=By.xpath("//label[.='Card Number']/following::input[@name='card_number']");
private final By cardCVC=By.xpath("//label[.='CVC']/following::input[@name='cvc']");
private final By cardExpMonth= By.xpath("//label[.='Expiration']/following::input[@name='expiry_month']");
private final By cardExpYear= By.xpath("//label[.='Expiration']/following::input[@name='expiry_year']");
private final By orderConfirmed= By.xpath("//h2[@data-qa=\"order-placed\"]/b");
private final By submitBtn= By.id("submit");
private final By downloadInvoice= By.linkText("Download Invoice");
private final By continueBtn= By.linkText("continue-button");

//actions
    @Step("navigate to payment page")
    public PaymentPage NavigateToPayment( ){
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb")+PaymentPageEndPoint);
        return this;
    }
    @Step("fill payment form with required data")
    public PaymentPage fillCardData(String name,String number,String cvc,String expMonth,String expYear){
        driver.element().type(cardName,name);
        driver.element().type(cardNumber,number);
        driver.element().type(cardCVC,cvc);
        driver.element().type(cardExpMonth,expMonth);
        driver.element().type(cardExpYear,expYear);
        return this;
    }
    @Step("submit payment and place order")
    public PaymentPage submitPayment(){
        driver.element().click(submitBtn);
        return this;
    }
    @Step("download payment invoice")
    public PaymentPage downloadInvoice(){
        driver.element().click(downloadInvoice);
        return this;
    }


    //validations
    @Step("validate payment success")
    public PaymentPage validatePayment(String message){
     new Verification().equals((driver.element().getText(orderConfirmed)),message,"Payment is not confirmed");
return this;
    }
}
