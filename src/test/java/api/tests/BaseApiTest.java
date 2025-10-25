package api.tests;

import api.TestData;
import api.Expected;
import io.restassured.RestAssured;
import model.User;
import org.junit.*;
import steps.LoginSteps;
import steps.UserSteps;

import java.net.InetAddress;
import java.net.URI;

public abstract class BaseApiTest {
    protected final UserSteps userSteps = new UserSteps();
    protected final LoginSteps loginSteps = new LoginSteps();

    protected User user;
    protected String token;
    protected String refreshToken;

    @BeforeClass
    public static void apiReachableOrSkip() {
        try {
            String host = new URI(System.getProperty("baseUri", "https://stellarburgers.education-services.ru")).getHost();
            InetAddress.getByName(host);
        } catch (Exception e) {
            Assume.assumeTrue("API недоступен: " + e.getMessage(), false);
        }
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = System.getProperty("baseUri", "https://stellarburgers.education-services.ru");
        user = TestData.randomUser();
        token = null;
        refreshToken = null;
    }

    @After
    public void tearDown() {
        if (refreshToken != null) {
            loginSteps.logout(refreshToken).statusCode(Expected.SC_OK_OR_ACCEPTED);
        }
        if (token != null) {
            userSteps.delete(token).statusCode(Expected.SC_OK_OR_ACCEPTED);
        }
    }
}
