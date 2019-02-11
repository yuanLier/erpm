package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.ErpApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class GameInitInfoRepositoryTest extends ErpApplicationTests {

    @Autowired
    private GameInitInfoRepository gameInitInfoRepository;

    @Test
    public void test(){

        System.out.println(gameInitInfoRepository.findTop1ByIdIsNotNullOrderByIdDesc());
    }

}