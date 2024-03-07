package com.course.graphql;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FakeHelloDataResolverTest {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void testOneHello(){
        var graphqlQuery = """
                {
                oneHello{
                text
                randomNumber
                }
                }
                """;
        String text =  dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery,"data.oneHello.text");
        Integer randomNumber = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery, "data.oneHello.randomNumber");
        assertFalse(StringUtils.isBlank(text));
        assertNotNull(randomNumber);
    }

    @Test
    void testAllHellos(){
        var graphqlQuery = """
                {
                allHellos {
                text
                randomNumber
                }
                }
                """;
        List<String> texts = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery,"data.allHellos[*].text");
        List<Integer> randomNumbers = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery,"data.allHellos[*].randomNumber");
        assertNotNull(texts);
        assertNotNull(randomNumbers);

    }
}
