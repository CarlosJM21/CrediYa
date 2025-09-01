package co.com.mrcompany.api.config.Operations;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;

import java.lang.annotation.Annotation;

public class Operation implements io.swagger.v3.oas.annotations.Operation {
    @Override
    public String method() {
        return "";
    }

    @Override
    public String[] tags() {
        return new String[0];
    }

    @Override
    public String summary() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public RequestBody requestBody() {
        return null;
    }

    @Override
    public ExternalDocumentation externalDocs() {
        return null;
    }

    @Override
    public String operationId() {
        return "";
    }

    @Override
    public Parameter[] parameters() {
        return new Parameter[0];
    }

    @Override
    public ApiResponse[] responses() {
        return new ApiResponse[0];
    }

    @Override
    public boolean deprecated() {
        return false;
    }

    @Override
    public SecurityRequirement[] security() {
        return new SecurityRequirement[0];
    }

    @Override
    public Server[] servers() {
        return new Server[0];
    }

    @Override
    public Extension[] extensions() {
        return new Extension[0];
    }

    @Override
    public boolean hidden() {
        return false;
    }

    @Override
    public boolean ignoreJsonView() {
        return false;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
