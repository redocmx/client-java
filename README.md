# Converting CFDI to PDF with Redocmx for Java

## Introduction

The `mx.redoc.RedocClient` library is a Java client designed to interact with the [redoc.mx](https://redoc.mx) REST API for converting CFDIs (Comprobante Fiscal Digital por Internet) into PDFs. This library simplifies the process of sending XML data to the redoc.mx service and retrieving the converted PDF, along with transaction details and metadata, making it easy to integrate into Java projects.

## Installation

To use the `RedocClient` library, you can add it to your pom.xml file:

```xml
<dependency>
    <groupId>mx.redoc</groupId>
    <artifactId>RedocClient</artifactId>
    <version>0.0.1</version>
</dependency>
```

Replace `<VERSION>` with the specific version of the `RedocClient` library you wish to install.

## Usage

Start by including `RedocClient` in your project:

```java
import com.redocmx.*;
```

Then, instantiate the `RedocClient` class with your API token:

```java
public class App
{
    public static void main( String[] args )
    {
        RedocClient redoc = new RedocClient("api_token");
    }
}
```

You can load the CFDI data from a file or directly from a string. Below is an example of loading from a file and converting it to PDF:

```java
try {
    Cfdi cfdi = redoc.cfdi().fromFile("./cfdi.xml");
    
    Pdf pdf = cfdi.toPdf();
    saveBufferToFile(pdf.toBuffer(), "./result.pdf");
    
    System.out.println("Transaction ID: " + pdf.getTransactionId());
    System.out.println("Total pages: " + pdf.getTotalPages());
    System.out.println("Total time MS: " + pdf.getTotalTimeMs());
    System.out.println("Metadata: " + pdf.getMetadata());
    
} catch (Exception e) {
    throw new RuntimeException(e);
}
```

## Examples

For more detailed examples on how to use the library, please refer to the following resources:


- [Basic example](https://github.com/redocmx/cfdi-a-pdf-ejemplos)
- [Custom logo and colors](https://github.com/redocmx/cfdi-a-pdf-ejemplos)
- [Change language to English](https://github.com/redocmx/cfdi-a-pdf-ejemplos)
- [Add additional rich content](https://github.com/redocmx/cfdi-a-pdf-ejemplos)



## Contributing

We welcome contributions! Feel free to submit pull requests or open issues for bugs, features, or improvements.

## License

This project is licensed under the MIT License. See the LICENSE file in the repository for more information.