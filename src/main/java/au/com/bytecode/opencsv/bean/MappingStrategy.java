package au.com.bytecode.opencsv.bean;

import au.com.bytecode.opencsv.CSVReader;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/bean/MappingStrategy.class */
public interface MappingStrategy<T> {
    PropertyDescriptor findDescriptor(int i) throws IntrospectionException;

    T createBean() throws InstantiationException, IllegalAccessException;

    void captureHeader(CSVReader cSVReader) throws IOException;
}
