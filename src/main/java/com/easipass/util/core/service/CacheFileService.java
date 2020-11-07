package com.easipass.util.core.service;

import com.easipass.util.core.config.Project;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.InfoException;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.FileUtil;
import com.easipass.util.core.util.StringUtil;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存文件服务
 *
 * @author ZJ
 * */
public final class CacheFileService {

    /**
     * 添加缓存文件
     *
     * @param fileName 文件名
     * @param inputStream 文件流
     *
     * @return 文件绝对路径
     * */
    public String add(String fileName, InputStream inputStream) {
        File file = new File(Project.CACHE_PATH, DateUtil.getTime() + "-" + fileName);
        FileUtil.createFile(file, inputStream);
        Project.CACHE_FILE.add(file.getName());
        return file.getAbsolutePath();
    }

    /**
     * 删除缓存文件
     *
     * @param fileName 文件名
     * */
    public void delete(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            throw new InfoException("请输入文件名");
        }
        Project.CACHE_FILE.removeIf(fileName::equals);
        FileUtil.delete(new File(Project.CACHE_PATH, fileName));
    }

    /**
     * 获取所有缓存文件
     *
     * @return 所有缓存文件
     * */
    public List<String> getAll() {
        File file = new File(Project.CACHE_PATH);

        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new ErrorException("创建缓存目录失败");
            }
        }

        File[] files = file.listFiles();
        List<String> fileList = new ArrayList<>();

        if (files == null) {
            throw new ErrorException("files is null");
        }

        for (File file1 : files) {
            if (file1.isFile()) {
                String fileName = file1.getName();
                boolean b = false;
                for (String s : Project.CACHE_FILE) {
                    if (fileName.equals(s)) {
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    fileList.add(fileName);
                }
            }
        }

        return fileList;
    }

}