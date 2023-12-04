package fossid.componentUrlChecker.values;

import java.util.ArrayList;

public class ComponentListValues {

    private static final ComponentListValues values = new ComponentListValues();

    private ComponentListValues() {}

    public static ComponentListValues getInstance() {
        return values;
    }

    private final ArrayList<ComponentValues> allComponentList = new ArrayList<>();
    private final ArrayList<ComponentValues> supplierUrlEmptyComponentList = new ArrayList<>();
    private final ArrayList<ComponentValues> updateFailedComponentList = new ArrayList<>();
    private final ArrayList<ComponentValues> updateComponentList = new ArrayList<>();
    private final ArrayList<ComponentValues> updateErrorComponentList = new ArrayList<>();

    public ArrayList<ComponentValues> getUpdateComponentList() {
        return updateComponentList;
    }
    public void setUpdateComponentList(ComponentValues componentValues) {
        this.updateComponentList.add(componentValues);
    }

    public ArrayList<ComponentValues> getAllComponentList() {
        return allComponentList;
    }
    public void setAllComponentList(ComponentValues componentValues) {
        this.allComponentList.add(componentValues);
    }

    public ArrayList<ComponentValues> getSupplierUrlEmptyComponentList() {
        return supplierUrlEmptyComponentList;
    }
    public void setSupplierUrlEmptyComponentList(ComponentValues componentValues) {
        this.supplierUrlEmptyComponentList.add(componentValues);
    }

    public ArrayList<ComponentValues> getUpdateFailedComponentList() {
        return updateFailedComponentList;
    }
    public void setUpdateFailedComponentList(ComponentValues componentValues) {
        this.updateFailedComponentList.add(componentValues);
    }

    public ArrayList<ComponentValues> getUpdateErrorComponentList() {
        return updateErrorComponentList;
    }
    public void setUpdateErrorComponentList(ComponentValues componentValues) {
        this.updateErrorComponentList.add(componentValues);
    }
}
