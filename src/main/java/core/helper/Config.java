package core.helper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: RICO
 * Date: 23.12.12
 * Time: 15:01
 */
@Singleton
public final class Config {

    private final Properties properties = new Properties();

    private String configPath;

    @Inject
    public Config() {}

    public void init() throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configPath);
            properties.load(inputStream);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (final IOException ignore) { }
        }
    }

    public void setConfigPath(final String path) {
        this.configPath = path;
    }

    public synchronized String getProperty(final PropertyName key) {
        return properties.getProperty(key.propertyKey);
    }

    public synchronized void addProperty(final PropertyName key, final String value) {
        properties.setProperty(key.propertyKey, value);
    }
}
