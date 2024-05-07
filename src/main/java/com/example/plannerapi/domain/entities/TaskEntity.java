package com.example.plannerapi.domain.entities;
import jakarta.persistence.*;
import lombok.*;
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
    @Column(name="task_id")
    private long taskId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="due_to_start")
    private LocalDateTime DueToStart;

    @Column(name="due_to_end")
    private LocalDateTime DueToEnd;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private Status status = Status.NEW;

    @Column(name="priority", nullable = false)
    private int priority = 0;

    @Column(name="tag")
    private String tag;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
