package it.unical.ea.lemubackend.lemu_backend.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
