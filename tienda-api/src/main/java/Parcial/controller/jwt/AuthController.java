package Parcial.controller.jwt;

import Parcial.dto.jwt.LoginRequest;
import Parcial.model.jtw.TokenResponse;
import Parcial.service.jwt.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService service;
    
    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authGeader) {
        return service.refreshToken(authGeader);
    }

}