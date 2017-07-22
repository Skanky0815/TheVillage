package core.engine.services;

import com.google.inject.Inject;
import core.engine.components.service.AbstractService;
import core.helper.Config;
import core.helper.PropertyName;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by RICO on 05.04.2015.
 */
public class TranslateService extends AbstractService {

    private static final String INDICATOR_MISSING_RESOURCE = "resource missing";

    private static final String INDICATOR_MISSING_KEY = "Key missing";

    private ResourceBundle resourceBundle;

    @Inject
    public TranslateService(Logger log, Config config) {
        super(log, config);

        init();
    }

    private void init() {
        final Locale locale = new Locale(config.getProperty(PropertyName.APP_LANGUAGE));
        this.resourceBundle = PropertyResourceBundle.getBundle("translations.Main", locale);
    }

    public String translate(final String key) {
        return this.translate(this.resourceBundle, key);
    }

    public String translate(final ResourceBundle resourceBundle, final String key) {
        if (resourceBundle != null) {
            try {
                return resourceBundle.getString(key);
            } catch (final MissingResourceException e) {
                this.log.info("missing key!");
                return  INDICATOR_MISSING_KEY + key;
            }
        }

        this.log.info("could not fond translation for '" + key + "'!");
        return INDICATOR_MISSING_RESOURCE;
    }
}
