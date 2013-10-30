package com.github.mastersobg.odkl.test;

import com.github.mastersobg.odkl.OdklApi;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author Ivan Gorbachev <gorbachev.ivan@gmail.com>
 */
public class App {

    private static final String CONFIG_NAME = "local.properties";

    private final Properties conf;
    private final OdklApi api;

    private final TestsRunner[] allTests;


    public App() {
        conf = loadConfig();
        api = createAPI(conf);

        allTests = new TestsRunner[]{
                new FriendsApi(api),
                new EventsApi(api),
                new GroupsApi(api)
        };
    }

    private Properties loadConfig(){
        Properties p = new Properties();
        try {
            FileReader fr = new FileReader(CONFIG_NAME);
            p.load(fr);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return p;
    }

    private OdklApi createAPI(Properties p) {
        String clientId = p.getProperty("clientId");
        String clientPublicKey = p.getProperty("clientPublicKey");
        String clientSecretKey = p.getProperty("clientSecretKey");
        String accessToken = p.getProperty("accessToken");
        String refreshToken = p.getProperty("refreshToken");
        return new OdklApi(clientId, clientPublicKey, clientSecretKey, accessToken, refreshToken);
    }

    private List<TestsRunner> getEnabledTests() {
        String enabledTests = conf.getProperty("enabledTests");
        if (enabledTests == null) {
            return Arrays.asList(allTests);
        }
        List<TestsRunner> runners = new ArrayList<TestsRunner>();
        for (TestsRunner tr : allTests) {
            if (enabledTests.contains(tr.getClass().getSimpleName())) {
                runners.add(tr);
            }
        }
        return runners;
    }

    void run() {
        long time = System.currentTimeMillis();
        int passed = 0, failed = 0;

        for (TestsRunner tr : getEnabledTests()) {
            tr.test();
            passed += tr.getPassed();
            failed += tr.getFailed();
        }

        time = System.currentTimeMillis() - time;
        System.out.printf("Run: %s Passed: %s Failed: %s [%d ms in total]\n", passed + failed, passed, failed, time);
    }

    public static void main(String []args) {
        new App().run();
    }
}
