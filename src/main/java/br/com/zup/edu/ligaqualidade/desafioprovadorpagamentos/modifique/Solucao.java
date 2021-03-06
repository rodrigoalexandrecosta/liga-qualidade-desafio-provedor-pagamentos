package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosTransacao;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.MetodoPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Solucao {

	/**
	 * 
	 * @param infoTransacoes dados das transações. A String está formatada da seguinte maneira:		
		<b>"valor,metodoPagamento,numeroCartao,nomeCartao,validade,cvv,idTransacao"</b>
		<ol>
		 <li> Valor é um decimal</li>
	 	 <li> O método de pagamento é 'DEBITO' ou 'CREDITO' </li>
	 	 <li> Validade é uma data no formato dd/MM/yyyy. </li>
	 	</ol>
	 	
	 * @param infoAdiantamentos informacao da transacao que pode ser recebida adiantada. A String está formatada da seguinte maneira:		
		<b>"idTransacao,taxa"</b>
		<ol>
	 	 <li> Taxa é um decimal </li>	 	 
	 	</ol> 
	 * 
	 * @return Uma lista de array de string com as informações na seguinte ordem:
	 * [status,valorOriginal,valorASerRecebidoDeFato,dataEsperadoRecebimento].
	 * <ol>
	 *  <li>O status pode ser 'pago' ou 'aguardando_pagamento'</li>
	 *  <li>O valor original e o a ser recebido de fato devem vir no formato decimal. Ex: 50.45</li>
	 *  <li>dataEsperadoRecebimento deve ser formatada como dd/MM/yyyy. Confira a classe {@link DateTimeFormatter}</li> 
	 * </ol> 
	 * 
	 * É esperado que o retorno respeite a ordem de recebimento
	 */
	public static List<String[]> executa(List<String> infoTransacoes, List<String> infoAdiantamentos) {

		DadosTransacao dadosTransacao = buildDadosTransacao(infoTransacoes);
		Recebivel recebivel = Recebivel.build(dadosTransacao.metodo, dadosTransacao.valor);
		RecebivelResponse response = new RecebivelResponse(recebivel, dadosTransacao.valor);

		return List.of(new String[][]{
				{response.status, response.valor, response.totalParaReceber, response.dataRecebimento}
		});
	}

	private static DadosTransacao buildDadosTransacao(List<String> info) {
		BigDecimal valor = new BigDecimal(info.get(0));
		MetodoPagamento metodoPagamento = MetodoPagamento.valueOf(info.get(1));
		String numeroCartao = info.get(2);
		String nomeCartao = info.get(3);
		LocalDate validade = LocalDate.parse(info.get(4));
		int cvv = Integer.parseInt(info.get(5));
		int id = Integer.parseInt(info.get(6));

		return new DadosTransacao(valor, metodoPagamento, numeroCartao, nomeCartao, validade, cvv, id);
	}

}
