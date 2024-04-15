package com.example.plannerapi.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name="custom_settings")
public class CustomSettingsEntity {
    private enum Theme { dark, light }
    private enum MainColor { white, black, red, green, cia }
    private enum SecondaryColor { white, black, red, green, cian }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long settingId;

    @Enumerated(EnumType.STRING)
    @Column(name="theme", nullable = false)
    private Theme theme = Theme.light;

    @Enumerated(EnumType.STRING)
    @Column(name="main_color", nullable = false)
    private MainColor mainColor = MainColor.white;

    @Enumerated(EnumType.STRING)
    @Column(name="secondary_color", nullable = false)
    private SecondaryColor secondaryColor = SecondaryColor.cian;

    @Column(name="send_messages_to_email", nullable = false)
    private boolean sendMessagesToEmail = false;

    @Column(name="send_notifications", nullable = false)
    private boolean sendNotifications = true;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
