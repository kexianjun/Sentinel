package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import org.apache.commons.lang.StringUtils;

public class ZookeeperConfigUtil {
    public static final String RULE_ROOT_PATH = "sentinel_rule_config";

    public static final int RETRY_TIMES = 3;
    public static final int SLEEP_TIME = 1000;

    public static String getPath(String appName) {
        StringBuilder stringBuilder = new StringBuilder(RULE_ROOT_PATH);

        if (StringUtils.isBlank(appName)) {
            return stringBuilder.toString();
        }
        if (appName.startsWith("/")) {
            stringBuilder.append(appName);
        }
        return stringBuilder.toString();
    }
}
