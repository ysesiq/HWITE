package au.com.bytecode.opencsv;

import java.io.IOException;
import java.util.ArrayList;

/* loaded from: Waila_1.5.2a.jar:au/com/bytecode/opencsv/CSVParser.class */
public class CSVParser {
    private final char separator;
    private final char quotechar;
    private final char escape;
    private final boolean strictQuotes;
    private String pending;
    private boolean inField;
    private final boolean ignoreLeadingWhiteSpace;
    public static final char DEFAULT_SEPARATOR = ',';
    public static final int INITIAL_READ_SIZE = 128;
    public static final char DEFAULT_QUOTE_CHARACTER = '\"';
    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
    public static final boolean DEFAULT_STRICT_QUOTES = false;
    public static final boolean DEFAULT_IGNORE_LEADING_WHITESPACE = true;
    public static final char NULL_CHARACTER = 0;

    public CSVParser() {
        this(',', '\"', '\\');
    }

    public CSVParser(char c) {
        this(c, '\"', '\\');
    }

    public CSVParser(char c, char c2) {
        this(c, c2, '\\');
    }

    public CSVParser(char c, char c2, char c3) {
        this(c, c2, c3, false);
    }

    public CSVParser(char c, char c2, char c3, boolean z) {
        this(c, c2, c3, z, true);
    }

    public CSVParser(char c, char c2, char c3, boolean z, boolean z2) {
        this.inField = false;
        if (anyCharactersAreTheSame(c, c2, c3)) {
            throw new UnsupportedOperationException("The separator, quote, and escape characters must be different!");
        }
        if (c == 0) {
            throw new UnsupportedOperationException("The separator character must be defined!");
        }
        this.separator = c;
        this.quotechar = c2;
        this.escape = c3;
        this.strictQuotes = z;
        this.ignoreLeadingWhiteSpace = z2;
    }

    private boolean anyCharactersAreTheSame(char c, char c2, char c3) {
        return isSameCharacter(c, c2) || isSameCharacter(c, c3) || isSameCharacter(c2, c3);
    }

    private boolean isSameCharacter(char c, char c2) {
        return c != 0 && c == c2;
    }

    public boolean isPending() {
        return this.pending != null;
    }

    public String[] parseLineMulti(String str) throws IOException {
        return parseLine(str, true);
    }

    public String[] parseLine(String str) throws IOException {
        return parseLine(str, false);
    }

    private String[] parseLine(String str, boolean z) throws IOException {
        if (!z && this.pending != null) {
            this.pending = null;
        }
        if (str == null) {
            if (this.pending != null) {
                String str2 = this.pending;
                this.pending = null;
                return new String[]{str2};
            }
            return null;
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder(128);
        boolean z2 = false;
        if (this.pending != null) {
            sb.append(this.pending);
            this.pending = null;
            z2 = true;
        }
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == this.escape) {
                if (isNextCharacterEscapable(str, z2 || this.inField, i)) {
                    sb.append(str.charAt(i + 1));
                    i++;
                }
            } else if (charAt == this.quotechar) {
                if (isNextCharacterEscapedQuote(str, z2 || this.inField, i)) {
                    sb.append(str.charAt(i + 1));
                    i++;
                } else {
                    if (!this.strictQuotes && i > 2 && str.charAt(i - 1) != this.separator && str.length() > i + 1 && str.charAt(i + 1) != this.separator) {
                        if (this.ignoreLeadingWhiteSpace && sb.length() > 0 && isAllWhiteSpace(sb)) {
                            sb.setLength(0);
                        } else {
                            sb.append(charAt);
                        }
                    }
                    z2 = !z2;
                }
                this.inField = !this.inField;
            } else if (charAt == this.separator && !z2) {
                arrayList.add(sb.toString());
                sb.setLength(0);
                this.inField = false;
            } else if (!this.strictQuotes || z2) {
                sb.append(charAt);
                this.inField = true;
            }
            i++;
        }
        if (z2) {
            if (!z) {
                throw new IOException("Un-terminated quoted field at end of CSV line");
            }
            sb.append(CSVWriter.DEFAULT_LINE_END);
            this.pending = sb.toString();
            sb = null;
        }
        if (sb != null) {
            arrayList.add(sb.toString());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private boolean isNextCharacterEscapedQuote(String str, boolean z, int i) {
        return z && str.length() > i + 1 && str.charAt(i + 1) == this.quotechar;
    }

    protected boolean isNextCharacterEscapable(String str, boolean z, int i) {
        return z && str.length() > i + 1 && (str.charAt(i + 1) == this.quotechar || str.charAt(i + 1) == this.escape);
    }

    protected boolean isAllWhiteSpace(CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
