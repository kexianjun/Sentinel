package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("flowRuleZookeeperPublisher")
public class FlowRuleZookeeperProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    @Autowired
    private CuratorFramework zkClient;
    @Autowired
    private Converter<String, List<FlowRuleEntity>> converter;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        String zkPath = ZookeeperConfigUtil.getPath(appName);
        byte[] bytes = zkClient.getData().forPath(zkPath);
        if (null == bytes || bytes.length == 0) {
            return new ArrayList<>();
        }
        String s = new String(bytes);

        return converter.convert(s);
    }
}
