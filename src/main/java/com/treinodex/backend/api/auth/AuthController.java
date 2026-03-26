package com.treinodex.backend.api.auth;

import com.treinodex.backend.core.security.TokenService;
import com.treinodex.backend.domain.user.User;
import com.treinodex.backend.domain.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UserRepository repository, PasswordEncoder encoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = encoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        User newUser = new User();
        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());

        repository.save(newUser);

        return ResponseEntity.ok().body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
         var useOptinal = repository.findByEmail(request.email());
         if (useOptinal.isEmpty()) {
             return ResponseEntity.status(401).body("Invalid credentials!");
         }

         User user = useOptinal.get();
         if (passwordEncoder.matches(request.password(), user.getPassword())) {
             String token = tokenService.generateToken(user);
             return  ResponseEntity.ok(new LoginResponse(token));
         } else {
             return ResponseEntity.status(401).body("Invalid credentials!");
         }
    }
}
