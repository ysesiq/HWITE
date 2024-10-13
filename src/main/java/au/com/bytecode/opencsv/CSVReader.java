package au.com.bytecode.opencsv;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/CSVReader.class */
public class CSVReader implements Closeable {
    private BufferedReader br;
    private boolean hasNext;
    private CSVParser parser;
    private int skipLines;
    private boolean linesSkiped;
    public static final int DEFAULT_SKIP_LINES = 0;

    public CSVReader(Reader reader) {
        this(reader, ',', '\"', '\\');
    }

    public CSVReader(Reader reader, char c) {
        this(reader, c, '\"', '\\');
    }

    public CSVReader(Reader reader, char c, char c2) {
        this(reader, c, c2, '\\', 0, false);
    }

    public CSVReader(Reader reader, char c, char c2, boolean z) {
        this(reader, c, c2, '\\', 0, z);
    }

    public CSVReader(Reader reader, char c, char c2, char c3) {
        this(reader, c, c2, c3, 0, false);
    }

    public CSVReader(Reader reader, char c, char c2, int i) {
        this(reader, c, c2, '\\', i, false);
    }

    public CSVReader(Reader reader, char c, char c2, char c3, int i) {
        this(reader, c, c2, c3, i, false);
    }

    public CSVReader(Reader reader, char c, char c2, char c3, int i, boolean z) {
        this(reader, c, c2, c3, i, z, true);
    }

    public CSVReader(Reader reader, char c, char c2, char c3, int i, boolean z, boolean z2) {
        this.hasNext = true;
        this.br = new BufferedReader(reader);
        this.parser = new CSVParser(c, c2, c3, z, z2);
        this.skipLines = i;
    }

    public List<String[]> readAll() throws IOException {
        ArrayList arrayList = new ArrayList();
        while (this.hasNext) {
            String[] readNext = readNext();
            if (readNext != null) {
                arrayList.add(readNext);
            }
        }
        return arrayList;
    }

    public String[] readNext() throws IOException {
        String[] strArr = null;
        do {
            String nextLine = getNextLine();
            if (!this.hasNext) {
                return strArr;
            }
            String[] parseLineMulti = this.parser.parseLineMulti(nextLine);
            if (parseLineMulti.length > 0) {
                if (strArr == null) {
                    strArr = parseLineMulti;
                } else {
                    String[] strArr2 = new String[strArr.length + parseLineMulti.length];
                    System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                    System.arraycopy(parseLineMulti, 0, strArr2, strArr.length, parseLineMulti.length);
                    strArr = strArr2;
                }
            }
        } while (this.parser.isPending());
        return strArr;
    }

    private String getNextLine() throws IOException {
        if (!this.linesSkiped) {
            for (int i = 0; i < this.skipLines; i++) {
                this.br.readLine();
            }
            this.linesSkiped = true;
        }
        String readLine = this.br.readLine();
        if (readLine == null) {
            this.hasNext = false;
        }
        if (this.hasNext) {
            return readLine;
        }
        return null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.br.close();
    }
}
