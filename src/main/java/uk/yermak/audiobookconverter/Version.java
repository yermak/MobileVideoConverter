package uk.yermak.audiobookconverter;

public class Version {
    public static final int MAJOR = 1;
    public static final int MINOR = 0;
    public static final int BUILD = 0;

    public Version() {
    }

    public static String getVersionString() {
        //TODO add load version from the build number.
        return "MobileVideoConverter " + MAJOR + "." + MINOR + (BUILD != 0 ? "." + BUILD : "");
    }
}
