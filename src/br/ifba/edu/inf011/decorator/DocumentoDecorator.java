package br.ifba.edu.inf011.decorator;

import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.prototype.Prototipavel;

public class DocumentoDecorator implements Documento{
	
	private Documento wrappeeDocumento;
	
	
	public DocumentoDecorator(Documento wrappeeDocumento) {
		this.wrappeeDocumento = wrappeeDocumento;
	}

	@Override
	public void inicializar(Operador proprietario, Privacidade privacidade) throws FWDocumentException {
		this.wrappeeDocumento.inicializar(proprietario, privacidade);
	}

	@Override
	public String getNumero() {
		return this.wrappeeDocumento.getNumero();
	}

	@Override
	public String getConteudo() throws FWDocumentException{
		return this.wrappeeDocumento.getConteudo();
	}
	
	@Override
	public void setConteudo(String conteudo) {
		this.wrappeeDocumento.setConteudo(conteudo);
	}	

	@Override
	public Operador getProprietario() {
		return this.wrappeeDocumento.getProprietario();
	}
	
	@Override
	public Prototipavel clonar() throws FWDocumentException{
		return this.wrappeeDocumento.clonar();
	}

	@Override
	public Privacidade getPrivacidade() {
		return this.wrappeeDocumento.getPrivacidade();
	}

	@Override
	public void setNumero(String numero) {
		this.wrappeeDocumento.setNumero(numero);
		
	}	
	
}
