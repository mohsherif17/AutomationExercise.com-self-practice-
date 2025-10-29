package Utils.Reportes;

import Utils.DataReader.PropertyReader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AllureConstants {
    public static final Path USER_DIR= Paths.get(PropertyReader.getProperty("user.dir"), File.separator);
    public static final Path USER_HOME= Paths.get(PropertyReader.getProperty("user.home"), File.separator);

    public static final Path RESULTS_FOLDER= Paths.get(String.valueOf(USER_DIR), "test-output", "allure-results", File.separator);
    public static final Path REPORT_PATH= Paths.get(String.valueOf(USER_DIR), "test-output", "allure-report", File.separator);   //single report
    public static final Path FULL_REPORT_PATH= Paths.get(String.valueOf(USER_DIR), "test-output","full-report", File.separator);

    public static final Path HISTORY_FOLDER= Paths.get(FULL_REPORT_PATH.toString(), "history", File.separator);
    public static final Path RESULTS_HISTORY_FOLDER= Paths.get(RESULTS_FOLDER.toString(), "history", File.separator);

    public static final String INDEX_HTML= "index.html";
    public static final String REPORT_PREFIX="AllureReport_"; //timestamp to be added
    public static final String REPORT_EXTENSION=".html";

    public static final String ALLURE_ZIP_BASE_URL="https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/";     //to get latest version

    public static final Path EXTRACTION_DIR= Paths.get(String.valueOf(USER_HOME), ".m2/repository/allure", File.separator);
}
