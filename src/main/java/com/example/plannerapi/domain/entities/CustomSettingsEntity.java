package com.example.plannerapi.domain.entities;

import jakarta.persistence.*;

@Entity
public class CustomSettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long settingId;
    private enum theme {
        dark, light
    }
    private enum mainColor {
        white, black, red, green, cia
    }
    private enum secondaryColor {
        white, black, red, green, cian
    }
    private boolean sendMessagesToEmail;
    private boolean sendNotifications;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
