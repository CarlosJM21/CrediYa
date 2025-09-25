package co.com.mrcompany.loggin;

import co.com.mrcompany.model.Ilogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class logginService implements Ilogger {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void logginInfo(String messagge) {
      logger.info(messagge);
    }

    @Override
    public void logginError(String message) {
      logger.error(message);
    }
}
