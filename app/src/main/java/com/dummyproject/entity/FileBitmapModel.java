package com.dummyproject.entity;

import android.graphics.Bitmap;

import java.io.File;

public class FileBitmapModel {

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    File file;
    Bitmap bitmap;

    public FileBitmapModel(File file, Bitmap bitmap)
    {
        this.file=file;
        this.bitmap=bitmap;
    }
    public FileBitmapModel()
    {

    }

}
