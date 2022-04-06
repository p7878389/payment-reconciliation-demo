package com.shareworks.reconciliation;

import com.shareworks.reconciliation.services.RedisServices;
import org.apache.commons.collections4.SetUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisServiceTest {

    @Resource
    private RedisServices redisServices;

    @Test
    public void putSet() {
        Set<String> valueSet = Stream.of("111", "222", "333").collect(Collectors.toSet());
        redisServices.setPut("{account}:localSet", valueSet);
        valueSet.remove("111");
        valueSet.add("444");
        redisServices.setPut("{account}:outerSet", valueSet);
    }

    @Test
    public void intersectAndStore() {
        redisServices.intersectAndStore("{account}:union", SetUtils.hashSet("{account}:localSet", "{account}:outerSet"));
    }

    @Test
    public void difference() {
        Set<Object> localSetDifference = redisServices.difference("{account}:localSet", "{account}:union");
        System.out.println(localSetDifference.toString());
        Set<Object> outerSetDifference = redisServices.difference("{account}:outerSet", "{account}:union");
        System.out.println(outerSetDifference.toString());

    }
}
