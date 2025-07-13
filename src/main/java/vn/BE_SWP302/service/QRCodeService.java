package vn.BE_SWP302.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeService {

    public String generateQRCodeBase64(String text) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 350, 350);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(pngData);
    }

    public String generatePaymentQRContent(Double amount, String description, String comment) {
        try {
            Map<String, Object> qrData = new HashMap<>();
            qrData.put("amount", amount);
            qrData.put("description", description);
            qrData.put("comment", comment);
            return new ObjectMapper().writeValueAsString(qrData);
        } catch (Exception e) {
            return "";
        }
    }
}