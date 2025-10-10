package support;

public final class Config {
    private Config() {}
    public static final String BASE_URI =
            System.getProperty("baseUri", "https://stellarburgers.nomoreparties.site");
}
