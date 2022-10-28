package com.computatongsin.computatongsin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homeAouth() {
        return "index";
    }

    @GetMapping("/images")
    public String homeS3images() {
        return "image-upload";
    }

    @GetMapping("/videos")
    public String homeS3videos() {
        return "video-upload";
    }
}
