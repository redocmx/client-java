package mx.redoc;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This class represents a PDF document along with metadata.
 */
public class Pdf {
    private final byte[] buffer;
    private final String transactionId;
    private final int totalPages;
    private final long totalTimeMs;
    private final JsonNode metadata;

    /**
     * Constructs a Pdf object with the specified parameters.
     *
     * @param buffer The byte array containing the PDF content.
     * @param transactionId The transaction ID associated with the PDF generation.
     * @param totalPages The total number of pages in the PDF document.
     * @param totalTimeMs The total time taken for PDF generation in milliseconds.
     * @param metadata Additional metadata associated with the PDF document.
     */
    public Pdf(byte[] buffer, String transactionId, int totalPages, long totalTimeMs, JsonNode metadata) {
        this.buffer = buffer;
        this.transactionId = transactionId;
        this.totalPages = totalPages;
        this.totalTimeMs = totalTimeMs;
        this.metadata = metadata;
    }

    /**
     * Retrieves the PDF content as a byte array.
     *
     * @return The byte array containing the PDF content.
     */
    public byte[] toBuffer() {
        return buffer;
    }

    /**
     * Retrieves the transaction ID associated with the PDF generation.
     *
     * @return The transaction ID associated with the PDF generation.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Retrieves the total number of pages in the PDF document.
     *
     * @return The total number of pages in the PDF document.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Retrieves the total time taken for PDF generation in milliseconds.
     *
     * @return The total time taken for PDF generation in milliseconds.
     */
    public long getTotalTimeMs() {
        return totalTimeMs;
    }

    /**
     * Retrieves the metadata associated with the PDF document.
     *
     * @return The metadata associated with the PDF document.
     */
    public JsonNode getMetadata() {
        return metadata;
    }
}
