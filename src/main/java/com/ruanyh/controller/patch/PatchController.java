package com.ruanyh.controller.patch;


import com.ruanyh.service.patch.PatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/patch")
public class PatchController {
    @Autowired
    private PatchService patchService;


}
