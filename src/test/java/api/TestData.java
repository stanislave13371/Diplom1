package api;

import com.github.javafaker.Faker;
import model.User;

import java.util.Locale;
import java.util.UUID;

public final class TestData {
    private static final Faker FAKER = Faker.instance(new Locale("en"));

    private TestData() {}

    public static User randomUser() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String first = FAKER.name().firstName();

        String name = first;
        String email = ("autotest." + first + "." + uuid + "@example.com").toLowerCase();
        String password = "P@ssw0rd" + FAKER.number().digits(4);

        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
