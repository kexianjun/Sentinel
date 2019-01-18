package com.alibaba.csp.sentinel.demo.mytest;

import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FlowQpsDemoTest {
    public static void main(String[] args) {
        AtomicInteger pass = new AtomicInteger();
        AtomicInteger block = new AtomicInteger();
        AtomicInteger total = new AtomicInteger();
        initFlowQpsRule();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    int passed = 0;
                    int blocked = 0;
                    int totals = 0;
                    try {
                        SphU.entry("test");
                        Thread.sleep(1000);

                        try {
                            SphU.entry("test-1");
                        } catch (BlockException e) {
                            System.out.println("test-1 blocked");
                        }
                        passed = pass.incrementAndGet();
                    } catch (BlockException e) {
                        blocked = block.incrementAndGet();

                    }catch (Exception exception){

                    } finally {
                        totals = total.incrementAndGet();
                        System.out.println("current total : " + totals + ",  pass : " + passed + ", block: " + blocked);
                    }
                }

            }).start();
        }
    }

    private static void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule = new FlowRule();
        rule.setResource("test");
        // set limit qps to 20
        rule.setCount(10);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRule rule1 = new FlowRule();
        rule1.setRefResource("test-1");
        rule1.setCount(8);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }
}
