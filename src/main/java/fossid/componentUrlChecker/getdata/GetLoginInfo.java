package fossid.componentUrlChecker.getdata;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static fossid.componentUrlChecker.values.AllValues.allValues;

public class GetLoginInfo {
	private final Logger logger = LogManager.getLogger(GetLoginInfo.class);
	
	public void getLoginInfo() {
		String propsPath = System.getProperty("user.dir") + "\\config.properties";
		FileReader resources = null;

		logger.info("config.properties path : " + propsPath);

		try {
			resources = new FileReader(propsPath);
			Properties props = new Properties();
			//InputStream is = getClass().getResourceAsStream(propsPath);
			props.load(resources);
			
			String schema;
			String url;

			schema = props.getProperty("fossid.schema");

			url = props.getProperty("fossid.domain");
			
			if(schema.equals("http")) {
				
				allValues.loginValues.setServerUri("http://" + url);
				
				//check "fossid.domain" to add / in front of api.php
				String temp = allValues.loginValues.getServerUri();
				temp = temp.substring(temp .length() - 1);
				
				if(temp.endsWith("/")) {
					allValues.loginValues.setServerApiUri("http://" + url + "api.php");
				} else {
					allValues.loginValues.setServerApiUri("http://" + url + "/api.php");
				}
				
			} else if(schema.equals("https")) {
				allValues.loginValues.setServerUri("https://" + url);
				
				//check "fossid.domain" to add / in front of api.php
				String temp = allValues.loginValues.getServerUri();
				temp = temp.substring(temp .length() - 1);
				
				if(temp.endsWith("/")) {
					allValues.loginValues.setServerApiUri("https://" + url + "api.php");
				} else {
					allValues.loginValues.setServerApiUri("https://" + url + "/api.php");
				}				
			}

			String username = props.getProperty("fossid.username");

			String apikey = props.getProperty("fossid.apikey");
			
			allValues.loginValues.setUsername(username);
			allValues.loginValues.setApikey(apikey);
		} catch (IOException e) {
			logger.error("Exception Message", e);
		} finally {
			try {
				if (resources != null) {
					resources.close();
				}
			} catch (Exception e) {
				logger.error("Exception Message", e);
			}
		}
	}
}
