package biz.donvi.jakesRTP1API.util;

import java.util.List;

public interface MultiDebugPrintProvider {

    List<String> getDebugPrinterNames();

    DebugPrintable getDebugPrinterFor(String name);

}
