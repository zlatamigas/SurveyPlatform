package epam.zlatamigas.surveyplatform.util.locale;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.LOCALE_BUNDLE_FILE_PATH;

/**
 * resource bundle manager. According to passed locale returns {@link ResourceBundle}
 * with requested locale if found, otherwise returns {@link ResourceBundle} with
 * default system locale.
 */
public class ResourceBundleManager {

    private static final Logger logger = LogManager.getLogger();

    private static final String LOCALE_DELIMITER = "_";

    private static ResourceBundleManager instance;

    private ResourceBundleManager(){}

    public static ResourceBundleManager getInstance() {
        if(instance == null){
            instance = new ResourceBundleManager();
        }
        return instance;
    }

    public ResourceBundle getResourceBundle(String locale){
        ResourceBundle resourceBundle;
        if(locale != null){
            try {
                resourceBundle = ResourceBundle.getBundle(LOCALE_BUNDLE_FILE_PATH + LOCALE_DELIMITER + locale);
            } catch (MissingResourceException e){
                logger.warn("Locale \'{}\' is not found", locale);
                resourceBundle = ResourceBundle.getBundle(LOCALE_BUNDLE_FILE_PATH);
            }
        } else {
            resourceBundle = ResourceBundle.getBundle(LOCALE_BUNDLE_FILE_PATH);
        }

        return resourceBundle;
    }
}
