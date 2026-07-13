package com.smarthome.management.platform.model.entity;

import com.smarthome.management.platform.model.enums.ActivityType;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "device_history")
public class DeviceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ActivityType activityType;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private DeviceStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private DeviceStatus newStatus;

    @Column(nullable = false, length = 220)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
}