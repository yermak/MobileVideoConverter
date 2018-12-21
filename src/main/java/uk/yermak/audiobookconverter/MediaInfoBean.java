package uk.yermak.audiobookconverter;

/**
 * Created by Yermak on 02-Jan-18.
 */
public class MediaInfoBean implements MediaInfo {
    private String fileName;
    private long duration;

    public MediaInfoBean(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public void setDuration(long millis) {
        this.duration = millis;
    }
}
