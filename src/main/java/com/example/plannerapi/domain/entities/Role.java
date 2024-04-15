//package com.example.plannerapi.domain.entities;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//
//@Entity
//@Table(name="roles")
//@NoArgsConstructor
//@AllArgsConstructor
//@Setter
//@Getter
//@Builder
//public class Role implements GrantedAuthority {
//    @Id
//    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_sequence")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Column(name="role_id")
//    private Integer roleId;
//    @Override
//    public String getAuthority() {
//        return null;
//    }
//}
