package ro.uaic.info.ip.proiect.b3.controllers.upload;

import java.util.HashMap;
import java.util.Map;

public class FileType {
    private static Map<String,String> fileTypes = new HashMap<String, String>();

    private static void initializeFileTypeMap() {
        fileTypes.put("text/x-sql","sql");
    }

    public static String get(String metaType) {
        initializeFileTypeMap();
        return fileTypes.get(metaType);
    }
}