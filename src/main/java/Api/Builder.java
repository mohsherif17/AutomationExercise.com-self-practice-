package Api;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import Utils.DataReader.PropertyReader;

import java.util.Map;

public class Builder {
    private static String BaseUri= PropertyReader.getProperty("baseUrlAPI");

    private Builder(){

    }
    public static RequestSpecification getUserManagementSpecifications(Map<String, ?> FormParam){
return new RequestSpecBuilder().setBaseUri(BaseUri)
        .setContentType(ContentType.URLENC).addFormParams(FormParam).build();
    }
}
