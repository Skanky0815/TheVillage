package core.helper;

/**
 * User: RICO
 * Date: 23.12.12
 * Time: 15:12
 */
public enum PropertyName {
    APP_VERSION("app.version"),
    APP_GUI_DEBUGGING("app.gui_debugging"),
    APP_LANGUAGE("app.language");

    final String propertyKey;

    PropertyName(final String propertyKey) {
        this.propertyKey = propertyKey;
    }
}
