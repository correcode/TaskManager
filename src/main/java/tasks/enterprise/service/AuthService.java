import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import tasks.enterprise.config.JwtUtil;
import tasks.enterprise.dto.LoginRequest;
import tasks.enterprise.dto.RegisterRequest;
import tasks.enterprise.respository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email ja cadastrado!");
        }
        
        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.econde(request.geetPassword()))
            .role(Role.USER)
            .build();

        userRepository.save(user);

        return jwtUtil.generateToken(user.getEmail());
    }
    
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuario nao encotrado!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha invalida!");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
