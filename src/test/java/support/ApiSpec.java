package support;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiSpec {
    private static final RequestSpecification SPEC = new RequestSpecBuilder()
            .setBaseUri(Config.BASE_URI)
            .setBasePath(Config.API_BASE_PATH)
            .setContentType(ContentType.JSON)
            .build();

    private ApiSpec() {
    }

    public static RequestSpecification request() {
        return SPEC;
    }
}
