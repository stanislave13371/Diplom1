package steps;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import support.Config;

public abstract class BaseApi {
    static {
        RestAssured.baseURI = Config.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("[RestAssured] baseURI = " + RestAssured.baseURI);
    }
}
