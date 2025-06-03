package com.springbootjwedemo;

import com.springbootjwedemo.dtos.TokenDto;
import com.springbootjwedemo.dtos.AnythingDto;
import com.springbootjwedemo.services.JweService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class Controller {
    private final JweService jweService;

    @PostMapping("/generateToken")
    public ResponseEntity<TokenDto> generate(
            @Valid @RequestBody AnythingDto request
    ) throws Exception {
        var token = jweService.generateToken(request);
        return ResponseEntity.ok(new TokenDto(token.toString()));
    }

    @PostMapping("/parseToken")
    public ResponseEntity<AnythingDto> parse(
            @RequestBody TokenDto token
    ) {
        var jwe = jweService.parse(token.getToken());
        if (jwe == null || jwe.isExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(new AnythingDto(
                jwe.getSubject(),
                jwe.get("password", String.class)
        ));
    }
}