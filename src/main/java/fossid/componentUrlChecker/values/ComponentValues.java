package fossid.componentUrlChecker.values;

import java.io.Serializable;

public class ComponentValues implements Serializable {
    public ComponentValues() {}

    public ComponentValues(String name, String version) {
        this.componentName = name;
        this.componentVersion = version;
    }

    public ComponentValues(String licenseIdentifier, String url, String supplierUrl, String downloadUrl) {
        this.licenseIdentifier = licenseIdentifier;
        this.url = url;
        this.supplierUrl = supplierUrl;
        this.downloadUrl = downloadUrl;
    }

    private String componentName;
    private String componentVersion;
    private String licenseIdentifier;
    private String url;
    private String supplierUrl;
    private String downloadUrl;

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(String componentVersion) {
        this.componentVersion = componentVersion;
    }

    public String getLicenseIdentifier() {
        return licenseIdentifier;
    }

    public void setLicenseIdentifier(String licenseIdentifier) {
        this.licenseIdentifier = licenseIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSupplierUrl() {
        return supplierUrl;
    }

    public void setSupplierUrl(String supplierUrl) {
        this.supplierUrl = supplierUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
