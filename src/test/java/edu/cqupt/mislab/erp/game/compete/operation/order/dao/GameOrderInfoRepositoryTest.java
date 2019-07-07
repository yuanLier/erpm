package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.ErpApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameOrderInfoRepositoryTest extends ErpApplicationTests {

    @Autowired
    GameOrderInfoRepository gameOrderInfoRepository;

    @Test
    public void test1(){

        List<Long> l1 = Arrays.asList((long)1, (long)2);
        List<Long> l2 = Arrays.asList((long)3, (long)6);
        List<Long> l3 = Arrays.asList((long)4, (long)5);

        System.out.println(gameOrderInfoRepository.findByGameBasicInfo_IdAndYearAndIsoBasicInfo_IdNotInAndMarketBasicInfo_IdInOrGameBasicInfo_IdAndYearAndIsoBasicInfo_IdNotInAndProductBasicInfo_IdIn((long)1,1, l1, l2, (long)1, 1, l1, l3));
    }
}