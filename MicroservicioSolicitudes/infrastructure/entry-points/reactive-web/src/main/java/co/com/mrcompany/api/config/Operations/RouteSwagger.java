package co.com.mrcompany.api.config.Operations;

import co.com.mrcompany.api.Handler;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;


public class RouteSwagger {

    public  RouterOperation[]  Config()
    {
        return { /*new RouteOperation(path = "/v1/note/{id}", method = RequestMethod.POST,
                                beanClass = Handler.class, beanMethod = "applyToLoan"),*/
               };
    }
}
