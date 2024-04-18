package com.app.ConStructCompany.Controller;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@RestController
public class ExportReportController {
    @GetMapping("/api/report")
    public ResponseEntity<?> generateReport() throws JRException, IOException{

            //load file jrxml từ resources
            InputStream inputStream = new ClassPathResource("reports/invoice.jrxml").getInputStream();

            // biên dịch jrxml thành jasperreport
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Create an empty data source (you can replace this with your own data source)
            JRDataSource dataSource = new JREmptyDataSource();

            // Fill the report and generate JasperPrint
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            // export the report to pdf
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("invoice", "invoice.pdf");
            return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());

    }
}
