package biz.donvi.JakesRTP1.util;

import java.util.List;

public interface MultiDebugPrintProvider {

    List<String> getDebugPrinterNames();

    DebugPrintable getDebugPrinterFor(String name);

}
