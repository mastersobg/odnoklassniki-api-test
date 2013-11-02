package com.github.mastersobg.odkl.test;

import com.github.mastersobg.odkl.OdklApi;
import com.github.mastersobg.odkl.model.Group;
import com.github.mastersobg.odkl.model.PageableResponse;
import com.github.mastersobg.odkl.model.Pagination;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GroupsApi extends TestsRunner {

    public GroupsApi(OdklApi api) {
        super(api);
    }

    @Override
    protected ITestMethod[] getTestMethods() {
        return new ITestMethod[]{
                new GetInfo(),
                new GetMembers(),
                new GetUserGroupsByIds(),
                new GetUserGroupsV2()
        };
    }

    private class GetInfo implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            List<Group> list = api.groups().getInfo(Arrays.asList(54284287279104L, 52884591935721L));
            return list != null && list.size() == 2;
        }
    }

    private class GetMembers implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            PageableResponse<List<Long>> r1 = api.groups().getMembers(52884591935721L, null);
            PageableResponse<List<Long>> r2 = api.groups().getMembers(52884591935721L,
                    new Pagination("LTE4OTI2Mzc0NzQ6LTE4OTI2Mzc0ODQ=")
            );
            PageableResponse<List<Long>> r3 = api.groups().getMembers(52884591935721L,
                    new Pagination("LTE4OTI2Mzc0ODQ6LTE4OTI2Mzc0OTQ=", Pagination.Direction.FORWARD, 20)
            );

            return r1 != null && r1.getData() != null && r1.getData().size() == 10 &&
                    r2 != null && r2.getData() != null && r2.getData().size() == 10 &&
                    r3 != null && r3.getData() != null && r3.getData().size() == 20;
        }
    }

    private class GetUserGroupsByIds implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            Map<Long, Group.UserStatus> map = api.groups().
                    getUserGroupsByIds(52884591935721L, Arrays.asList(562059989504L));
            return map != null && map.size() == 1;
        }
    }

    private class GetUserGroupsV2 implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
