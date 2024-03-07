package com.course.graphql.component.fake;

import com.course.graphql.DgsConstants;
import com.course.graphql.datasource.fake.FakeBookDataSource;
import com.course.graphql.types.Author;
import com.course.graphql.types.Book;
import com.course.graphql.types.ReleaseHistory;
import com.course.graphql.types.ReleaseHistoryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakeBookDataResolver {
    @DgsData(parentType = "Query", field = "books")
    public List<Book> booksWrittenBy(@InputArgument(name = "author") Optional<String> authorName){
        if((authorName.isEmpty()) || StringUtils.isBlank(authorName.get())){
            return FakeBookDataSource.BOOK_LIST;
        }
        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(book -> StringUtils.containsIgnoreCase(book.getAuthor().getName(), authorName.get())).collect(Collectors.toList());
    }

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.BooksByReleased
    )
    public List<Book> getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {
       var releasedMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");
       var releasedInput = ReleaseHistoryInput.newBuilder()
               .printedEdition((boolean) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
               .year((int) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
               .build();
        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(book -> this.matchReleaseHistory(releasedInput, book.getReleased()))
                .collect(Collectors.toList());
    }

    private boolean matchReleaseHistory(ReleaseHistoryInput input, ReleaseHistory element){
        return input.getPrintedEdition().equals(element.getPrintedEdition()) && input.getYear() == element.getYear();
    }

}
