package atf.drivers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                // Files.createTempDirectory creates a brand new folder every time you create a
                // driver.
                // Chrome uses that folder as its profile, so no clashes.
                // Tests can run in parallel or sequentially without profile fights.
                ChromeOptions options = new ChromeOptions();
                options.addArguments(
                        "--headless=new",
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--disable-extensions",
                        "--remote-allow-origins=*");

                try {
                    // Create temp user data directory
                    // Path userDataDir = Files.createTempDirectory("chrome-profile-");
                    Path userDataDir = Files.createTempDirectory(Path.of("/tmp"), "chrome-profile-");
                    options.addArguments("--user-data-dir=" + userDataDir.toAbsolutePath());

                    // Register shutdown hook to clean up the temp directory
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        try {
                            deleteDirectoryRecursively(userDataDir.toFile());
                            System.out.println("Deleted temp Chrome user data directory: " + userDataDir);
                        } catch (IOException e) {
                            System.err.println("Failed to delete temp directory: " + userDataDir);
                            e.printStackTrace();
                        }
                    }));
                } catch (IOException e) {
                    System.err.println("Failed to create temp Chrome user data directory.");
                    e.printStackTrace();
                }
                System.out.println("🔧 WebDriver created using DriverFactory with browser: " + browser);
                return new ChromeDriver(options);

            default:
                return new ChromeDriver(); // fallback
        }
    }

    // The addShutdownHook ensures cleanup runs when the JVM exits (which Jenkins
    // will do at the end of the build).
    // Works even if the test fails midway — it’s cleanup insurance.
    // This prevents temp folders from filling your container’s disk space over
    // time.
    private static void deleteDirectoryRecursively(java.io.File file) throws IOException {
        if (file.isDirectory()) {
            for (java.io.File child : file.listFiles()) {
                deleteDirectoryRecursively(child);
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }
}