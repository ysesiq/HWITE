package au.com.bytecode.opencsv.bean;

import au.com.bytecode.opencsv.CSVReader;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/bean/HeaderColumnNameMappingStrategy.class */
public class HeaderColumnNameMappingStrategy<T> implements MappingStrategy<T> {
    protected String[] header;
    protected Map<String, PropertyDescriptor> descriptorMap = null;
    protected Class<T> type;

    @Override // au.com.bytecode.opencsv.bean.MappingStrategy
    public void captureHeader(CSVReader cSVReader) throws IOException {
        this.header = cSVReader.readNext();
    }

    @Override // au.com.bytecode.opencsv.bean.MappingStrategy
    public PropertyDescriptor findDescriptor(int i) throws IntrospectionException {
        String columnName = getColumnName(i);
        if (null == columnName || columnName.trim().length() <= 0) {
            return null;
        }
        return findDescriptor(columnName);
    }

    protected String getColumnName(int i) {
        if (null == this.header || i >= this.header.length) {
            return null;
        }
        return this.header[i];
    }

    protected PropertyDescriptor findDescriptor(String str) throws IntrospectionException {
        if (null == this.descriptorMap) {
            this.descriptorMap = loadDescriptorMap(getType());
        }
        return this.descriptorMap.get(str.toUpperCase().trim());
    }

    protected boolean matches(String str, PropertyDescriptor propertyDescriptor) {
        return propertyDescriptor.getName().equals(str.trim());
    }

    protected Map<String, PropertyDescriptor> loadDescriptorMap(Class<T> cls) throws IntrospectionException {
        PropertyDescriptor[] loadDescriptors;
        HashMap hashMap = new HashMap();
        for (PropertyDescriptor propertyDescriptor : loadDescriptors(getType())) {
            hashMap.put(propertyDescriptor.getName().toUpperCase().trim(), propertyDescriptor);
        }
        return hashMap;
    }

    private PropertyDescriptor[] loadDescriptors(Class<T> cls) throws IntrospectionException {
        return Introspector.getBeanInfo(cls).getPropertyDescriptors();
    }

    @Override // au.com.bytecode.opencsv.bean.MappingStrategy
    public T createBean() throws InstantiationException, IllegalAccessException {
        return this.type.newInstance();
    }

    public Class<T> getType() {
        return this.type;
    }

    public void setType(Class<T> cls) {
        this.type = cls;
    }
}
