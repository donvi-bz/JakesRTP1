package biz.donvi.JakesRTP1.util;

import java.util.List;

public interface DebugPrintable {

    /**
     * Returns the name of this debug printable object. The name will correspond NOT to the type of object that the
     * debug printer is for, but instead for the printer's use case. Here is a list of the current names, though these
     * may change in the future.
     * <ul>
     *     <li>default</li>
     *     <li>formatted</li>
     *     <li>minecraft</li>
     * </ul>
     * @return The name of this debug printable.
     */
    String getDebugPrintableName();
    /**
     * Provides zero or more lines describing the object.
     * @return Zero or more lines describing the object.
     */
    List<String> infoStrings();
}
