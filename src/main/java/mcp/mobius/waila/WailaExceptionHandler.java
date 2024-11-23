package mcp.mobius.waila;

import java.util.ArrayList;
import java.util.List;

public class WailaExceptionHandler {
    private static final ArrayList<String> errs = new ArrayList<>();

    public static List<String> handleErr(Throwable e, String className, List<String> currenttip) {
        if (!errs.contains(className)) {
            errs.add(className);
        }
        if (currenttip != null) {
            currenttip.add("<ERROR>");
        }
        return currenttip;
    }
}
