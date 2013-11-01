package com.github.mastersobg.odkl.test;

import com.github.mastersobg.odkl.OdklApi;
import com.github.mastersobg.odkl.model.Group;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GroupsApi extends TestsRunner {

    public GroupsApi(OdklApi api) {
        super(api);
    }

    @Override
    protected ITestMethod[] getTestMethods() {
        return new ITestMethod[] {
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
            List<Long> list = api.groups().
                    getMembers(52884591935721L, "LTE4OTI2Mzc0NzQ6LTE4OTI2Mzc1MTA=", "forward", 100);
            return false;
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
