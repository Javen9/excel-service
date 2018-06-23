package com.excel.service.controller;

import com.excel.service.util.ConfUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by javen on 2018/5/11.
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, String account, String password) {
        if (ConfUtil.getValue("login-account").equals(account) && ConfUtil.getValue("login-password").equals(password)) {
            request.getSession().setAttribute("user", account);
            return "index";
        }
        return "login";
    }
}
