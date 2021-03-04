package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosTransacao;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.MetodoPagamento;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.Solucao;

public class CenarioRecebiveisSemAdiantamento {

	@ParameterizedTest
	@MethodSource("geradorDeTransacoesSemAdiantamento")
	void teste(List<DadosTesteTransacao> info,List<DadosEsperadosRetorno> retornosEsperados) throws Exception {
		ArrayList<DadosTransacao> dadosTransacoes = new ArrayList<>();
		for(DadosTesteTransacao dadosTeste : info) {
			dadosTransacoes.add(new DadosTransacao(new BigDecimal(dadosTeste.valorTransacao), 
					MetodoPagamento.valueOf(dadosTeste.formaPagamento),
					"764387534", "Nome do cartao", 
					LocalDate.now().plusYears(2),
					457,1));
			
		}
		
		List<String[]> retornos = Solucao.executa(dadosTransacoes,List.of());
		Assertions.assertEquals(retornosEsperados.size(), retornos.size());
		for(int i=0;i < retornos.size(); i++) {
			String status = retornos.get(i)[0];
			String valorOriginal = new BigDecimal(retornos.get(i)[1]).setScale(0).toString();
			String valorASerRecebido = new BigDecimal(retornos.get(i)[2]).setScale(0).toString();
			String data = retornos.get(i)[3];
			
			Assertions.assertEquals(retornosEsperados.get(i).status, status);
			Assertions.assertEquals(retornosEsperados.get(i).valorOriginal, valorOriginal);
			Assertions.assertEquals(retornosEsperados.get(i).valorASerRecebido, valorASerRecebido);
			Assertions.assertEquals(retornosEsperados.get(i).dataRecebimento, data);
		} 
	}

	static Stream<Arguments> geradorDeTransacoesSemAdiantamento() {
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter padraoFormatacao = DateTimeFormatter
				.ofPattern("dd/MM/yyyy");

		return Stream.of(
				Arguments.of(
						List.of(new DadosTesteTransacao("100", "DEBITO")),
						List.of(new DadosEsperadosRetorno("pago", "100", "97",
								hoje.format(padraoFormatacao))

						)),
				Arguments.of(
						List.of(new DadosTesteTransacao("100", "CREDITO")),
						List.of(new DadosEsperadosRetorno("aguardando_pagamento", "100", "95",
								hoje.plusDays(30).format(padraoFormatacao))

						)),
				Arguments.of(
						List.of(new DadosTesteTransacao("100", "CREDITO"),
								new DadosTesteTransacao("100", "DEBITO")),
						List.of(new DadosEsperadosRetorno("aguardando_pagamento", "100", "95",hoje.plusDays(30).format(padraoFormatacao)),
								new DadosEsperadosRetorno("pago", "100", "97",hoje.format(padraoFormatacao))

						))				
				
				);

	}

	static class DadosEsperadosRetorno {
		public final String status;
		public final String valorOriginal;
		public final String valorASerRecebido;
		public final String dataRecebimento;

		public DadosEsperadosRetorno(String status, String valorOriginal,
				String valorASerRecebido, String dataRecebimento) {
			super();
			this.status = status;
			this.valorOriginal = valorOriginal;
			this.valorASerRecebido = valorASerRecebido;
			this.dataRecebimento = dataRecebimento;
		}

		@Override
		public String toString() {
			return "DadosEsperadosRetorno [status=" + status
					+ ", valorOriginal=" + valorOriginal
					+ ", valorASerRecebido=" + valorASerRecebido
					+ ", dataRecebimento=" + dataRecebimento + "]";
		}

	}

	static class DadosTesteTransacao {
		public final String valorTransacao;
		public final String formaPagamento;

		public DadosTesteTransacao(String valorTransacao,
				String formaPagamento) {
			super();
			this.valorTransacao = valorTransacao;
			this.formaPagamento = formaPagamento;
		}

		@Override
		public String toString() {
			return "DadosTesteTransacao [valorTransacao=" + valorTransacao
					+ ", formaPagamento=" + formaPagamento + "]";
		}

	}
}
