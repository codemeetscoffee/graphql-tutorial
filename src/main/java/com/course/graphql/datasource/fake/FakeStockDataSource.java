package com.course.graphql.datasource.fake;

import com.course.graphql.types.Stock;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeStockDataSource {
    @Autowired
    private Faker faker;

    public Stock randomStock(){
        return Stock.newBuilder().price(faker.random().nextInt(1,100)).symbol(faker.stock().nyseSymbol()).build();
    }
}
