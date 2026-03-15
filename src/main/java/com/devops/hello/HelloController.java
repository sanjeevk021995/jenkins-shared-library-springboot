package com.devops.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // @GetMapping("/hello")
    // public String hello() {
    //     Thread.sleep(200);
    //     return "Hello DevOps Monitoring!";
    // }


    @GetMapping("/hello")
    public String hello() {
        try {
            Thread.sleep(200); // 200ms delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt flag
        }
        return "Hello DevOps";
    }

}