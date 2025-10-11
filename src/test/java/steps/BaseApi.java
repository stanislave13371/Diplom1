package steps;

import support.ApiSpec;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApi {
    protected RequestSpecification spec() {
        return ApiSpec.request();
    }
}
