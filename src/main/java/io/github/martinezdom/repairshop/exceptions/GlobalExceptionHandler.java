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
                    errores.add("Email is invalid");
                    break;
                case "password":
                    errores.add("Password does not meet requirements");
                    break;
                default:
                    errores.add(error.getField() + ": " + error.getDefaultMessage());
            }
        }

        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Data validation error", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerNotFound(CustomerNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(VehicleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleVehicleExists(VehicleAlreadyExistsException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}