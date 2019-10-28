package edu.cqupt.mislab.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "edu.cqupt.mislab.erp")
public class ErpApplication {

    public static void main(String[] args){
        SpringApplication.run(ErpApplication.class,args);
    }
}

