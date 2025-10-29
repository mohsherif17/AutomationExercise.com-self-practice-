package Media;

import Utils.DataReader.PropertyReader;
import Utils.Logs.LogsManager;
import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;

import java.io.File;

public class VideoRecManger {


    private static final String RECORDINGS_PATH = "target/test-output/recordings/";
    private static final ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();

    public static void startRecording() {
        if (PropertyReader.getProperty("recordTests").equalsIgnoreCase("true")) {
            try {
                if (recorder.get() != null) {
                    return;
                }

                // Ensure the recordings directory exists
                File recordingsDir = new File(RECORDINGS_PATH);
                if (!recordingsDir.exists()) {
                    recordingsDir.mkdirs();
                }

                // TODO: Check if execution type is local before recording
                // Configure the recorder to use the custom directory and file name
                if(PropertyReader.getProperty("executionType").equalsIgnoreCase("local")) {
                    IVideoRecorder videoRecorder =
                            RecorderFactory.getRecorder(VideoRecorder.conf().recorderType());

                    recorder.set(videoRecorder);

                    // Start recording
                    recorder.get().start();
                    LogsManager.info(" Recording started.");
                }
            } catch (Exception e) {
                LogsManager.error(" Failed to start screen recording: " + e.getMessage());
            }
        }
    }

    /**
     * Stops screen recording.
     */
    public static void stopRecording() {
        try {
            IVideoRecorder activeRecorder = recorder.get();
            if (activeRecorder != null) {
                activeRecorder.stopAndSave(PropertyReader.getProperty("video.folder"));
                LogsManager.info(" Recording stopped and saved.");
                recorder.remove();
            }
        } catch (Exception e) {
            LogsManager.error(" Failed to stop screen recording: " + e.getMessage());
        }
    }
}
