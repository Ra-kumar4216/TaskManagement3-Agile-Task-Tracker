package com.TaskManagement3.cloud;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public abstract class StorageServicelmpl implements StorageService {
    private Path lastStoredPath;

    public String store(byte[] fileBytes, String relativeFolder) {
        try {
            Path folderPath = Paths.get(relativeFolder);
            Files.createDirectories(folderPath);
            Path filePath = folderPath.resolve(UUID.randomUUID().toString());
            Files.write(filePath, fileBytes);
            lastStoredPath = filePath;
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public byte[] read(String storagePath) {
        try {
            return Files.readAllBytes(Paths.get(storagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        if (lastStoredPath == null) {
            return;
        }

        try {
            Files.deleteIfExists(lastStoredPath);
        } catch (IOException e) {
            throw new RuntimeException("Delete failed", e);
        }
    }
}
