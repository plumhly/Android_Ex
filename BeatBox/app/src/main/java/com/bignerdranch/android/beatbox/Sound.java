package com.bignerdranch.android.beatbox;

public class Sound {
    private String mAssetPath;
    private String nName;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        nName = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getnName() {
        return nName;
    }
}

