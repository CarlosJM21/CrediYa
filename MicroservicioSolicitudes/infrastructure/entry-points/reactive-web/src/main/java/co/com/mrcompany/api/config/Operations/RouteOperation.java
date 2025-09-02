package co.com.mrcompany.api.config.Operations;

import co.com.mrcompany.model.loantype.LoanType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;

@Builder
@AllArgsConstructor
public class RouteOperation implements RouterOperation {

    /*public RouteOperation(String path,@Nulla RequestMethod[] method = null,
                          String[] consumes, String[] produces) {
        super(path, method,consumes,produces);
    }*/

    @Override
    public String path() {
        return "";
    }

    @Override
    public RequestMethod[] method() {
        return new RequestMethod[0];
    }

    @Override
    public String[] consumes() {
        return new String[0];
    }

    @Override
    public String[] produces() {
        return new String[0];
    }

    @Override
    public String[] headers() {
        return new String[0];
    }

    @Override
    public String[] params() {
        return new String[0];
    }

    @Override
    public Class<?> beanClass() {
        return null;
    }

    @Override
    public String beanMethod() {
        return "";
    }

    @Override
    public Class<?>[] parameterTypes() {
        return new Class[0];
    }

    @Override
    public Operation operation() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
