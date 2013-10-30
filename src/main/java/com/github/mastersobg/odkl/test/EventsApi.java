package com.github.mastersobg.odkl.test;

import com.github.mastersobg.odkl.OdklApi;
import com.github.mastersobg.odkl.model.Event;

import java.util.List;

/**
 * @author Ivan Gorbachev <gorbachev.ivan@gmail.com>
 */
public class EventsApi extends TestsRunner {

    public EventsApi(OdklApi api) {
        super(api);
    }

    @Override
    protected ITestMethod[] getTestMethods() {
        return new ITestMethod[] {
            new Get()
        };
    }

    private class Get implements ITestMethod {

        @Override
        public boolean test() throws Exception {
            List<Event> list = api.events().get();
            return list != null && list.size() > 0;
        }
    }
}
