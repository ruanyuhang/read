package com.ruanyh.controller.demo;

import com.ruanyh.service.demo.DemoService;
import com.ruanyh.vo.DemoVO;
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
    public DemoVO getDetail(HttpServletRequest request) {
        DemoVO vo = new DemoVO();
        vo.setId(1);
        vo.setName("测试");
        vo.setCreateTime(System.currentTimeMillis());
        return vo;
    }

}
