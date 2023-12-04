package fossid.componentUrlChecker.getdata;

import fossid.componentUrlChecker.values.ComponentValues;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static fossid.componentUrlChecker.values.AllValues.allValues;

public class GetComponentList {
    private final Logger logger = LogManager.getLogger(GetComponentList.class);

    public void getData() {
        getAllComponent();
    }

    private void getAllComponent() {
        JSONObject dataObject = new JSONObject();
        dataObject.put("username", allValues.loginValues.getUsername());
        dataObject.put("key", allValues.loginValues.getApikey());

        JSONObject rootObject = new JSONObject();
        rootObject.put("group", "components");
        rootObject.put("action", "list_components");
        rootObject.put("data", dataObject);

        BufferedReader br = null;
        HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(entity);

            HttpResponse httpClientResponse = httpClient.execute(httpPost);

            if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
                throw new Exception("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
            }

            br = new BufferedReader(new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
            String result = br.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject resultJson = (JSONObject) jsonParser.parse(result);

            logger.debug("resultJson: " + resultJson);

            JSONObject resultJsonData = (JSONObject) resultJson.get("data");

            resultJsonData.values().forEach(values -> {
                JSONObject value = (JSONObject) values;

                String name = (String) value.get("name");
                String version = (String) value.get("version");

                ComponentValues componentValues = new ComponentValues(name, version);

                allValues.componentListValues.setAllComponentList(componentValues);

                getComponentInfo(componentValues);

                String updateMessage = "";
                if (componentValues.getSupplierUrl().isEmpty() && componentValues.getDownloadUrl().isEmpty()) {
                    allValues.componentListValues.setSupplierUrlEmptyComponentList(componentValues);
                    if (componentValues.getUrl().isEmpty()) {
                        allValues.componentListValues.setUpdateFailedComponentList(componentValues);
                    } else {
                        updateMessage = updateComponent(componentValues);
                    }
                } else if (componentValues.getUrl().isEmpty()) {
                    if (!componentValues.getSupplierUrl().isEmpty()) {
                        updateMessage = updateComponent(componentValues);
                    }
                }

                if (("An error occurred. Please check WebApp logs.").equals(updateMessage)) {
                    allValues.componentListValues.setUpdateErrorComponentList(componentValues);
                } else {
                    allValues.componentListValues.setUpdateComponentList(componentValues);
                }
            });

        } catch (Exception e) {
            logger.error("Exception Message", e);
            System.exit(1);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                logger.error("Exception Message", e);
            }
        }
    }

    private void getComponentInfo(ComponentValues componentValues) {
        JSONObject dataObject = new JSONObject();
        dataObject.put("username", allValues.loginValues.getUsername());
        dataObject.put("key", allValues.loginValues.getApikey());
        dataObject.put("component_name", componentValues.getComponentName());
        dataObject.put("component_version", componentValues.getComponentVersion());

        JSONObject rootObject = new JSONObject();
        rootObject.put("group", "components");
        rootObject.put("action", "get_information");
        rootObject.put("data", dataObject);

        BufferedReader br = null;
        HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(entity);

            HttpResponse httpClientResponse = httpClient.execute(httpPost);

            if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
                throw new Exception("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
            }

            br = new BufferedReader(new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
            String result = br.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject resultJson = (JSONObject) jsonParser.parse(result);

            logger.debug("resultJson: " + resultJson);

            JSONObject resultJsonData = (JSONObject) resultJson.get("data");

            String licenseIdentifier = resultJsonData.get("license_identifier")
                    != null
                    ? (String) resultJsonData.get("license_identifier") : "";
            String url = (String) resultJsonData.get("url")
                    != null
                    ? (String) resultJsonData.get("url") : "";
            String supplierUrl = (String) resultJsonData.get("supplier_url")
                    != null
                    ? (String) resultJsonData.get("supplier_url") : "";
            String downloadUrl = (String) resultJsonData.get("download_url")
                    != null
                    ? (String) resultJsonData.get("download_url") : "";

            componentValues.setLicenseIdentifier(licenseIdentifier);
            componentValues.setUrl(url);
            componentValues.setSupplierUrl(supplierUrl);
            componentValues.setDownloadUrl(downloadUrl);
        } catch (Exception e) {
            logger.error("Exception Message", e);
            System.exit(1);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                logger.error("Exception Message", e);
            }
        }
    }

    private String updateComponent(ComponentValues componentValues) {
        String updateMessage = "";

        JSONObject dataObject = new JSONObject();
        if (componentValues.getSupplierUrl().isEmpty()) {
            dataObject.put("username", allValues.loginValues.getUsername());
            dataObject.put("key", allValues.loginValues.getApikey());
            dataObject.put("name", componentValues.getComponentName());
            dataObject.put("version", componentValues.getComponentVersion());
            dataObject.put("url", componentValues.getUrl());
            dataObject.put("license_identifier", componentValues.getLicenseIdentifier());
            dataObject.put("supplier_url", componentValues.getUrl());
            dataObject.put("comment", "");
        } else {
            dataObject.put("username", allValues.loginValues.getUsername());
            dataObject.put("key", allValues.loginValues.getApikey());
            dataObject.put("name", componentValues.getComponentName());
            dataObject.put("version", componentValues.getComponentVersion());
            dataObject.put("url", componentValues.getSupplierUrl());
            dataObject.put("license_identifier", componentValues.getLicenseIdentifier());
            dataObject.put("supplier_url", componentValues.getSupplierUrl());
            dataObject.put("comment", "");
        }

        JSONObject rootObject = new JSONObject();
        rootObject.put("group", "components");
        rootObject.put("action", "update");
        rootObject.put("data", dataObject);

        BufferedReader br = null;
        HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(entity);

            HttpResponse httpClientResponse = httpClient.execute(httpPost);

            if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
                throw new Exception("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
            }

            br = new BufferedReader(new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
            String result = br.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject resultJson = (JSONObject) jsonParser.parse(result);

            logger.debug("resultJson: " + resultJson);

            updateMessage = (String) resultJson.get("message");

            logger.info(updateMessage);
        } catch (Exception e) {
            logger.error("Exception Message", e);
            System.exit(1);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                logger.error("Exception Message", e);
            }
        }

        return updateMessage;
    }
}
