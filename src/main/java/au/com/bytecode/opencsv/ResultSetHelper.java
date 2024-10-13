package au.com.bytecode.opencsv;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/ResultSetHelper.class */
public interface ResultSetHelper {
    String[] getColumnNames(ResultSet resultSet) throws SQLException;

    String[] getColumnValues(ResultSet resultSet) throws SQLException, IOException;
}
