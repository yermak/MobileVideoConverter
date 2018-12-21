package uk.yermak.audiobookconverter;

public class OutputParameters {

    private int quality = 3;
    private String encoder;
    private String preset;
    private int CRF = 20;

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public void setEncoder(String value) {
        this.encoder = value;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String getPreset() {
        return preset;
    }

    public void setCRF(Integer crf) {
        this.CRF = crf;
    }

    public int getCRF() {
        return CRF;
    }

    public String getEncoder() {
        return "libx265";
    }
}
