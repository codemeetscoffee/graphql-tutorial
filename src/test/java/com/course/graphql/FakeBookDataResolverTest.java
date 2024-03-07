package com.course.graphql;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FakeBookDataResolverTest {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Autowired
    Faker faker;

    @Test
    void testAllBooks(){
//        var graphqlQuery = new BooksGraphQLQuery.Builder().build();
//        var projectionRoot = new BooksProjectionRoot<>().title().author().name().originCountry();
    }
}
