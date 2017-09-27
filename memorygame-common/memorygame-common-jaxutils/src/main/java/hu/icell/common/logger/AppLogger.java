package hu.icell.common.logger;

import java.io.Serializable;

import org.slf4j.Logger;

public interface AppLogger extends Logger, Serializable {
    public void setLogger(Logger logger);
}
