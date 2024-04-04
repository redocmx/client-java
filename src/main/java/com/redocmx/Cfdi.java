package com.redocmx;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a CFDI (Comprobante Fiscal Digital por Internet) object for interacting with CFDI operations.
 */
public class Cfdi {
    private final Service service;

    private final File file = new File();

    private Addenda addenda;
    private Map<String, String> addendaReplaceValues;

    /**
     * Constructs a Cfdi object with the specified Service instance.
     *
     * @param service The Service instance used for interacting with the Redoc service.
     */
    public Cfdi(Service service) {
        this.service = service;
    }

    /**
     * Reads content from a file specified by the filePath parameter.
     *
     * @param filePath The path of the file to read.
     * @return The Cfdi object.
     */
    public Cfdi fromFile(String filePath) {
        file.fromFile(filePath);
        return this;
    }

    /**
     * Parses content from a string and sets it to the Cfdi object.
     *
     * @param fileContent The content of the file as a string.
     * @return The Cfdi object.
     */
    public Cfdi fromString(String fileContent) {
        file.fromString(fileContent);
        return this;
    }

    /**
     * Sets the Addenda object to be associated with this Cfdi.
     *
     * @param addenda The Addenda object to be associated.
     */
    public void setAddenda(Addenda addenda) {
        this.addenda = addenda;
    }

    /**
     * Sets the Addenda object and replacement values to be associated with this Cfdi.
     *
     * @param addenda The Addenda object to be associated.
     * @param replaceValues A map containing key-value pairs for replacement in the Addenda content.
     */
    public void setAddenda(Addenda addenda, Map<String, String> replaceValues) {
        this.addenda = addenda;
        this.addendaReplaceValues = replaceValues;
    }

    /**
     * Converts the CFDI content to PDF format.
     *
     * @return A Pdf object representing the converted PDF.
     * @throws Exception If an error occurs during the conversion process.
     */
    public Pdf toPdf() throws Exception {
        FileContent file = this.file.getFile();
        String fileType = file.getType();

        Map<String,String> options = new HashMap<String, String>();

        if (this.addenda != null) {
            String addendaContent = this.addenda.getFileContent(this.addendaReplaceValues);
            options.put("addenda", addendaContent);
        }

        if(fileType.equals("buffer")) {
            byte[] fileContent = (byte[]) file.getContent();
            return this.service.cfdisConvert(fileContent, options);
        } else {
            String fileContent = (String) file.getContent();
            return this.service.cfdisConvert(fileContent, options);
        }

    }

    /**
     * Converts the CFDI content to PDF format with additional options.
     *
     * @param options A map containing additional options for the conversion process.
     * @return A Pdf object representing the converted PDF.
     * @throws Exception If an error occurs during the conversion process.
     */
    public Pdf toPdf(Map<String, String> options) throws Exception {
        FileContent file = this.file.getFile();
        String fileType = file.getType();

        if (this.addenda != null) {
            String addendaContent = this.addenda.getFileContent(this.addendaReplaceValues);
            options.put("addenda", addendaContent);
        }

        if(fileType.equals("buffer")) {
            byte[] fileContent = (byte[]) file.getContent();
            return this.service.cfdisConvert(fileContent, options);
        } else {
            String fileContent = (String) file.getContent();
            return this.service.cfdisConvert(fileContent, options);
        }
    }
}
