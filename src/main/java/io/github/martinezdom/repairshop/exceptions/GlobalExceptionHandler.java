package io.github.martinezdom.repairshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errores = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            switch (error.getField()) {
                case "email":
                    errores.add("El correo no es válido");
                    break;
                case "password":
                    errores.add("La contraseña no cumple los requisitos");
                    break;
                default:
                    errores.add(error.getField() + ": " + error.getDefaultMessage());
            }
        }

        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Error en la validación de datos", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}