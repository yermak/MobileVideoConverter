package uk.yermak.audiobookconverter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BatchConversionStrategy implements ConversionStrategy {
    private final ExecutorService executorService = Executors.newWorkStealingPool();
    private final StatusChangeListener listener;
    private Conversion conversion;

    protected Map<String, ProgressCallback> progressCallbacks;


    public BatchConversionStrategy(Conversion conversion, Map<String, ProgressCallback> progressCallbacks) {
        this.conversion = conversion;
        this.progressCallbacks = progressCallbacks;
        listener = new StatusChangeListener();
        conversion.addStatusChangeListener(listener);
    }

    protected String getTempFileName(long jobId, int index, String extension) {
        return "";
    }

    public void run() {
        List<Future<ConverterOutput>> futures = new ArrayList<>();
        try {
            for (int i = 0; i < conversion.getMedia().size(); ++i) {
                MediaInfo mediaInfo = conversion.getMedia().get(i);
                String outputFileName = this.determineOutputFilename(mediaInfo.getFileName());
                Future<ConverterOutput> converterFuture =
                        executorService
                                .submit(new FFMpegConverter(conversion, conversion.getOutputParameters(), mediaInfo, outputFileName, progressCallbacks.get(mediaInfo.getFileName())));
                futures.add(converterFuture);
            }
            conversion.finished();
        } finally {
            conversion.removeStatusChangeListener(listener);
        }
    }

    private String determineOutputFilename(String inputFilename) {
        String outputFilename;
        if (conversion.getOutputDestination() == null) {
            outputFilename = inputFilename.replaceAll("(?i)\\.mp4", "-h265.mp4");
        } else {
            File file = new File(inputFilename);
            File outFile = new File(conversion.getOutputDestination(), file.getName());
            outputFilename = outFile.getAbsolutePath().replaceAll("(?i)\\.mp4", "-h265.mp4");
        }

        if (!outputFilename.endsWith(".mp4")) {
            outputFilename = outputFilename + ".mp4";
        }

        return Utils.makeFilenameUnique(outputFilename);
    }


    protected File prepareFiles(long jobId) throws IOException {
        File fileListFile = new File(System.getProperty("java.io.tmpdir"), "filelist." + jobId + ".txt");
        List<String> outFiles = IntStream.range(0, conversion.getMedia().size()).mapToObj(i -> "file '" + getTempFileName(jobId, i, ".mp4") + "'").collect(Collectors.toList());

        FileUtils.writeLines(fileListFile, "UTF-8", outFiles);

        return fileListFile;
    }

}
