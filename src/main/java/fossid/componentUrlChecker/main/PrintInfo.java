package fossid.componentUrlChecker.main;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static fossid.componentUrlChecker.values.AllValues.allValues;

public class PrintInfo {
    private static final Logger logger = LogManager.getLogger(PrintInfo.class);
    public static void startFOSSID() {
        logger.info("Start Component URL Checker");
        logger.info("");
        logger.info(" ******     ******     ******        ******");
        logger.info("*      *   *           *     *      *      ");
        logger.info("*      *   *           *      *    *       ");
        logger.info("*      *   *           *     *    *        ");
        logger.info("*      *    ******     ******     *        ");
        logger.info("*      *          *    *     *    *        ");
        logger.info("*      *          *    *      *    *       ");
        logger.info("*      *          *    *     *      *      ");
        logger.info(" ******     ******     ******        ******");
        logger.info("");
    }

    public static void endFOSSID() {
        logger.info("--[Finish Component URL Checker]--");
        logger.info("Check Component Count : " + allValues.componentListValues.getAllComponentList().size());
        if (allValues.componentListValues.getSupplierUrlEmptyComponentList().size() == 0) {
            logger.info("<None of the components have been updated.>");
        } else {
            logger.info("Empty supplier_url & download_url Component Count : " + allValues.componentListValues.getSupplierUrlEmptyComponentList().size());
            logger.info("Updated Component Count : " + allValues.componentListValues.getUpdateComponentList().size());
            logger.info("Update Error Component Count : " + allValues.componentListValues.getUpdateErrorComponentList().size());
            logger.info("Failed to update Component (URL doesn't exist) Count : " + allValues.componentListValues.getUpdateFailedComponentList().size());
        }
    }

    public static void printInfo() {
        int apiKeyLength = allValues.loginValues.getApikey().length();
        StringBuilder apiKey = new StringBuilder();

        for (int i = 0; i < apiKeyLength; i++) {
            apiKey.append("*");
        }

        logger.info("");
        logger.info("Server URL: " + allValues.loginValues.getServerApiUri());
        logger.info("UserName: " + allValues.loginValues.getUsername());
        logger.info("ApiKey: " + apiKey);
        logger.info("");
    }
}
