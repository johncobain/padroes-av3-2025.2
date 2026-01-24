package br.ifba.edu.inf011.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.command.*;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.strategy.AutenticadorStrategy;
import br.ifba.edu.inf011.strategy.CriminalStrategy;
import br.ifba.edu.inf011.strategy.ExportacaoStrategy;
import br.ifba.edu.inf011.strategy.PadraoStrategy;
import br.ifba.edu.inf011.strategy.PessoalStrategy;

public class GerenciadorDocumentoModel {
    private Map<Integer, AutenticadorStrategy> estrategias;
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
        
        this.estrategias = new HashMap<>();
        this.estrategias.put(0, new CriminalStrategy());
        this.estrategias.put(1, new PessoalStrategy());
        this.estrategias.put(2, new ExportacaoStrategy());
    }
    
    public Documento criarDocumento(int tipoAutenticadorIndex, Privacidade privacidade) throws FWDocumentException {
        Operador operador = factory.getOperador();
        Documento documento = factory.getDocumento();
        
        operador.inicializar("jdc", "João das Couves");
        documento.inicializar(operador, privacidade);
        
        AutenticadorStrategy estrategia = this.estrategias.get(tipoAutenticadorIndex);
        estrategia = estrategia != null ? estrategia : new PadraoStrategy();

        this.autenticador.setEstrategia(estrategia);
        this.autenticador.autenticar(tipoAutenticadorIndex, documento);
        
        this.repositorio.add(documento);
        this.atual = documento;
        return documento;
    }

    public void salvarDocumento(Documento doc, String conteudo) throws Exception {
        if (doc != null) {
            Command cmd = new EditarConteudoCommand(doc, conteudo);
            doc.getCommandHistory().execute(cmd);
        }
    }

    public void assinarDocumento(Documento doc) throws FWDocumentException {
        if (doc == null) return;
        try {
            Operador operador = factory.getOperador();
            operador.inicializar("jdc", "João das Couves");
            Command cmd = new AssinarCommand(this, doc, operador);
            doc.getCommandHistory().execute(cmd);
        } catch (Exception e) {
            throw new FWDocumentException("Erro ao assinar: " + e.getMessage());
        }
    }
    
    public void protegerDocumento(Documento doc) throws FWDocumentException {
        if (doc == null) return;
        try {
            Command cmd = new ProtegerCommand(this, doc);
            doc.getCommandHistory().execute(cmd);
        } catch (Exception e) {
            throw new FWDocumentException("Erro ao proteger: " + e.getMessage());
        }
    }
    
    public void tornarUrgente(Documento doc) throws FWDocumentException {
        if (doc == null) return;
        try {
            Command cmd = new TornarUrgenteCommand(this, doc);
            doc.getCommandHistory().execute(cmd);
        } catch (Exception e) {
            throw new FWDocumentException("Erro ao tornar urgente: " + e.getMessage());
        }
    }
    
    public void undo() {
        if (this.atual != null) {
            this.atual.getCommandHistory().undo();
        }
    }

    public void redo() {
        if (this.atual != null) {
            this.atual.getCommandHistory().redo();
        }
    }
    
    public void consolidar() {
        if (this.atual != null) {
            this.atual.getCommandHistory().consolidate();
        }
    }
    
    public void macroAlterarEAssinar(Documento doc, String conteudo) throws Exception {
        if (doc == null) return;
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");
        
        MacroCommand macro = new MacroCommand("Alterar e Assinar");
        macro.addCommand(new EditarConteudoCommand(doc, conteudo));
        macro.addCommand(new AssinarCommand(this, doc, operador));
        
        doc.getCommandHistory().execute(macro);
    }
    
    public void macroPriorizar(Documento doc) throws Exception {
        if (doc == null) return;
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");
        
        MacroCommand macro = new MacroCommand("Priorizar");
        macro.addCommand(new TornarUrgenteCommand(this, doc));
        macro.addCommand(new AssinarCommand(this, doc, operador));
        
        doc.getCommandHistory().execute(macro);
    }
    
    public void atualizarRepositorio(Documento antigo, Documento novo) {
        int index = repositorio.indexOf(antigo);
        if (index != -1) {
            repositorio.set(index, novo);
        }
    }

    public List<Documento> getRepositorio() { return repositorio; }
    public Documento getDocumentoAtual() { return this.atual; }
    public void setDocumentoAtual(Documento doc) { this.atual = doc; }
}
