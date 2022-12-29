package com.splendor.notes.spring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author splendor.s
 * @create 2022/11/6 下午3:41
 * @description
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue){
        propertyValueList.add(propertyValue);
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName){
        for(PropertyValue propertyValue : propertyValueList){
            if(propertyName.equals(propertyValue.getName())){
                return propertyValue;
            }
        }
        return null;
    }
}
