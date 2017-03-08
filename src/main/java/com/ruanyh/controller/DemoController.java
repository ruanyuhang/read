package com.ruanyh.controller;

import com.ruanyh.service.DemoService;
import com.ruanyh.vo.DemoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping(value="/toPage", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request) {
        return "demo/page";
    }

    @RequestMapping(value="/getDetail")
    @ResponseBody
    public DemoVo getDetail(HttpServletRequest request) {
        DemoVo vo = new DemoVo();
        vo.setId(1);
        vo.setName("测试");
        vo.setCreateTime(System.currentTimeMillis());
        return vo;
    }

}
