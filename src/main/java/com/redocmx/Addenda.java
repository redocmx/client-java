package com.redocmx;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * This class represents an Addenda object that handles file operations and content manipulation.
 */
public class Addenda {
    private final File file = new File();

    /**
     * Default constructor for Addenda class.
     */
    public Addenda() {}

    /**
     * Reads content from a file specified by the filePath parameter.
     *
     * @param filePath The path of the file to read.
     * @return The Addenda object.
     */
    public Addenda fromFile(String filePath) {
        file.fromFile(filePath);
        return this;
    }

    /**
     * Parses content from a string and sets it to the Addenda object.
     *
     * @param fileContent The content of the file as a string.
     * @return The Addenda object.
     */
    public Addenda fromString(String fileContent) {
        file.fromString(fileContent);
        return this;
    }

    /**
     * Replaces values in the given content with the specified values in the map.
     *
     * @param content The content in which values need to be replaced.
     * @param replaceValues A map containing key-value pairs for replacement.
     * @return The content with replaced values.
     */
    public String replaceValues(String content, Map<String, String> replaceValues) {
        if(replaceValues == null) return content;

        for(Map.Entry<String, String> entry : replaceValues.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            content = content.replace(key, value);
        }

        return content;
    }

    /**
     * Retrieves the content of the file and replaces values specified in the map.
     *
     * @param replaceValues A map containing key-value pairs for replacement.
     * @return The content of the file with replaced values.
     * @throws Exception If an error occurs while retrieving the file content.
     */
    public String getFileContent(Map<String, String> replaceValues) throws Exception {
        FileContent file = this.file.getFile();
        String fileType = file.getType();

        String fileContent = fileType.equals("buffer") ? new String((byte[]) file.getContent(), StandardCharsets.UTF_8) : (String) file.getContent();

        return this.replaceValues(fileContent, replaceValues);
    }

}
