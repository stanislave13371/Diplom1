package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specs {
    public static RequestSpecification req() {
        return new RequestSpecBuilder()
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .build();
    }
}