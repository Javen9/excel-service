package com.excel.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by javen on 2018/5/11.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String root() {
        return "redirect:index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
