package com.course.graphql.datasource.fake;

import com.course.graphql.types.Address;
import com.course.graphql.types.Author;
import com.course.graphql.types.Book;
import com.course.graphql.types.MobileApp;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakeMobileAppDataSource {
    @Autowired
    private Faker faker;

    public static final List<MobileApp> MOBILE_APP_LIST = new ArrayList<>();

    @PostConstruct
    private void postConstruct() throws MalformedURLException {
        for (int i = 0; i < 20; i++) {
            var addresses = new ArrayList<Address>();
            var author = Author.newBuilder().addresses(addresses)
                    .name(faker.app().author())
                    .originCountry(faker.country().name())
                    .build();
            var mobileApp = MobileApp.newBuilder()
                    .name(faker.app().name())
                    .author(author).version(faker.app().version())
                    .platform(List.of("ios"))
                    .releaseDate(LocalDate.now())
                    .downloaded(5)
//                    .homepage(new URL("blabla.com"))
                    .build();
            for (int j = 0; j < ThreadLocalRandom.current().nextInt(1,3); j++) {
                var address = Address.newBuilder().country(faker.address().country()).build();
                addresses.add(address);
            }
            MOBILE_APP_LIST.add(mobileApp);
        }
    }
}
