package au.com.bytecode.opencsv.bean;

import java.util.HashMap;
import java.util.Map;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/bean/HeaderColumnNameTranslateMappingStrategy.class */
public class HeaderColumnNameTranslateMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {
    private Map<String, String> columnMapping = new HashMap();

    @Override // au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy
    protected String getColumnName(int i) {
        if (i < this.header.length) {
            return this.columnMapping.get(this.header[i].toUpperCase());
        }
        return null;
    }

    public Map<String, String> getColumnMapping() {
        return this.columnMapping;
    }

    public void setColumnMapping(Map<String, String> map) {
        for (String str : map.keySet()) {
            this.columnMapping.put(str.toUpperCase(), map.get(str));
        }
    }
}
