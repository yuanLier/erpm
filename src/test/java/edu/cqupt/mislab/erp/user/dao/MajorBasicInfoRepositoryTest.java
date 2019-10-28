package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.ErpApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MajorBasicInfoRepositoryTest extends ErpApplicationTests {

    @Autowired
    MajorBasicInfoRepository majorBasicInfoRepository;

    @Test
    public void test1(){

        System.out.println(majorBasicInfoRepository.findAll());
    }
}