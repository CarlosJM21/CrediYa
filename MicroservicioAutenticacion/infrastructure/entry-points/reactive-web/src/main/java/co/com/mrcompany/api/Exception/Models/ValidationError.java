package co.com.mrcompany.api.Exception.Models;

import lombok.Builder;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

/*Clase que representa un error de validación*/

@Getter
@Builder
public class ValidationError {
    private final String field;
    private final String message;

    /*    Representa múltiples errores de validación*/
    public static class ValidationErrors extends RuntimeException {
        private final List<ValidationError> errors;
        public ValidationErrors(List<ValidationError> errors) {
            super("Se encontraron errores de validación");
            this.errors = errors;}
        public List<ValidationError> getErrors() {
            return errors;}
        /**
         Builder para construir múltiples errores de validación*/
        public static class Builder {
            private final List<ValidationError> errors = new ArrayList<>();
            public Builder addError(String field, String message) {
                errors.add(ValidationError.builder().field(field).message(message).build());
                return this;}
            public ValidationErrors build() {
                return new ValidationErrors(errors);}
            public boolean hasErrors() {
                return !errors.isEmpty();}}}

}
