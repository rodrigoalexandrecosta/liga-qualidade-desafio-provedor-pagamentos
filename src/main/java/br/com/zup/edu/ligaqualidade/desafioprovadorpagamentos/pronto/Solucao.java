package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Solucao {

	/**
	 * 
	 * @param infoTransacoes dados das transações
	 * @param infoAdiantamentos informacao da transacao que pode ser recebida adiantada
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
		
		return List.of(new String[][] { 
					 {"pago","200","194","04/03/2021"} 					 
					}); 
	}

}
