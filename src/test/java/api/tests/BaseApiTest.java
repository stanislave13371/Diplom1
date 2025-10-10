package api.tests;

import api.TestData;
import api.Expected;
import io.restassured.RestAssured;
import model.User;
import org.junit.After;
import org.junit.Before;
import steps.LoginSteps;
import steps.UserSteps;

public abstract class BaseApiTest {
    protected final UserSteps userSteps = new UserSteps();
    protected final LoginSteps loginSteps = new LoginSteps();

    protected User user;
    protected String token;
    protected String refreshToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        user = TestData.randomUser();
        token = null;
        refreshToken = null;
    }

    @After
    public void tearDown() {
        if (refreshToken != null) {
            loginSteps.logout(refreshToken)
                    .statusCode(Expected.SC_OK_OR_ACCEPTED);
        }
        if (token != null) {
            userSteps.delete(token)
                    .statusCode(Expected.SC_OK_OR_ACCEPTED);
        }
    }
}
