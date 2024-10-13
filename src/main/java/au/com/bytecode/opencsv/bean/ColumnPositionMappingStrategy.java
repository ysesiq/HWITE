package au.com.bytecode.opencsv.bean;

import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/bean/ColumnPositionMappingStrategy.class */
public class ColumnPositionMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {
    private String[] columnMapping = new String[0];

    @Override // au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy, au.com.bytecode.opencsv.bean.MappingStrategy
    public void captureHeader(CSVReader cSVReader) throws IOException {
    }

    @Override // au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy
    protected String getColumnName(int i) {
        if (null == this.columnMapping || i >= this.columnMapping.length) {
            return null;
        }
        return this.columnMapping[i];
    }

    public String[] getColumnMapping() {
        if (this.columnMapping != null) {
            return (String[]) this.columnMapping.clone();
        }
        return null;
    }

    public void setColumnMapping(String[] strArr) {
        this.columnMapping = strArr != null ? (String[]) strArr.clone() : null;
    }
}
