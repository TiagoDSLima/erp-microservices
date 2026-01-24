package com.tiagolima.erp.faturamento.controller;

import com.tiagolima.erp.faturamento.bucket.BucketFile;
import com.tiagolima.erp.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        try(InputStream is = file.getInputStream()){
            MediaType mediaType = MediaType.parseMediaType(file.getContentType());
            var bucketFile = new BucketFile(file.getOriginalFilename(), is, mediaType, file.getSize());
            bucketService.upload(bucketFile);
            return ResponseEntity.ok("Arquivo enviado com sucesso!");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Erro ao enviar o arquivo: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getUrl(@RequestParam Long codigoPedido) {
        try{
            String url = bucketService.getUrl(codigoPedido);
            return ResponseEntity.ok(url);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Erro ao obter URL do arquivo: " + e.getMessage());
        }
    }
}
