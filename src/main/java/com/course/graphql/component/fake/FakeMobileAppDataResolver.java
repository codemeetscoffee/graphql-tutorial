package com.course.graphql.component.fake;

import com.course.graphql.DgsConstants;
import com.course.graphql.datasource.fake.FakeMobileAppDataSource;
import com.course.graphql.types.MobileApp;
import com.course.graphql.types.MobileAppFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakeMobileAppDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.MobileApp
    )
    public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter", collectionType = MobileAppFilter.class) Optional<MobileAppFilter> filter){

        if(filter.isEmpty()) {
            return FakeMobileAppDataSource.MOBILE_APP_LIST;
        }
        return FakeMobileAppDataSource.MOBILE_APP_LIST.stream().filter(
                mobileApp -> this.matchFilter(filter.get(),mobileApp)
        ).collect(Collectors.toList());
    }

    private boolean matchFilter(MobileAppFilter mobileAppFilter, MobileApp mobileApp) {
    var isAppMatch = StringUtils.containsIgnoreCase(mobileApp.getName(),
            StringUtils.defaultIfBlank(mobileAppFilter.getName(), StringUtils.EMPTY))
                    && StringUtils.containsIgnoreCase(mobileApp.getVersion(),
                    StringUtils.defaultIfBlank(mobileAppFilter.getVersion(), StringUtils.EMPTY));
    if (!isAppMatch) {
        return false;
    }
    return true;
    }
}
