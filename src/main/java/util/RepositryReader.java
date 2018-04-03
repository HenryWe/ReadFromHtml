package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RepositryReader {

    private static final Logger logger = Logger.getLogger(RepositryReader.class.getName());

    final String PROPERTY_FILE = "config.properties";

    public String getHTMLFileUrl() {

        Properties properties = new Properties();

        try (InputStream stream = RepositryReader.class.getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
            properties.load(stream);
        }
        catch (IOException e) { logger.log(Level.INFO, e.getMessage());
        }

        return properties.containsKey("url")?properties.getProperty("url"):"";
    }

    public String getOutputFile() {

        Properties properties = new Properties();

        try (InputStream stream = RepositryReader.class.getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
            properties.load(stream);
        }
        catch (IOException e) { logger.log(Level.INFO, e.getMessage());
        }

        return properties.containsKey("file")?properties.getProperty("file"):"";
    }
}
