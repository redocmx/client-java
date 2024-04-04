package com.redocmx;

/**
 * This class represents the content of a file along with its type.
 */
public class FileContent {
    private final Object content;
    private final String type;

    /**
     * Constructs a FileContent object with the specified content and type.
     *
     * @param content The content of the file.
     * @param type The type of the content (e.g., "string" or "buffer").
     */
    public FileContent(Object content, String type) {
        this.content = content;
        this.type = type;
    }

    /**
     * Retrieves the content of the file.
     *
     * @return The content of the file.
     */
    public Object getContent() {
        return this.content;
    }

    /**
     * Retrieves the type of the content.
     *
     * @return The type of the content (e.g., "string" or "buffer").
     */
    public String getType() {
        return type;
    }
}