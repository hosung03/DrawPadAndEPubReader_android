package com.hosung.drawpadandepubreader.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class Places {
    public static File getScreenshotFolder() {
		File path = new File(Environment.getExternalStorageDirectory(),"/DrawPadAndEPub/");
		path.mkdirs();

		return path;
	}

	public static File getCameraTempFolder() {
		File path = new File(Environment.getExternalStorageDirectory(),"/DrawPadAndEPub/Temp/");
		path.mkdirs();
		File noScanning = new File(path, ".nomedia");
		if (!noScanning.exists())
			try {
				noScanning.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return path;
	}

	public static File getCameraTempFile() {
		return new File(getCameraTempFolder(), "temp.jpg");
	}
}