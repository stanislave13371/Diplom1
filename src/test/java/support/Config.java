package support;

public final class Config {
    private Config() {}
    public static final String BASE_URI =
            System.getProperty("baseUri", "https://stellarburgers.nomoreparties.site");

    public static final String API_BASE_PATH = "/api";
}
