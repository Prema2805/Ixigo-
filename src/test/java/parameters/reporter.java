package parameters;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class reporter {

    private static final String SCREENSHOTS_DIR = System.getProperty("user.dir")
            + File.separator + "reports" + File.separator + "screenshots";

    public static void generateReport(WebDriver driver, ExtentTest test, Status status, String message) {
        try {
            // Capture screenshot only on FAIL / WARNING (to reduce noise)
            String screenshotPath = (status == Status.FAIL || status == Status.WARNING)
                    ? captureScreenshot(driver, message)
                    : null;

            if (screenshotPath != null) {
                test.log(status, message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else {
                test.log(status, message);
            }
        } catch (Exception e) {
            test.log(status, message);
            test.log(Status.WARNING, "Could not attach screenshot: " + e.getMessage());
        }
    }

    private static String captureScreenshot(WebDriver driver, String message) {
        if (driver == null) return null;

        try {
            File dir = new File(SCREENSHOTS_DIR);
            if (!dir.exists()) dir.mkdirs();

            // Shorten name: hash of message instead of raw message
            String safeHash = hashString(message);
            String fileName = "ss_" + safeHash + "_" + System.currentTimeMillis() + ".png";
            File destFile = new File(dir, fileName);

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, destFile);

            return destFile.getAbsolutePath();
        } catch (Exception ex) {
            return null;
        }
    }

    private static String hashString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return "hashErr";
        }
    }
}
