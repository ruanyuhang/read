package com.ruanyh.service;

import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoService {

    public void demo() {
        System.out.println("demo...");
    }

}
