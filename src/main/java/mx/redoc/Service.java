package mx.redoc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * This class represents a service for converting CFDIs (Comprobante Fiscal Digital por Internet) to PDF format using the Redoc API.
 */
public class Service {
    private final String apiKey;
    private final CloseableHttpClient httpClient;
    private final String apiUrl;

    /**
     * Constructs a Service object with the specified API key.
     *
     * @param apiKey The API key used for authentication with the Redoc API.
     */
    public Service(String apiKey) {
        this.apiKey = (apiKey != null && !apiKey.isEmpty()) ? apiKey : System.getenv("REDOC_API_KEY");
        this.apiUrl = System.getenv("REDOC_API_URL") != null ? System.getenv("REDOC_API_URL") : "https://api.redoc.mx/cfdis/convert";

        this.httpClient = HttpClients.createDefault();
    }

    /**
     * Converts CFDI content provided as a string to PDF format.
     *
     * @param xmlContent The content of the CFDI as a string.
     * @param options Additional options for the conversion process.
     * @return A Pdf object representing the converted PDF.
     * @throws Exception If an error occurs during the conversion process.
     */
    public Pdf cfdisConvert(String xmlContent, Map<String, String> options) throws Exception {
        ByteArrayInputStream xmlContentStream = new ByteArrayInputStream(xmlContent.getBytes());
        return  this.convert(xmlContentStream, options);
    }

    /**
     * Converts CFDI content provided as a byte array to PDF format.
     *
     * @param xmlContent The content of the CFDI as a byte array.
     * @param options Additional options for the conversion process.
     * @return A Pdf object representing the converted PDF.
     * @throws Exception If an error occurs during the conversion process.
     */
    public Pdf cfdisConvert(byte[] xmlContent, Map<String, String> options) throws Exception {
        ByteArrayInputStream xmlContentStream = new ByteArrayInputStream(xmlContent);
        return  this.convert(xmlContentStream, options);
    }

    private Pdf convert (ByteArrayInputStream xml, Map<String, String> options) throws Exception {
        HttpPost post = new HttpPost(apiUrl);

        MultipartEntityBuilder entity = MultipartEntityBuilder.create()
                .addBinaryBody("xml", xml, ContentType.create("text/xml"), "document.xml");

        if (options.get("style_pdf") != null) {
            entity.addTextBody("style_pdf", options.get("style_pdf"));
        }

        if (options.get("addenda") != null) {
            entity.addTextBody("addenda", options.get("addenda"));
        }

        HttpEntity body = entity.build();

        post.setEntity(body);

        post.setHeader("Accept", "application/pdf");
        post.setHeader("X-Redoc-Api-Key", this.apiKey);

        try (CloseableHttpResponse response = httpClient.execute(post)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                // Assuming the response includes PDF binary and metadata in headers
                byte[] pdfContent = EntityUtils.toByteArray(response.getEntity());
                String transactionId = response.getFirstHeader("X-redoc-Transaction-Id").getValue();
                int totalPages = Integer.parseInt(response.getFirstHeader("X-redoc-pdf-Total-Pages").getValue());
                long totalTimeMs = Long.parseLong(response.getFirstHeader("x-redoc-process-total-time").getValue());

                String metadataHeader = response.getFirstHeader("X-redoc-xml-Metadata").getValue();
                byte[] decodedBytes = Base64.getDecoder().decode(metadataHeader);
                String metadataString = new String(decodedBytes, StandardCharsets.UTF_8);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode metadata = mapper.readTree(metadataString);

                return new Pdf(pdfContent, transactionId, totalPages, totalTimeMs, metadata);

            } else {
                throw new IOException("Failed to convert CFDI to PDF: " + response.getStatusLine().getStatusCode());
            }
        }
    }
}
