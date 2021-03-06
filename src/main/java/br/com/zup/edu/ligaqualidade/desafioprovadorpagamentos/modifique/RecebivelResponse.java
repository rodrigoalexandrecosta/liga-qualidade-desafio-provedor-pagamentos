package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class RecebivelResponse {
    public final String status;
    public final String valor;
    public final String totalParaReceber;
    public final String dataRecebimento;

    public RecebivelResponse(Recebivel recebivel, BigDecimal valorTotal) {
        this.status = recebivel.status.name();
        this.valor = String.format("%.2f", valorTotal);
        this.totalParaReceber = String.format("%.2f", recebivel.totalParaReceber);
        this.dataRecebimento = recebivel.dataRecebimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
