package com.shirleydl.mtest.method;

import org.springframework.web.multipart.MultipartFile;

public class CheckFileUtil {
    /**
     * 获得文件类型
     *
     * @param file
     * @return 0:其他文件，1:excel，2:xmind，3:png/jpg
     */
    public Integer getFileType(MultipartFile file) {
        //判断文件是否存在
        if (null == file) {
            return null;
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            if (!fileName.endsWith("xmind")) {
                return 0;
            }
            return 2;
        } else {
            return 1;
        }
    }
}
