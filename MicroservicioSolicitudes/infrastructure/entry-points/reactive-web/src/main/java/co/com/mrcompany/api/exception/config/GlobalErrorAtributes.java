package co.com.mrcompany.api.exception.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GlobalErrorAtributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new LinkedHashMap<String, Object>();//getErrorAttributes(request, options.isIncluded(Include.STACK_TRACE));
        Throwable error = getError(request);
        errorAttributes.put("message", error.getMessage());
        errorAttributes.put("path", request.path());
        errorAttributes.put("error", "Bad Request.");
        return errorAttributes;
    }
}
