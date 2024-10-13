package au.com.bytecode.opencsv;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/CSVWriter.class */
public class CSVWriter implements Closeable {
    public static final int INITIAL_STRING_SIZE = 128;
    private Writer rawWriter;
    private PrintWriter pw;
    private char separator;
    private char quotechar;
    private char escapechar;
    private String lineEnd;
    public static final char DEFAULT_ESCAPE_CHARACTER = '\"';
    public static final char DEFAULT_SEPARATOR = ',';
    public static final char DEFAULT_QUOTE_CHARACTER = '\"';
    public static final char NO_QUOTE_CHARACTER = 0;
    public static final char NO_ESCAPE_CHARACTER = 0;
    public static final String DEFAULT_LINE_END = "\n";
    private ResultSetHelper resultService;

    public CSVWriter(Writer writer) {
        this(writer, ',');
    }

    public CSVWriter(Writer writer, char c) {
        this(writer, c, '\"');
    }

    public CSVWriter(Writer writer, char c, char c2) {
        this(writer, c, c2, '\"');
    }

    public CSVWriter(Writer writer, char c, char c2, char c3) {
        this(writer, c, c2, c3, DEFAULT_LINE_END);
    }

    public CSVWriter(Writer writer, char c, char c2, String str) {
        this(writer, c, c2, '\"', str);
    }

    public CSVWriter(Writer writer, char c, char c2, char c3, String str) {
        this.resultService = new ResultSetHelperService();
        this.rawWriter = writer;
        this.pw = new PrintWriter(writer);
        this.separator = c;
        this.quotechar = c2;
        this.escapechar = c3;
        this.lineEnd = str;
    }

    public void writeAll(List<String[]> list) {
        for (String[] strArr : list) {
            writeNext(strArr);
        }
    }

    protected void writeColumnNames(ResultSet resultSet) throws SQLException {
        writeNext(this.resultService.getColumnNames(resultSet));
    }

    public void writeAll(ResultSet resultSet, boolean z) throws SQLException, IOException {
        if (z) {
            writeColumnNames(resultSet);
        }
        while (resultSet.next()) {
            writeNext(this.resultService.getColumnValues(resultSet));
        }
    }

    public void writeNext(String[] strArr) {
        if (strArr == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(128);
        for (int i = 0; i < strArr.length; i++) {
            if (i != 0) {
                sb.append(this.separator);
            }
            String str = strArr[i];
            if (str != null) {
                if (this.quotechar != 0) {
                    sb.append(this.quotechar);
                }
                sb.append((CharSequence) (stringContainsSpecialCharacters(str) ? processLine(str) : str));
                if (this.quotechar != 0) {
                    sb.append(this.quotechar);
                }
            }
        }
        sb.append(this.lineEnd);
        this.pw.write(sb.toString());
    }

    private boolean stringContainsSpecialCharacters(String str) {
        return (str.indexOf(this.quotechar) == -1 && str.indexOf(this.escapechar) == -1) ? false : true;
    }

    protected StringBuilder processLine(String str) {
        StringBuilder sb = new StringBuilder(128);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (this.escapechar != 0 && charAt == this.quotechar) {
                sb.append(this.escapechar).append(charAt);
            } else if (this.escapechar == 0 || charAt != this.escapechar) {
                sb.append(charAt);
            } else {
                sb.append(this.escapechar).append(charAt);
            }
        }
        return sb;
    }

    public void flush() throws IOException {
        this.pw.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        flush();
        this.pw.close();
        this.rawWriter.close();
    }

    public boolean checkError() {
        return this.pw.checkError();
    }

    public void setResultService(ResultSetHelper resultSetHelper) {
        this.resultService = resultSetHelper;
    }
}
