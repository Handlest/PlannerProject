package com.example.plannerapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    private String entityName;
    private String entityId;
    private final String message = "Entity was not found";
}
