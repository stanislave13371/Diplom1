package utils;

import java.util.UUID;

public class RandomUtils {
    public static String randomEmail() {
        return "auto_" + UUID.randomUUID() + "@mail.ru";
    }
    public static String randomName() {
        return "auto_" + UUID.randomUUID().toString().substring(0, 8);
    }
    public static String randomPassword() {
        return "P@" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}