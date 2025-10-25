package utils;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.Locale;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBuilder {

    private String name;
    private String email;
    private String password;
    private String incorrectPassword;

    private static final Faker faker = new Faker();
    private static final Faker fakerRu = new Faker(new Locale("ru-RU"));

    public static UserBuilder registerUserData(){
        String name = fakerRu.name().firstName();
        String email = faker.name().firstName() + "." + faker.name().lastName() + "@mail.ru";
        String password = fakerRu.internet().password(6, 12);
        String incorrectPassword = faker.regexify("[0-9]{2}[a-zA-Z]{3}");
        return UserBuilder.builder()
                .name(name)
                .email(email)
                .password(password)
                .incorrectPassword(incorrectPassword)
                .build();
    }
}
