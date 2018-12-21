package uk.yermak.audiobookconverter;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Track;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by yermak on 1/10/2018.
 */
public class FXMediaLoader implements MediaLoader {

    private final StatusChangeListener listener;
    private List<String> fileNames;
    private Conversion conversion;



    public FXMediaLoader(List<String> files, Conversion conversion) {
        this.fileNames = files;
        this.conversion = conversion;
        Collections.sort(fileNames);

        listener = new StatusChangeListener();
        conversion.addStatusChangeListener(listener);
    }

    @Override
    public List<MediaInfo> loadMediaInfo() {
        List<MediaInfo> media = new ArrayList<>();
        for (String fileName : fileNames) {

            CompletableFuture completableFuture = new CompletableFuture();
            MediaInfo mediaInfo = new MediaInfoProxy(fileName, completableFuture);
            media.add(mediaInfo);

            Media m = new Media(new File(fileName).toURI().toASCIIString());
            MediaPlayer mediaPlayer = new MediaPlayer(m);
            MediaView viewer = new MediaView(mediaPlayer);

            mediaPlayer.setOnReady(() -> loadMetadata(m, fileName, completableFuture));
        }
        return media;
    }

    private void loadMetadata(Media m, String fileName, CompletableFuture completableFuture) {
//        ObservableMap<String, Object> metadata = m.getMetadata();

        MediaInfoBean mediaInfo = new MediaInfoBean(fileName);

        if (!m.getTracks().isEmpty()) {
            Track track = m.getTracks().get(0);
            track.getMetadata();
            mediaInfo.setDuration((long) m.getDuration().toMillis());
        }
        completableFuture.complete(mediaInfo);
    }
}
