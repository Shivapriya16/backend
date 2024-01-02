package com.shiva.backend;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("path/{entity}")
    public String postMethodName(@PathVariable String entity) {
        return entity;
    }

}
