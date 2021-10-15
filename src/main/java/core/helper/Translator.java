package core.helper;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * User: RICO
 * Date: 23.12.12
 * Time: 16:19
 */
@Deprecated
public final class Translator {

    private static final String INDICATOR_MISSING_RESOURCE = "resource missing";

    private static final String INDICATOR_MISSING_KEY = "Key missing";

    private static ResourceBundle resourceBundle;

    public static void setLocale(final Locale locale) {
        resourceBundle = PropertyResourceBundle.getBundle("translations.Main", locale);
    }

    public static String translate(final String key) {
        return Translator.translate(Translator.resourceBundle, key);
    }

    public static String translate(final ResourceBundle resourceBundle, final String key) {
        if (resourceBundle != null) {
            try {
                return resourceBundle.getString(key);
            } catch (final MissingResourceException e) {
                return INDICATOR_MISSING_KEY + key;
            }
        }

        return INDICATOR_MISSING_RESOURCE;
    }
}
