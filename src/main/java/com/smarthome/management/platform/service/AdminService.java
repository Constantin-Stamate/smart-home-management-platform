package com.smarthome.management.platform.service;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.repository.DeviceRepository;
import com.smarthome.management.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    @Transactional(readOnly = true)
    public List<User> users() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Device> devices() {
        return deviceRepository.findAll();
    }

    @Transactional
    public void toggleUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }
}