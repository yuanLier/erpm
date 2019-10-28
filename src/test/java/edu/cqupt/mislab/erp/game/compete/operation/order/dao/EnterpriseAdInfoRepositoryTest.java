package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.ErpApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class EnterpriseAdInfoRepositoryTest extends ErpApplicationTests {

    @Autowired
    EnterpriseAdInfoRepository enterpriseAdInfoRepository;

    @Test
    public void distinctByEnterpriseOfOneYear() {
        enterpriseAdInfoRepository.distinctByEnterpriseOfOneYear(1,(long)1);
    }
}