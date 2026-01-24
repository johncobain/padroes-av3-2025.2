package br.ifba.edu.inf011.model.documentos;

import java.time.LocalDateTime;
import java.util.Set;

import br.ifba.edu.inf011.model.Assinatura;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.prototype.Prototipavel;

public class CalculoPericial extends AbstractDocumentoBase implements Documento, Prototipavel {

	public CalculoPericial(CalculoPericial calculo) throws FWDocumentException{
		this.conteudo = this.getConteudo();
		this.proprietario = (this.getProprietario() != null) ? (Operador) this.getProprietario().clonar() :
															 null;
		this.dataCriacao = LocalDateTime.now();
		this.numero = "DOC-" + System.currentTimeMillis(); 
	}
	
	
    public CalculoPericial(String conteudo, Operador proprietario, 
			 LocalDateTime dataCriacao, Privacidade privacidade, Set<Assinatura> assinaturas) {
    	super(conteudo, proprietario, dataCriacao, privacidade, assinaturas);
    }

    public CalculoPericial(Operador proprietario, Privacidade privacidade) {
    	super(proprietario, privacidade);
    }    

    public CalculoPericial() {
    	super();
    } 
    

	public Prototipavel clonar() throws FWDocumentException{
		return new CalculoPericial(this);
	}



	

}
