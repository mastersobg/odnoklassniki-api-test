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
            Pagination pagination = new Pagination();
            while (true) {
                PageableResponse<Long> r = api.groups().getMembers(52092368126041L, pagination);
                pagination = new Pagination(r.getAnchor());

                if (!r.hasMore()) {
                    break;
                }
            }

            PageableResponse<Long> r1 = api.groups().getMembers(52884591935721L, new Pagination());
            PageableResponse<Long> r2 = api.groups().getMembers(52884591935721L,
                    new Pagination("LTE4OTI2Mzc0NzQ6LTE4OTI2Mzc0ODQ=")
            );
            PageableResponse<Long> r3 = api.groups().getMembers(52884591935721L,
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
            PageableResponse<Long> r = api.groups().getUserGroupsV2(new Pagination());
            return r != null && r.getAnchor() != null && r.getData().size() > 0;
        }
    }
}
