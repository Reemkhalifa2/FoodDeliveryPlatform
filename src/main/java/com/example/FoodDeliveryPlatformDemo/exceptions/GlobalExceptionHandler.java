package com.example.FoodDeliveryPlatformDemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─── 404 Not Found ────────────────────────────────────────────
    @ExceptionHandler({
            AddressNotFoundException.class,
            CustomerNotFoundException.class,
            MenuItemNotFoundException.class,
            ObjectNotFoundException.class,
            OrderNotFoundException.class,
            OwnerNotFoundException.class,
            ResourceNotFoundException.class,
            RestaurantNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex, WebRequest request) {
        return build(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    // ─── 400 Bad Request ──────────────────────────────────────────
    @ExceptionHandler({
            InvalidRequestException.class,
            NullRequestBodyException.class,
            InvalidLoyaltyPointsException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex, WebRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request);
    }

    // ─── 409 Conflict ─────────────────────────────────────────────
    @ExceptionHandler({
            InvalidOrderStateException.class,
            InvalidPaymentStatusException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex, WebRequest request) {
        return build(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    // ─── Generic fallback ─────────────────────────────────────────
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handleGeneric(GenericException ex, WebRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Error", ex.getMessage(), request);
    }

    // ─── 500 fallback ─────────────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(Exception ex, WebRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "An unexpected error occurred on the server.", request);
    }

    // ─── Builder helper ───────────────────────────────────────────
    private ResponseEntity<ErrorResponse> build(HttpStatus status, String error,
                                                String message, WebRequest request) {
        ErrorResponse body = new ErrorResponse(
                status,
                status.value(),
                error,
                message,
                request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(status).body(body);
    }
}