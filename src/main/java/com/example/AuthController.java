package com.example;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    // Единственный захардкоженный пользователь
    private static final String VALID_USER = "admin";
    private static final String VALID_PASS = "secret123";

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {

        String user = body.getOrDefault("username", "");
        String pass = body.getOrDefault("password", "");

        if (VALID_USER.equals(user) && VALID_PASS.equals(pass)) {
            return Map.of(
                    "success", true,
                    "message", "Добро пожаловать, " + user + "!"
            );
        }

        return Map.of(
                "success", false,
                "message", "Неверный логин или пароль"
        );
    }
}