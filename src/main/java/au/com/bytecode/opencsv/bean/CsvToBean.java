package au.com.bytecode.opencsv.bean;

import au.com.bytecode.opencsv.CSVReader;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/bean/CsvToBean.class */
public class CsvToBean<T> {
    private Map<Class<?>, PropertyEditor> editorMap = null;

    public List<T> parse(MappingStrategy<T> mappingStrategy, Reader reader) {
        return parse(mappingStrategy, new CSVReader(reader));
    }

    public List<T> parse(MappingStrategy<T> mappingStrategy, CSVReader cSVReader) {
        try {
            mappingStrategy.captureHeader(cSVReader);
            ArrayList arrayList = new ArrayList();
            while (true) {
                String[] readNext = cSVReader.readNext();
                if (null == readNext) {
                    return arrayList;
                }
                arrayList.add(processLine(mappingStrategy, readNext));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing CSV!", e);
        }
    }

    protected T processLine(MappingStrategy<T> mappingStrategy, String[] strArr) throws IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        T createBean = mappingStrategy.createBean();
        for (int i = 0; i < strArr.length; i++) {
            PropertyDescriptor findDescriptor = mappingStrategy.findDescriptor(i);
            if (null != findDescriptor) {
                findDescriptor.getWriteMethod().invoke(createBean, convertValue(checkForTrim(strArr[i], findDescriptor), findDescriptor));
            }
        }
        return createBean;
    }

    private String checkForTrim(String str, PropertyDescriptor propertyDescriptor) {
        return trimmableProperty(propertyDescriptor) ? str.trim() : str;
    }

    private boolean trimmableProperty(PropertyDescriptor propertyDescriptor) {
        return !propertyDescriptor.getPropertyType().getName().contains("String");
    }

    protected Object convertValue(String str, PropertyDescriptor propertyDescriptor) throws InstantiationException, IllegalAccessException {
        PropertyEditor propertyEditor = getPropertyEditor(propertyDescriptor);
        Object obj = str;
        if (null != propertyEditor) {
            propertyEditor.setAsText(str);
            obj = propertyEditor.getValue();
        }
        return obj;
    }

    private PropertyEditor getPropertyEditorValue(Class<?> cls) {
        if (this.editorMap == null) {
            this.editorMap = new HashMap();
        }
        PropertyEditor propertyEditor = this.editorMap.get(cls);
        if (propertyEditor == null) {
            propertyEditor = PropertyEditorManager.findEditor(cls);
            addEditorToMap(cls, propertyEditor);
        }
        return propertyEditor;
    }

    private void addEditorToMap(Class<?> cls, PropertyEditor propertyEditor) {
        if (propertyEditor != null) {
            this.editorMap.put(cls, propertyEditor);
        }
    }

    protected PropertyEditor getPropertyEditor(PropertyDescriptor propertyDescriptor) throws InstantiationException, IllegalAccessException {
        Class propertyEditorClass = propertyDescriptor.getPropertyEditorClass();
        return null != propertyEditorClass ? (PropertyEditor) propertyEditorClass.newInstance() : getPropertyEditorValue(propertyDescriptor.getPropertyType());
    }
}
