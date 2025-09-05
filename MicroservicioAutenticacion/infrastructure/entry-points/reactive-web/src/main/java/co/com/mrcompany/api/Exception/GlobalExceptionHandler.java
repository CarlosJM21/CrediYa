package co.com.mrcompany.api.Exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import co.com.mrcompany.model.Exceptions.DomainException.EmailExistsException;
import co.com.mrcompany.model.Exceptions.DomainException.UserNotFoundException;
import co.com.mrcompany.model.Exceptions.DomainException.WrongSalaryRangeException;
import co.com.mrcompany.api.Exception.Models.ValidationError;
import co.com.mrcompany.api.Exception.Models.Validations;
import co.com.mrcompany.api.Exception.Models.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper om;

    private static String message = "message";
    private static String code = "code";
    private static String validateFailed = "Validation failed";
    private static String fieldErrors = "fieldErrors";

    public  GlobalExceptionHandler(ObjectMapper objectMapper)
    {
        this.om = objectMapper;
    }

    @Override
    public @NonNull Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable ex) {
        // Log the exception for debugging
        log.error("Global error handler caught: ", ex);

        HttpStatus status = mapStatus(ex);
        Map<String, Object> body = toBody(ex, status);

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Use a non-blocking approach to write the response
        return Mono.fromCallable(() -> {
                    try {
                        return om.writeValueAsBytes(body);
                    } catch (Exception e) {
                        log.error("Error serializing error response", e);
                        throw Exceptions.propagate(e);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic()) // Move potentially blocking operation to a dedicated thread
                .flatMap(bytes -> exchange.getResponse()
                        .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)))
                )
                .onErrorResume(e -> {
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    return exchange.getResponse().setComplete();
                });
    }

    private HttpStatus mapStatus(Throwable ex) {

        log.error("Global error handler caught: ", ex);
         if (ex instanceof WebExchangeBindException || ex instanceof ConstraintViolationException ) {
            return HttpStatus.BAD_REQUEST;
        }
        if (ex instanceof ResponseStatusException rse) {
            return HttpStatus.valueOf(rse.getStatusCode().value());
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }


    private Map<String, Object> toBody(Throwable ex, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put(code, ErrorCode.VALIDATION.name());
        body.put(message, validateFailed);

        if (ex instanceof WebExchangeBindException bex) {
            body.put(fieldErrors, bex.getFieldErrors().stream()
                    .map(this::fieldErrorToMap)
                    .collect( Collectors.toList()));
            return body;
        }

        if (ex instanceof ConstraintViolationException cve) {
            body.put("violations", cve.getConstraintViolations().stream()
                    .map(this::violationToMap)
                    .collect(Collectors.toList()));
            return body;
        }

        if (ex instanceof Validations.ValidationErrors validationErrors) {
            body.put(fieldErrors, validationErrors.getErrors().stream()
                    .map(this::validationErrorToMap)
                    .collect(Collectors.toList()));
            return body;
        }

        if (ex instanceof EmailExistsException exc) {
            body.put(fieldErrors, this.MessageErrorToMap(exc.getMessage(), exc.getField()));
            body.put(code, ErrorCode.DUPLICATE.name());
            return body;
        }

        if (ex instanceof WrongSalaryRangeException exc) {
            body.put(fieldErrors, this.MessageErrorToMap(exc.getMessage(), exc.getField()));
            body.put(code, ErrorCode.FORBIDDEN.name());
            return body;
        }

        if (ex instanceof UserNotFoundException exc) {
            body.put(fieldErrors, this.MessageErrorToMap(exc.getMessage(), exc.getField()));
            body.put(code, ErrorCode.NOT_FOUND.name());
            return body;
        }

        body.put(code, ErrorCode.INTERNAL.name());
        body.put(message, safeMessage(ex.getMessage()));
        return body;
    }

    private Map<String, Object> MessageErrorToMap(String error, String field) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("field", field);
        m.put(message, error);
        return m;
    }

    private Map<String, Object> validationErrorToMap(ValidationError error) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("field", error.getField());
        m.put(message, error.getMessage());
        return m;
    }

    private Map<String, Object> fieldErrorToMap(org.springframework.validation.FieldError fe) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("field", fe.getField());
        if (fe.getRejectedValue() != null) {  // rejectedValue puede ser null
            m.put("rejectedValue", fe.getRejectedValue());
        }
        m.put(message, fe.getDefaultMessage());
        return m;
    }

    private String safeMessage(String msg) {
        return (msg == null || msg.isBlank()) ? "Unexpected error" : msg;
    }

    private Map<String, Object> violationToMap(ConstraintViolation<?> v) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("property", v.getPropertyPath() == null ? "" : v.getPropertyPath().toString());
        if (v.getInvalidValue() != null) {
            m.put("invalidValue", v.getInvalidValue());
        }
        m.put(message, v.getMessage());
        return m;
    }
}