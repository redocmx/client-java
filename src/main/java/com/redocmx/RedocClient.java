package com.redocmx;

/**
 * This class represents a client for interacting with the Redoc service.
 * It provides methods to access different functionalities of the service.
 */
public class RedocClient {
    private final String apiKey;
    private final Service service;

    /**
     * Constructs a RedocClient object with the specified API key.
     *
     * @param apiKey The API key used for authentication with the Redoc service.
     */
    public RedocClient(String apiKey) {
        this.apiKey = apiKey;
        this.service = new Service(this.apiKey);
    }

    /**
     * Retrieves an object for interacting with CFDI (Comprobante Fiscal Digital por Internet) operations.
     *
     * @return A Cfdi object for handling CFDI operations.
     */

    public Cfdi cfdi() {
        return new Cfdi(this.service);
    }

    /**
     * Retrieves an object for handling addenda operations.
     *
     * @return An Addenda object for handling addenda-related operations.
     */
    public Addenda addenda() {
        return new Addenda();
    }
}
