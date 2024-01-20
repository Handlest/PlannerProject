package com.example.plannerapi.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {

    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;
    private String login;
    private String password;
    private ZonedDateTime registrationDateTime;
    private String email;
    private boolean isActive;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private CustomSettingsEntity settings;
}
