package com.example.plannerapi.domain.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tasks")
public class TaskEntity {
    public enum Status { NEW, OVERDUE, COMPLETED, DELETED }

    @Id
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long taskId;

    @Column(nullable = false)
    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDeadline;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDeadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NEW;

    @Column(nullable = false)
    private int priority = 0;

    private String tag;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;
}
