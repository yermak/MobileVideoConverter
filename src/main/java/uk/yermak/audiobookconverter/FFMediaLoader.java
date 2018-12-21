package uk.yermak.audiobookconverter;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FFMediaLoader implements MediaLoader{

    private final StatusChangeListener listener;
    private List<String> fileNames;
    private static final String FFPROBE = new File("external/x64/ffprobe.exe").getAbsolutePath();
    private static final ExecutorService mediaExecutor = Executors.newSingleThreadExecutor();

    public FFMediaLoader(List<String> files, Conversion conversion) {
        this.fileNames = files;
        Collections.sort(fileNames);

        //TODO add latch to remove listener at the end.
        listener = new StatusChangeListener();
//        ConverterApplication.getContext().addStatusChangeListener(listener);
    }

    public List<MediaInfo> loadMediaInfo() {
        try {
            FFprobe ffprobe = new FFprobe(FFPROBE);
            List<MediaInfo> media = new ArrayList<>();
            for (String fileName : fileNames) {
                Future futureLoad = mediaExecutor.submit(new MediaInfoCallable(ffprobe, fileName));
                MediaInfo mediaInfo = new MediaInfoProxy(fileName, futureLoad);
                media.add(mediaInfo);
            }
            return media;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static class MediaInfoCallable implements Callable<MediaInfo> {

        private final String filename;
        private FFprobe ffprobe;

        public MediaInfoCallable(FFprobe ffprobe, String filename) {
            this.ffprobe = ffprobe;
            this.filename = filename;
        }

        @Override
        public MediaInfo call() throws Exception {
            try {
                FFmpegProbeResult probeResult = ffprobe.probe(filename);
                FFmpegFormat format = probeResult.getFormat();
                MediaInfoBean mediaInfo = new MediaInfoBean(filename);

                List<FFmpegStream> streams = probeResult.getStreams();
                for (int i = 0; i < streams.size(); i++) {
                    FFmpegStream fFmpegStream = streams.get(i);
                    if ("h264".equals(fFmpegStream.codec_name)) {
                        mediaInfo.setDuration((long) fFmpegStream.duration * 1000);
                    }
                }
                return mediaInfo;
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }


}
