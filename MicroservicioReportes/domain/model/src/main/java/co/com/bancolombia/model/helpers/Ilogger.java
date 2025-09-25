package co.com.bancolombia.model.helpers;

public interface Ilogger {

    void logginInfo(String messagge, Object... ex);

    void logginError(String message, Object... ex);
}
