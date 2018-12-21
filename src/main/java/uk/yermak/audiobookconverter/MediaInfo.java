package uk.yermak.audiobookconverter;

/**
 * Created by Yermak on 03-Jan-18.
 */
public interface MediaInfo {

    String getFileName();


    long getDuration();

    void setDuration(long millis);
}
