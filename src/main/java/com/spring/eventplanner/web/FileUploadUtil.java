package com.spring.eventplanner.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Iterator;
import java.util.logging.Logger;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            deleteAllFilesInDirectory(uploadPath);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    private static void deleteAllFilesInDirectory(Path filePath) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filePath)) {
            directoryStream.forEach(path ->{
                path.toFile().delete();
            });
        } catch (IOException ie) {
            System.out.println("Problem trying to delete files in " + filePath);
        }
    }

    public static Path fetchProfilePhotoByUserId(Long userId) throws Exception {
        Path imagePath = null;

        Path rootLocation = Path.of("user-photos/" + userId);
        //LOG.debug("Fetching profile image from " + rootLocation.toString());

        try {
            if (rootLocation.toFile().exists()) {
                Iterator<Path> iterator = Files.newDirectoryStream(rootLocation).iterator();

                if (iterator.hasNext()) {
                    imagePath = iterator.next();
                    System.out.println("File name is " + imagePath);
                }
            }
        } catch (IOException ie) {
            throw new Exception(ie.getMessage());
        }

        return imagePath;
    }

    private static String getRootLocationForUserProfileImageUpload(Long userId) {
        if (userId==null) throw new IllegalArgumentException("No user id!");

        StringBuilder builder = new StringBuilder();
        String userFilesBasePath="/user-photos";
        builder.append(userFilesBasePath);
        builder.append("/");
        builder.append(userId);

        String location = builder.toString();

       // createDirectoryIfItDoesntExist(location);

        return location;
    }
}
