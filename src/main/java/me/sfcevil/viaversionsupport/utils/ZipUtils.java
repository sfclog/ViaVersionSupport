package me.sfcevil.viaversionsupport.utils;


import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class ZipUtils {

    public static void zipFiles(Collection<File> resFileList, File zipFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipArchiveOutputStream zipout = new ZipArchiveOutputStream(fos)) {
            for (File resFile : resFileList) {
                if(!resFile.canRead()) continue;
                zipFile(resFile, zipout, "");
            }
        }
    }

    private static void zipFile(File resFile, ZipArchiveOutputStream zipout, String rootpath)
            throws IOException {
        rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator) + resFile.getName();
        rootpath = new String(rootpath.getBytes("UTF-8"), "UTF-8");
        if (!resFile.canRead()) {
            return;
        }
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    if (!(file.getName().contains("playerdata") || file.getName().contains("stats"))) {
                        zipFile(file, zipout, rootpath);
                    }
                }
            }
        } else {
            try (FileInputStream fis = new FileInputStream(resFile)) {
                ZipArchiveEntry zipEntry = new ZipArchiveEntry(rootpath);
                zipout.putArchiveEntry(zipEntry);
                IOUtils.copy(fis, zipout);
                zipout.closeArchiveEntry();
            }
        }
    }
}