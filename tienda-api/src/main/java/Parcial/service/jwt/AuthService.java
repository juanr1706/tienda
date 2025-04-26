package Parcial.service.jwt;

import Parcial.dto.jwt.LoginRequest;
import Parcial.dto.jwt.RegisterRequest;
import Parcial.model.jtw.Token;
import Parcial.model.jtw.TokenResponse;
import Parcial.model.jtw.User;
import Parcial.repository.jwt.TokenRepository;
import Parcial.repository.jwt.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);

    }

    public TokenResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );



        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());
        if (!validUserTokens.isEmpty()) {
            for (Token token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Bearer token");
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new IllegalArgumentException("Invalid Refresh token");
        }

        User user = userRepository.findByUsername(username).orElseThrow(( ) -> new UsernameNotFoundException(username));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid Refresh token");
        }

        String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken);

    }

}