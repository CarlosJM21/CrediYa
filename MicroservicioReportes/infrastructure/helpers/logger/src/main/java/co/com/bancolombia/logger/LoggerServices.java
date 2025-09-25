package co.com.bancolombia.logger;

import co.com.bancolombia.model.helpers.Ilogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoggerServices implements Ilogger {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void logginInfo(String messagge, Object... ex) {
        logger.info(messagge,ex);
    }

    @Override
    public void logginError(String message, Object... ex) {
        logger.error(message, ex);
    }
}
