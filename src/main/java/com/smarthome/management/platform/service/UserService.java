package com.smarthome.management.platform.service;

import com.smarthome.management.platform.dto.ProfileRequest;
import com.smarthome.management.platform.dto.RegisterRequest;
import com.smarthome.management.platform.model.entity.Role;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.model.enums.RoleName;
import com.smarthome.management.platform.repository.RoleRepository;
import com.smarthome.management.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Exista deja un cont cu acest email.");
        }
        if (StringUtils.hasText(request.getConfirmPassword())
                && !request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Parolele nu coincid.");
        }

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("Rolul USER lipseste."));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Transactional
    public void updateProfile(User user, ProfileRequest request) {
        user.setFullName(request.getFullName());
        userRepository.save(user);
    }
}