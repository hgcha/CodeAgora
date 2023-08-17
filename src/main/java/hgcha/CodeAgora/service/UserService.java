package hgcha.CodeAgora.service;

import hgcha.CodeAgora.dto.UserCreateDto;
import hgcha.CodeAgora.entity.Role;
import hgcha.CodeAgora.entity.User;
import hgcha.CodeAgora.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

}
