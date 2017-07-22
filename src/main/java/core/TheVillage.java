package core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import core.game.ui.GameFrame;
import core.helper.*;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.MessageFormat;
import java.util.Locale;


public class TheVillage extends AbstractModule {

    public static final Logger LOGGER = LogManager.getRootLogger();

    public static final String FS = File.separator;

    public static final String BASE_RESOURCES_PATH = "src" + FS + "main" + FS + "resources" + FS;

    private static final String CONFIG_PATH = BASE_RESOURCES_PATH + "AppConfig.properties";

    private Config config;

	public static void main(String[] args) {
        final TheVillage game = new TheVillage();
        final Injector injector = Guice.createInjector(game);
        final Config config = game.loadConfig(injector.getInstance(Config.class));
        game.setupLogging();

        Translator.setLocale(new Locale(config.getProperty(PropertyName.APP_LANGUAGE)));
        GuiDebugger.setGuiDebugger(config.getProperty(PropertyName.APP_GUI_DEBUGGING));

        game.setupGame();
        game.run(injector);
	}

    private void setupGame() {
        ImageLoader.init();
    }

    private TheVillage() {

    }

    protected void configure() {
        bind(Logger.class).toInstance(LOGGER);
    }

    private void run(final Injector injector) {
        final MessageFormat titleFormat = new MessageFormat(Translator.translate("game.name"));
        final Object[] arguments = { this.config.getProperty(PropertyName.APP_VERSION) };
        new GameFrame(titleFormat.format(arguments), injector);
    }

    private void setupLogging() {
        PropertyConfigurator.configureAndWatch(CONFIG_PATH);
        LOGGER.info("Init logger");
    }

    private Config loadConfig(final Config config) {
        try {
            config.setConfigPath(CONFIG_PATH);
            config.init();
            this.config = config;
            return config;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        return null;
    }
}
