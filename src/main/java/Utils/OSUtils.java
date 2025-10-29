package Utils;

import Utils.DataReader.PropertyReader;

public class OSUtils {
    public enum OsType {
        WINDOWS,
        MAC,
        LINUX,
        OTHER
    }
    public static OsType getCurrentOS() {
        String os = PropertyReader.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return OsType.WINDOWS;
        } else if (os.contains("mac")) {
            return OsType.MAC;
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return OsType.LINUX;
        } else {
            return OsType.OTHER;
        }
    }
}
