package api.tests;

import api.Expected;
import api.TestData;
import io.restassured.RestAssured;
import model.User;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import steps.LoginSteps;
import steps.UserSteps;

import java.net.InetAddress;

public abstract class BaseApiTest {
    protected final UserSteps userSteps = new UserSteps();
    protected final LoginSteps loginSteps = new LoginSteps();

    protected User user;
    protected String token;
    protected String refreshToken;

    @Before
    public void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
        user = TestData.randomUser();
        token = null;
        refreshToken = null;
        try {
            InetAddress.getByName("stellarburgers.nomoreparties.site");
        } catch (Exception e) {
            Assume.assumeTrue("API недоступен: " + e.getMessage(), false);
        }
    }

    @After
    public void tearDown() {
        try {
            if (refreshToken != null) {
                loginSteps.logout(refreshToken)
                        .statusCode(Expected.SC_OK_OR_ACCEPTED);
            }
        } finally {
            if (token != null) {
                userSteps.delete(token)
                        .statusCode(Expected.SC_OK_OR_ACCEPTED);
            }
        }
    }
}
