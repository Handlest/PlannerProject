package com.example.plannerapi.controllers.specifications;
import org.springframework.data.jpa.domain.Specification;
import com.example.plannerapi.domain.entities.TaskEntity;

public class TaskSpecifications {
    public static Specification<TaskEntity> hasStatus(TaskEntity.Status status) {
        return (root, query, builder) ->
                builder.equal(root.get("status"), status.toString());
    }

    public static Specification<TaskEntity> hasPriority(int priority) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<TaskEntity> hasTag(String tag) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("tag"), "%" + tag + "%");
    }
}
