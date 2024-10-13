package au.com.bytecode.opencsv;

import mcp.mobius.waila.gui.truetyper.TrueTypeFont;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/ResultSetHelperService.class */
public class ResultSetHelperService implements ResultSetHelper {
    public static final int CLOBBUFFERSIZE = 2048;
    private static final int NVARCHAR = -9;
    private static final int NCHAR = -15;
    private static final int LONGNVARCHAR = -16;
    private static final int NCLOB = 2011;

    @Override // au.com.bytecode.opencsv.ResultSetHelper
    public String[] getColumnNames(ResultSet resultSet) throws SQLException {
        ArrayList arrayList = new ArrayList();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            arrayList.add(metaData.getColumnName(i + 1));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @Override // au.com.bytecode.opencsv.ResultSetHelper
    public String[] getColumnValues(ResultSet resultSet) throws SQLException, IOException {
        ArrayList arrayList = new ArrayList();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            arrayList.add(getColumnValue(resultSet, metaData.getColumnType(i + 1), i + 1));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private String handleObject(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    private String handleBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal == null ? "" : bigDecimal.toString();
    }

    private String handleLong(ResultSet resultSet, int i) throws SQLException {
        return resultSet.wasNull() ? "" : Long.toString(resultSet.getLong(i));
    }

    private String handleInteger(ResultSet resultSet, int i) throws SQLException {
        return resultSet.wasNull() ? "" : Integer.toString(resultSet.getInt(i));
    }

    private String handleDate(ResultSet resultSet, int i) throws SQLException {
        Date date = resultSet.getDate(i);
        String str = null;
        if (date != null) {
            str = new SimpleDateFormat("dd-MMM-yyyy").format((java.util.Date) date);
        }
        return str;
    }

    private String handleTime(Time time) {
        if (time == null) {
            return null;
        }
        return time.toString();
    }

    private String handleTimestamp(Timestamp timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        if (timestamp == null) {
            return null;
        }
        return simpleDateFormat.format((java.util.Date) timestamp);
    }

    private String getColumnValue(ResultSet resultSet, int i, int i2) throws SQLException, IOException {
        String str = "";
        switch (i) {
            case LONGNVARCHAR /* -16 */:
            case NCHAR /* -15 */:
            case NVARCHAR /* -9 */:
            case -1:
            case 1:
            case 12:
                str = resultSet.getString(i2);
                break;
            case -7:
            case 2000:
                str = handleObject(resultSet.getObject(i2));
                break;
            case -6:
            case 4:
            case 5:
                str = handleInteger(resultSet, i2);
                break;
            case -5:
                str = handleLong(resultSet, i2);
                break;
            case TrueTypeFont.ALIGN_CENTER /* 2 */:
            case 3:
            case 6:
            case 7:
            case 8:
                str = handleBigDecimal(resultSet.getBigDecimal(i2));
                break;
            case 16:
                str = Boolean.valueOf(resultSet.getBoolean(i2)).toString();
                break;
            case 91:
                str = handleDate(resultSet, i2);
                break;
            case CSVParser.DEFAULT_ESCAPE_CHARACTER /* 92 */:
                str = handleTime(resultSet.getTime(i2));
                break;
            case 93:
                str = handleTimestamp(resultSet.getTimestamp(i2));
                break;
            case 2005:
            case NCLOB /* 2011 */:
                Clob clob = resultSet.getClob(i2);
                if (clob != null) {
                    str = read(clob);
                    break;
                }
                break;
            default:
                str = "";
                break;
        }
        if (str == null) {
            str = "";
        }
        return str;
    }

    private static String read(Clob clob) throws SQLException, IOException {
        StringBuilder sb = new StringBuilder((int) clob.length());
        Reader characterStream = clob.getCharacterStream();
        char[] cArr = new char[CLOBBUFFERSIZE];
        while (true) {
            int read = characterStream.read(cArr, 0, cArr.length);
            if (read == -1) {
                return sb.toString();
            }
            sb.append(cArr, 0, read);
        }
    }
}
