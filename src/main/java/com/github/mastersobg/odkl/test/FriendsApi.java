package com.github.mastersobg.odkl.test;

import com.github.mastersobg.odkl.OdklApi;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Gorbachev <gorbachev.ivan@gmail.com>
 */
public class FriendsApi extends TestsRunner {

    public FriendsApi(OdklApi api) {
        super(api);
    }

    @Override
    protected ITestMethod[] getTestMethods() {
        return new ITestMethod[] {
                new Get(),
                new GetAppUsers(),
                new GetBirthdays(),
                new AreFriends(),
                new GetMutualFriends(),
                new GetOnline()
        };
    }

    private class Get implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            List<Long> list = api.friends().get();
            return list != null && !list.isEmpty();
        }
    }

    private class GetAppUsers implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            List<Long> list = api.friends().getAppUsers();
            return list != null;
        }
    }

    private class GetBirthdays implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            Map<Long, Date> l1 = api.friends().getBirthdays(false);
            Map<Long, Date> l2 = api.friends().getBirthdays(true);
            return l1 != null && l2 != null && l1.size() <= l2.size();
        }
    }

    private class AreFriends implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            boolean r1 = api.friends().areFriends(562059989504L, 180004519691L);
            boolean r2 = api.friends().areFriends(523306665633L, 180004519691L);
            boolean r3 = api.friends().areFriends(523306665633L, 229434779799L);
            return r1 && r2 && !r3;
        }
    }

    private class GetMutualFriends implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            List<Long> list = api.friends().getMutualFriends(183913748157L);
            return list != null && list.size() > 0;
        }
    }

    private class GetOnline implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            List<Long> list = api.friends().getOnline();
            return list != null;
        }
    }
}
