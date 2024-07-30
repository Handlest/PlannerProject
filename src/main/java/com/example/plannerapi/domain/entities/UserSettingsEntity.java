package com.example.plannerapi.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NotNull
public class UserSettingsEntity {
    private enum Theme { dark, light }
    private enum MainColor { white, black, red, green, cia }
    private enum SecondaryColor { white, black, red, green, cian }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="theme", nullable = false)
    private Theme theme = Theme.light;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="main_color", nullable = false)
    private MainColor mainColor = MainColor.white;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="secondary_color", nullable = false)
    private SecondaryColor secondaryColor = SecondaryColor.cian;

    @NotNull
    @Column(name="send_messages_to_email", nullable = false, columnDefinition = "boolean default false")
    private boolean sendMessagesToEmail = false;

    @NotNull
    @Column(name="send_notifications", nullable = false)
    private boolean sendNotifications = true;
}
