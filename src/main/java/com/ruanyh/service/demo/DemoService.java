package com.ruanyh.service.demo;

import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoService {

    public void demo() {
        System.out.println("demo...");
    }

}
