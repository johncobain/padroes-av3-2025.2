package br.ifba.edu.inf011.decorator;

import java.time.format.DateTimeFormatter;

import br.ifba.edu.inf011.model.Assinatura;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Documento;

public class AssinaturaDecorator extends DocumentoDecorator {

	private Assinatura assinatura;
	
	public AssinaturaDecorator(Documento wrappeeDocumento, Assinatura assinatura) {
		super(wrappeeDocumento);
		this.assinatura = assinatura;
	}

	@Override
	public void setConteudo(String conteudo){
		String regexAssinatura = "\\nAssinado por: .*? em \\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}";
		String conteudoLimpo = conteudo.replaceAll(regexAssinatura, "");

		super.setConteudo(conteudoLimpo.trim());
	}
	
	@Override
	public String getConteudo() throws FWDocumentException{
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String dataFormatada = this.assinatura.dataAssinatura().format(formatador);
		
		return super.getConteudo() +
			   "\nAssinado por: " + this.assinatura.usuario().getNome() + " em " 
								  + dataFormatada;
	}
	

}
