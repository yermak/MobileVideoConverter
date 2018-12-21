package uk.yermak.audiobookconverter.fx;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import uk.yermak.audiobookconverter.Conversion;
import uk.yermak.audiobookconverter.ConversionSubscriber;
import uk.yermak.audiobookconverter.MediaInfo;
import uk.yermak.audiobookconverter.OutputParameters;

/**
 * Created by yermak on 08/09/2018.
 */
public class OutputController implements ConversionSubscriber {

    @FXML
    private ComboBox<String> encoder;

    @FXML
    private ComboBox<String> preset;

    @FXML
    private Spinner<Integer> crf;

    @FXML
    private ComboBox<Quality> vbr;

    enum Quality {LOWEST(1), LOW(2), MEDIUM(3), HIGH(4), HIGHEST(5);
        int VALUE;
        Quality(int i) {
            this.VALUE = i;
        }
    };

    private OutputParameters params;
    private ObservableList<MediaInfo> media;

    @FXML
    private void initialize() {

        encoder.getItems().addAll("HEVC/H265");

        preset.getItems().addAll("ultrafast", "superfast", " veryfast", " faster", "fast", "medium", "slow", "slower", "veryslow", "placebo");



        resetForNewConversion(ConverterApplication.getContext().registerForConversion(this));

        vbr.getItems().addAll(Quality.values());

        encoder.valueProperty().addListener(o -> params.setEncoder(encoder.getValue()));
        preset.valueProperty().addListener(o -> params.setPreset(preset.getValue()));
        crf.valueProperty().addListener(o -> params.setCRF(crf.getValue()));

        vbr.valueProperty().addListener(o -> params.setQuality(vbr.getValue().VALUE));



        vbr.getSelectionModel().select(Quality.HIGH);
        preset.getSelectionModel().select(4);
        encoder.getSelectionModel().select(0);
    }


    @Override
    public void resetForNewConversion(Conversion conversion) {
        params = new OutputParameters();
        conversion.setOutputParameters(params);
        media = conversion.getMedia();
    }
}
