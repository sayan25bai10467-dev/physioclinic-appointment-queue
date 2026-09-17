package com.vit.physioclinic.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.logging.*;

public final class LoggerUtil {
    private LoggerUtil() {}

    public static Logger createLogger(Path logDirectory) {
        try {
            Files.createDirectories(logDirectory);
            Logger logger = Logger.getLogger("PhysioClinic");
            logger.setUseParentHandlers(false);
            for (Handler handler : logger.getHandlers()) logger.removeHandler(handler);

            FileHandler fileHandler = new FileHandler(logDirectory.resolve("clinic.log").toString(), true);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return String.format("%s | %s | %s%n", LocalDateTime.now(), record.getLevel(), record.getMessage());
                }
            });
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
            return logger;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to initialize application logging.", e);
        }
    }
}
