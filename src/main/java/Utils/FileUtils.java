package Utils;

import Utils.DataReader.PropertyReader;
import Utils.Logs.LogsManager;

import java.io.File;


import static org.apache.commons.io.FileUtils.*;

public class FileUtils {
    private static final String USER_DIR= PropertyReader.getProperty("user.dir")+File.separator;

    private FileUtils() {
        // Private constructor to prevent instantiation
    }

    //rename
    public static void RenameFile(String oldFilePath, String newFilePath) {
        try {
            var targetFile = new File(oldFilePath);
            String targetDir = targetFile.getParentFile().getAbsolutePath();
            File newFile = new File(targetDir + "/" + newFilePath);
            if (targetFile.getPath().equals(newFile.getPath())) {
                copyFile(targetFile, newFile);
                org.apache.commons.io.FileUtils.deleteQuietly(targetFile);
                LogsManager.info("file renamed successfully from :" + oldFilePath + " to :" + newFilePath);
            }else {
                LogsManager.info("the old file name and new file name are the same, no renaming performed.");
            }

        } catch (Exception e) {
            LogsManager.error("failed to rename the file" + e.getMessage());
        }
    }

    //create directory
    public static void CreateDirectory(String PAth) {

        try {
            File Directory = new File(USER_DIR + PAth);
            if (!Directory.exists()) {
                Directory.mkdir();
                LogsManager.info("directory created successfully at :" + USER_DIR + PAth);
            }
        } catch (Exception e) {
            LogsManager.error("failed to create the directory" + e.getMessage());
        }
    }
    public static void createDirectory(String path){
        try {
            File file=new File(USER_DIR+path);
            if (!file.exists()){
                file.mkdirs();
                LogsManager.info("Directory created: " + path);
            }
        }
        catch (Exception e){
            LogsManager.error("Error while creating directory: " + e.getMessage());
        }
    }

    //clean directory
    public static void cleanDirectory(File file){
        try {
            org.apache.commons.io.FileUtils.deleteQuietly(file);

        }
        catch (Exception e){
            LogsManager.error("Error while cleaning directory: " + e.getMessage());
        }
    }
    //cleaning direcctory
    public static void CleanDirectory(File file) {
        try {
            deleteQuietly(file);
        } catch (Exception e) {
            LogsManager.error("Failed to clear the file" + e.getMessage());
        }
    }
    public static void forceClean(File file) {
        try {
            forceDelete(file);
        } catch (Exception e) {
            LogsManager.error("Failed to clear the file" + e.getMessage());
        }
    }
}
