package br.ifba.edu.inf011.model;

import java.util.ArrayList;
import java.util.List;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;

public class GerenciadorDocumentoModel {

	private List<Documento> repositorio;
    private DocumentOperatorFactory factory;
    private Autenticador autenticador;
    private GestorDocumento gestor;
    private Documento atual;


    public GerenciadorDocumentoModel(DocumentOperatorFactory factory) {
        this.repositorio = new ArrayList<>();
        this.factory = factory;
        this.autenticador = new Autenticador();
        this.gestor = new GestorDocumento();
        this.atual = null;
    }

    public Documento criarDocumento(int tipoAutenticadorIndex, Privacidade privacidade) throws FWDocumentException {
        Operador operador = factory.getOperador();
        Documento documento = factory.getDocumento();
        
        operador.inicializar("jdc", "João das Couves");
        documento.inicializar(operador, privacidade);
        
        this.autenticador.autenticar(tipoAutenticadorIndex, documento);
        this.repositorio.add(documento);
        this.atual = documento;
        return documento;
    }

    public void salvarDocumento(Documento doc, String conteudo) throws Exception {
        if (doc != null) {
            doc.setConteudo(conteudo);
        }
        this.atual = doc;
    }

    public List<Documento> getRepositorio() {
        return repositorio;
    }
    
    public Documento assinarDocumento(Documento doc) throws FWDocumentException {
        if (doc == null) return null;
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");
        Documento assinado = gestor.assinar(doc, operador);
        this.atualizarRepositorio(doc, assinado);
        this.atual = assinado;
        return assinado;
    }    
    
    public Documento protegerDocumento(Documento doc) throws FWDocumentException {
        if (doc == null) return null;
        Documento protegido = gestor.proteger(doc);
        this.atualizarRepositorio(doc, protegido);
        this.atual = protegido;
        return protegido;        
    }    
    
    
    public Documento tornarUrgente(Documento doc) throws FWDocumentException {
        if (doc == null) return null;
        Documento urgente = gestor.tornarUrgente(doc);
        this.atualizarRepositorio(doc, urgente);
        this.atual = urgente;
        return urgente;         
    }      
    
    public void atualizarRepositorio(Documento antigo, Documento novo) {
        int index = repositorio.indexOf(antigo);
        if (index != -1) {
            repositorio.set(index, novo);
        }
    } 
    
	public Documento getDocumentoAtual() {
		return this.atual;
	}
	
	public void setDocumentoAtual(Documento doc) {
		this.atual = doc;
	}        
    
    
}
