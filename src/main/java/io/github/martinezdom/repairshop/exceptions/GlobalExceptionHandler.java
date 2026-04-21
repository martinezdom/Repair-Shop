package io.github.martinezdom.repairshop.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleVehicleNotFound(VehicleNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RepairNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRepairNotFound(RepairNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(VehicleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleVehicleExists(VehicleAlreadyExistsException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExists(CustomerAlreadyExistsException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExists(EmailAlreadyExists ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(PhoneAlreadyExists.class)
    public ResponseEntity<ErrorResponseDTO> handlePhoneAlreadyExists(PhoneAlreadyExists ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidStatusException(InvalidStatusException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}