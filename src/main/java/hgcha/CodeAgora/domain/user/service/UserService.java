package hgcha.CodeAgora.domain.user.service;

import hgcha.CodeAgora.domain.user.dto.UserCreateDto;
import hgcha.CodeAgora.domain.user.role.Role;
import hgcha.CodeAgora.domain.user.entity.User;
import hgcha.CodeAgora.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void create(UserCreateDto userCreateDto) {
        User user = User.builder()
                        .username(userCreateDto.getUsername())
                        .password(bCryptPasswordEncoder.encode(userCreateDto.getPassword()))
                        .email(userCreateDto.getEmail())
                        .role(Role.ROLE_USER)
                        .build();

        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void banUser(Long id, int period) {
        User user = userRepository.findById(id).orElseThrow();
        user.setBannedUntil(LocalDateTime.now().plusDays(period));
        userRepository.save(user);
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
