package cn.bugstack.springframework.beans;

import com.sun.tools.classfile.ConstantPool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Properties {
    private List<Property> properties = new ArrayList<>();

    public void addPropertyValue(Property propertyValue) {
        removePropertyValue(propertyValue.getName());
        properties.add(propertyValue);
    }

    public void addPropertyValue(String name, Object propertyValue) {
        removePropertyValue(name);
        properties.add(new Property(name, propertyValue));
    }

    public List<Property> getPropertiesList() {
        return properties;
    }

    public Object[] getPropertiesObject() {
        Object[] propertiesObject = new Object[properties.size()];
        for (int i = 0; i < propertiesObject.length; i++) {
            propertiesObject[i] = properties.get(i).getValue();
        }
        return propertiesObject;
    }

    private void removePropertyValue(String name){
        for(int i = 0; i<properties.size(); i++){
            if(properties.get(i).getName().equals(name)){
                properties.remove(i);
            }
        }
    }
}
