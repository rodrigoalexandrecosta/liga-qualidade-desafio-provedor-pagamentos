package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.MetodoPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Recebivel {
    public final StatusPagamento status;
    public final BigDecimal totalParaReceber;
    public final LocalDate dataRecebimento;

    public Recebivel(StatusPagamento statusPagamento, BigDecimal totalParaReceber, LocalDate dataRecebimento) {
        this.status = statusPagamento;
        this.totalParaReceber = totalParaReceber;
        this.dataRecebimento = dataRecebimento;
    }

    public static Recebivel build(MetodoPagamento metodoPagamento, BigDecimal valorTransacao) {
        if (metodoPagamento.equals(MetodoPagamento.DEBITO)) {
            BigDecimal valorFinal = valorTransacao.subtract(valorTransacao.multiply(new BigDecimal("0.03")));
            return new Recebivel(StatusPagamento.PAGO, valorFinal, LocalDate.now());
        }
        BigDecimal valorFinal = valorTransacao.subtract(valorTransacao.multiply(new BigDecimal("0.05")));
        return new Recebivel(StatusPagamento.AGUARDANDO_LIBERACAO_FUNDOS, valorFinal, LocalDate.now().plusDays(30));
    }
}
