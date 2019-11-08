package com.carros.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String get() {
        return "Raíz dos carros";
    }

    @GetMapping("/userInfo")
    public UserDetails userInfo(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

}
