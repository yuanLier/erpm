package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.ErpApplicationTests;
import edu.cqupt.mislab.erp.user.model.entity.CollegeInfo;
import edu.cqupt.mislab.erp.user.model.entity.MajorInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class MajorInfoRepositoryTest extends ErpApplicationTests {

    @Autowired
    MajorInfoRepository majorInfoRepository;

    @Test
    public void test1(){

        System.out.println(majorInfoRepository.findAll());
    }
}