package uk.yermak.audiobookconverter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetadataBuilder {

    protected static File prepareMeta(long jobId, MetaData bookInfo, List<MediaInfo> media) throws IOException {
        File metaFile = new File(System.getProperty("java.io.tmpdir"), "FFMETADATAFILE" + jobId);
        List<String> metaData = new ArrayList<>();

        metaData.add(";FFMETADATA1");
        metaData.add("major_brand=MP42");
        metaData.add("minor_version=512");
        metaData.add("compatible_brands=isomiso2");
        metaData.add("media_type=2");
        metaData.add("encoder=" + "https://github.com/yermak/AudioBookConverter");

        FileUtils.writeLines(metaFile, "UTF-8", metaData);
        return metaFile;

    }
}
