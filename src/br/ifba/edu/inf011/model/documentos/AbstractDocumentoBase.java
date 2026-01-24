package br.ifba.edu.inf011.model.documentos;

import java.time.LocalDateTime;
import java.util.Set;

import br.ifba.edu.inf011.model.Assinatura;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.operador.Operador;

public abstract class AbstractDocumentoBase implements Documento{

    protected String numero;
    protected String conteudo;
    protected Operador proprietario; 
    protected LocalDateTime dataCriacao;
    protected Privacidade privacidade;
    
    public AbstractDocumentoBase(String conteudo, Operador proprietario, 
			 LocalDateTime dataCriacao, Privacidade privacidade, Set<Assinatura> assinaturas) {
		this.conteudo = conteudo;
		this.proprietario = proprietario;
		this.dataCriacao = dataCriacao;
		this.privacidade = privacidade;
    }    
    
    public AbstractDocumentoBase(Operador proprietario, Privacidade privacidade) {
    	this(null, proprietario, LocalDateTime.now(), privacidade, null);
    }    
    
    public AbstractDocumentoBase() {
    	this(null, null, LocalDateTime.now(), null, null);
    }       

    @Override
	public void inicializar(Operador proprietario, Privacidade privacidade) {
		this.proprietario = proprietario;
		this.privacidade = privacidade;
		
	}

	@Override
	public String getNumero() {
		return this.numero;
	}

	@Override
	public String getConteudo() throws FWDocumentException{
		return this.conteudo;
	}

	@Override
	public Operador getProprietario() {
		return this.proprietario;
	}
	
	@Override
	public Privacidade getPrivacidade() {
		return this.privacidade;
	}	
	
	@Override
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}	
	
	@Override
	public void setNumero(String numero) {
		this.numero = numero;
	}
    
    // Exemplo simples de representação do documento
    @Override
    public String toString() {
        return  this.getClass().getSimpleName() + "{" +
                "numero='" + numero + '\'' +
                ", proprietario='" + proprietario + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
