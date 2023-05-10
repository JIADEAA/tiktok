package com.jiade.utils;

import com.jiade.base.BaseInfoProperties;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;



/**
 * @author: JIADE
 * @description: RedisOperatorTest
 * @date: 2023/5/10 19:32
 **/
@SpringBootTest
@Slf4j
class RedisOperatorTest extends BaseInfoProperties {


    @Test
    void sAdd() {
    }

    @Test
    void sRemove() {
    }

    @Test
    void sMembers() {
    }

    @Test
    void sIsMembers() {
    }

    @Test
    void sCard() {
    }

    @Test
    void zAdd() {
        System.out.println(redis.zAdd("ww","ww"));
    }

    @Test
    void zCard() {
    }

    @Test
    void zRevRange() {
    }

    @Test
    void zRemove() {
    }

    @Test
    void zRank() {
        System.out.println(redis.zRank("ww","aa"));;
    }

}