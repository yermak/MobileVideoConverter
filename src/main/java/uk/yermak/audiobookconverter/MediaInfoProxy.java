package uk.yermak.audiobookconverter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Yermak on 03-Jan-18.
 */
public class MediaInfoProxy implements MediaInfo {
    private final String filename;
    private final Future<MediaInfo> futureLoad;

    public MediaInfoProxy(String filename, Future futureLoad) {
        this.filename = filename;
        this.futureLoad = futureLoad;
    }

    private MediaInfo getMediaInfo() {
        try {
            return futureLoad.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setDuration(long duration) {
        getMediaInfo().setDuration(duration);
    }

    @Override
    public long getDuration() {
        return getMediaInfo().getDuration();
    }

    @Override
    public String getFileName() {
        return filename;
    }

    @Override
    public String toString() {
        return filename;
    }
}
