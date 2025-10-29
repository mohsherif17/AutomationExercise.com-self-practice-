package Utils.Reportes;

import Utils.Logs.LogsManager;
import Utils.OSUtils;
import Utils.TerminalUtils;
import Utils.Reportes.AllureConstants;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AllureBinaryManager {
    //get the version
    private static class LazyHolder {
        static final String VERSION = resolveVersion();

        private static String resolveVersion() {
            try {
                String url= Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest")
                        .followRedirects(true)
                        .execute().url().toString();
                return url.split("/tag/")[1];
            }
            catch (Exception e) {
                throw new IllegalStateException("Error while resolving allure version: " + e.getMessage());
            }
        }
    }

    public static void downloadAndExtract() {
        try {
            String version=LazyHolder.VERSION;
            Path extractionDir= Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version);

            if (!Files.exists(extractionDir)){
                LogsManager.info("Allure binaries already exist");
                return;
            }

            //giving permissions if os is not windows
            if (!OSUtils.getCurrentOS().equals(OSUtils.OsType.WINDOWS)){
                TerminalUtils.executeTerminalCommand("chmod","u+x",AllureConstants.USER_DIR.toString());
            }

            Path zipPath = downloadZip(version);
            extractZip(zipPath);

            LogsManager.info("Allure binaries downloaded and extracted successfully");

            //giving permissions to execute if os is not windows
            if (!OSUtils.getCurrentOS().equals(OSUtils.OsType.WINDOWS)){
                TerminalUtils.executeTerminalCommand("chmod","u+x",getExecutable().toString());
            }

            Files.deleteIfExists(Files.list(AllureConstants.EXTRACTION_DIR).filter(p -> p.toString().endsWith(".zip")).findFirst().get());

        }
        catch (Exception e) {
            LogsManager.error("Error while downloading and extracting allure binaries: " + e.getMessage());
        }

    }


    public static Path getExecutable(){

        String version = LazyHolder.VERSION;
        //C:\Users\Maka\.m2\repo\allure\allure-2.35.1\bin
        Path binaryPath = Paths.get(AllureConstants.EXTRACTION_DIR.toString(),"allure-"+version,"bin","allure");
        return OSUtils.getCurrentOS()==OSUtils.OsType.WINDOWS
                ? binaryPath.resolveSibling(binaryPath.getFileName()+".bat")
                : binaryPath;
    }



    //download Allure Zip file
    private static Path downloadZip(String version) {
        try {
            String url = AllureConstants.ALLURE_ZIP_BASE_URL + version + "/allure-commandline-" + version + ".zip";
            Path zipFile= Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version + ".zip");
            if (!Files.exists(zipFile)){
                Files.createDirectories(AllureConstants.EXTRACTION_DIR);
                try (BufferedInputStream in = new BufferedInputStream(new URI(url).toURL().openStream());
                     OutputStream out=Files.newOutputStream(zipFile)) {
                    in.transferTo(out);
                }
                catch (Exception e) {
                    LogsManager.error("Error while downloading allure zip: " + e.getMessage());
                }
            }
            return zipFile;
        }
        catch (Exception e) {
            LogsManager.error("Error while downloading allure zip: " + e.getMessage());
            return Paths.get(" ");
        }
    }

    //extract Allure Zip file
    private static void extractZip(Path zipPath) {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path filePath = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), entry.getName());
                if (!entry.isDirectory()) {
                    Files.createDirectories(filePath);
                }else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zipInputStream, filePath);
                }
            }
        } catch (Exception e) {
            LogsManager.error("Error while extracting allure zip: " + e.getMessage());
        }
    }

}
