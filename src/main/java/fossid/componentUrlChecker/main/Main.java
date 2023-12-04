package fossid.componentUrlChecker.main;

import fossid.componentUrlChecker.getdata.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final long start = System.currentTimeMillis();

    public static void main(String[] args) {
        try {
            PrintInfo.startFOSSID();

            GetLoginInfo getLogin = new GetLoginInfo();
            getLogin.getLoginInfo();

            ValidateAuthentication.validateAuthentication();

            PrintInfo.printInfo();

            GetComponentList getComponentList = new GetComponentList();
            getComponentList.getData();

            PrintInfo.endFOSSID();

            long end = System.currentTimeMillis();
            logger.info("RunTime : " + (end - start)/60000 + "m" + ((end - start)%60000)/1000 + "s");
        } catch (Exception e) {
            logger.error("Exception Message", e);
            System.exit(1);
        }
    }
}
