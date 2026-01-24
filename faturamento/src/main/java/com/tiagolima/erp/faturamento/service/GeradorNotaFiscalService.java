package com.tiagolima.erp.faturamento.service;

import com.tiagolima.erp.faturamento.bucket.BucketFile;
import com.tiagolima.erp.faturamento.bucket.BucketService;
import com.tiagolima.erp.faturamento.model.Pedido;
import com.tiagolima.erp.faturamento.publisher.FaturamentoPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final FaturamentoPublisher faturamentoPublisher;
    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource notaFiscal;

    @Value("classpath:reports/logo.png")
    private Resource logo;

    private final BucketService bucketService;

    public void gerarNotaFiscal(Pedido pedido) {
        log.info("Gerando nota fiscal para o pedido {}", pedido.getCodigo());

        try {
            byte[] byteArray = gerarPdf(pedido);
            String nomeArquivo = geraNomeNotaFiscal(pedido);
            var file = new BucketFile(nomeArquivo, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, byteArray.length);

            bucketService.upload(file);
            String url = bucketService.getUrl(pedido.getCodigo());
            faturamentoPublisher.publicar(pedido, url);


            log.info("Gerada a nota fiscal para o pedido {}", pedido.getCodigo());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public byte[] gerarPdf(Pedido pedido) {
        try(InputStream inputStream = notaFiscal.getInputStream()){
            Map<String, Object> params = new HashMap<>();
            params.put("NOME", pedido.getCliente().getNome());
            params.put("CPF", pedido.getCliente().getCpf());
            params.put("LOGRADOURO", pedido.getCliente().getLogradouro());
            params.put("NUMERO", pedido.getCliente().getNumero());
            params.put("BAIRRO", pedido.getCliente().getBairro());
            params.put("EMAIL", pedido.getCliente().getEmail());
            params.put("TELEFONE", pedido.getCliente().getTelefone());
            params.put("DATA_PEDIDO", pedido.getDataPedido());
            params.put("TOTAL_PEDIDO", pedido.getTotalPedido());
            params.put("LOGO", logo.getFile().getAbsolutePath());

            var dataSource = new JRBeanCollectionDataSource(pedido.getItens());
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e){
            throw new RuntimeException("Erro ao gerar pdf da nota fiscal");
        }
    }

    private String geraNomeNotaFiscal(Pedido pedido) {
        return String.format("nota-fiscal-%d.pdf", pedido.getCodigo());
    }


}
