package com.redocmx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class represents a file handler for reading file content or file paths.
 */
public class File {
    private String filePath;
    private byte[] fileBuffer;
    private String fileContent;

    /**
     * Constructs a File object with initial values set to null.
     */
    public File() {
        this.filePath = null;
        this.fileBuffer = null;
        this.fileContent = null;
    }

    /**
     * Sets the file path for the File object.
     *
     * @param filePath The path of the file to read.
     */
    public void fromFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Sets the file content for the File object.
     *
     * @param fileContent The content of the file as a string.
     */
    public void fromString(String fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * Retrieves the file content as a FileContent object.
     *
     * @return A FileContent object representing the content of the file.
     * @throws IOException If an error occurs during file reading.
     */
    public FileContent getFile() throws IOException {
        if (this.fileContent != null) {
            return new FileContent(this.fileContent, "string");
        }

        if (this.fileBuffer != null) {
            return new FileContent(this.fileBuffer, "buffer");
        }

        if (this.filePath != null) {
            if (!Files.exists(Paths.get(this.filePath))) {
                throw new IOException("File does not exist: " + this.filePath);
            }
            if (!Files.isReadable(Paths.get(this.filePath))) {
                throw new IOException("File is not readable: " + this.filePath);
            }

            this.fileBuffer = Files.readAllBytes(Paths.get(this.filePath));
            return new FileContent(this.fileBuffer, "buffer");
        }

        throw new IOException("Failed to load file " + this.getClass().getSimpleName() + ", you must use fromFile or fromString.");
    }

}

