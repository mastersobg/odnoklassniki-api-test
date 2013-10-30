package com.github.mastersobg.odkl.test;

import com.github.mastersobg.odkl.OdklApi;

/**
 * @author Ivan Gorbachev <gorbachev.ivan@gmail.com>
 */
public abstract class TestsRunner {

    protected final OdklApi api;

    private int passed;
    private int failed;

    public TestsRunner(OdklApi api) {
        this.api = api;
    }

    protected abstract ITestMethod[]getTestMethods();

    public void test() {
        for (ITestMethod tm : getTestMethods()) {
            boolean ret = false;
            long time = System.currentTimeMillis();
            try {
                ret = tm.test();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
            time = System.currentTimeMillis() - time;

            String result = String.format("%s %s [%d ms]\n",
                    tm.getClass().getName(), ret ? "passed" : "failed", time);
            System.out.println(result);

            if (ret) {
                ++passed;
            } else {
                ++failed;
            }
        }
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }
}
