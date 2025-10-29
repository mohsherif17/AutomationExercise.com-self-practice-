package Api;

import Utils.Logs.LogsManager;
import Validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagementApi {
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;

    public UserManagementApi() {
        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    //endpoint
    private static final String CreateAccEndPoint = "/createAccount";
    private static final String deleteAccEndPoint = "/deleteAccount";
@Step("fill registration API with name:  password: {password}, birthday: {day}-{month}-{year}, first name: {firstName}, last name: {lastName}, address: {address}, country: {country}, state: {state}, zipcode: {zipcode}, mobile number: {mobileNumber}")
    public UserManagementApi createRegisterUserAcc(String name,
                                                   String email,
                                                   String title,
                                                   String password,
                                                   String day,
                                                   String month,
                                                   String year,
                                                   String firstName,
                                                   String lastName,
                                                   String company,
                                                   String address1,
                                                   String address2,
                                                   String country,
                                                   String state,
                                                   String city,
                                                   String zipcode,
                                                   String mobileNumber) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("title", title);
        formParams.put("password", password);
        formParams.put("day", day);
        formParams.put("month", month);
        formParams.put("year", year);
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", company);
        formParams.put("address1", address1);
        formParams.put("address2", address2);
        formParams.put("country", country);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("zipcode", zipcode);
        formParams.put("mobile_number", mobileNumber);
        response = requestSpecification.spec(Builder.getUserManagementSpecifications(formParams)).post(CreateAccEndPoint);
        LogsManager.info(response.toString());
        return this;
    }



    @Step("fill registration API with minimal data")
    public UserManagementApi createRegisterUserAcc(String name,
                                                   String email,
                                                   String password,
                                                   String firstName,
                                                   String lastName) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("title", "Mr");
        formParams.put("password", password);
        formParams.put("day", "17");
        formParams.put("month", "June");
        formParams.put("year", "2000");
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", "company");
        formParams.put("address1", "address1");
        formParams.put("address2", "address2");
        formParams.put("country", "India");
        formParams.put("state", "state");
        formParams.put("city", "city");
        formParams.put("zipcode", "123456");
        formParams.put("mobile_number", "1255628548");
        response = requestSpecification.spec(Builder.getUserManagementSpecifications(formParams)).post(CreateAccEndPoint);
        LogsManager.info(response.toString());
        return this;
    }




@Step("verify register using api")
    public UserManagementApi verifyAccCreated() {
        verification.equals(response.jsonPath().get("message"), "User created!", "message miss match");
        return this;
    }
    @Step("delete account using api")
    public UserManagementApi deleteRegisterUserAcc(String password,
                                                   String email){
        Map<String, String> formParams = new HashMap<>();
        formParams.put("password", password);
        formParams.put("email", email);
        response = requestSpecification.spec(Builder.getUserManagementSpecifications(formParams)).delete(deleteAccEndPoint);
        LogsManager.info(response.toString());
        return this;

    }
    @Step("verify account is deleted")
    public UserManagementApi verifyAccDeleted() {
        verification.equals(response.jsonPath().get("message"), "Account not found!", "message miss match");
        return this;
    }
}
