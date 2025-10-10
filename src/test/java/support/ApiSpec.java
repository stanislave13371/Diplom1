package support;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

public class ApiSpec {

    public static RequestSpecification json() {
        String base = System.getProperty("apiBase", "https://stellarburgers.nomoreparties.site");
        RestAssured.useRelaxedHTTPSValidation(); // на всякий случай
        return new RequestSpecBuilder()
                .setBaseUri(base)
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.BODY)
                .build();
    }
}
