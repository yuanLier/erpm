package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.ErpApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class GameOrderInfoRepositoryTest extends ErpApplicationTests {

    @Autowired
    GameOrderInfoRepository gameOrderInfoRepository;

    @Test
    public void findByGameBasicInfo_IdAndYearAndSelectedIsTrueAndEnterpriseBasicInfo_IdIsNull() {
        System.out.println(gameOrderInfoRepository.findByGameBasicInfo_IdAndYearAndSelectedIsTrueAndEnterpriseBasicInfoIsNull((long) 1,1));
    }
}