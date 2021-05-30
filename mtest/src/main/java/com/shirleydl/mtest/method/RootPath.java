package com.shirleydl.mtest.method;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * 获取当前根目录绝对路径
 */
public class RootPath {
    public static String getPath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!path.exists()) {
            path = new File("");
        }
        return path.getAbsolutePath();
    }
}
