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

    private CommandHistory commandHistory;

    public GerenciadorDocumentoModel(DocumentOperatorFactory factory) {
        this.repositorio = new ArrayList<>();
        this.factory = factory;
        this.autenticador = new Autenticador();
        this.gestor = new GestorDocumento();
        this.atual = null;

        this.commandHistory = new CommandHistory();
        
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
        
        Command cmd = new CriarDocumentoCommand(this, documento);
        this.commandHistory.execute(cmd);

        return documento;
    }

    public void salvarDocumento(Documento doc, String conteudo) throws Exception {
        if (doc != null) {
            Command cmd = new EditarConteudoCommand(doc, conteudo);
            this.commandHistory.execute(cmd);
        }
    }

    public Documento assinarDocumento(Documento doc) throws FWDocumentException {
        if (doc == null) return null;
        try {
            this.atual = doc;
            Operador operador = factory.getOperador();
            operador.inicializar("jdc", "João das Couves");
            Command cmd = new AssinarCommand(this, doc, operador);
            this.commandHistory.execute(cmd);
            return this.getDocumentoAtual();
        } catch (Exception e) {
            throw new FWDocumentException("Erro ao assinar: " + e.getMessage());
        }
    }
    
    public Documento protegerDocumento(Documento doc) throws FWDocumentException {
        if (doc == null) return null;
        try {
            this.atual = doc;
            Command cmd = new ProtegerCommand(this, doc);
            this.commandHistory.execute(cmd);
            return this.getDocumentoAtual();
        } catch (Exception e) {
            throw new FWDocumentException("Erro ao proteger: " + e.getMessage());
        }
    }
    
    public Documento tornarUrgente(Documento doc) throws FWDocumentException {
        if (doc == null) return null;
        try {
            this.atual = doc;
            Command cmd = new TornarUrgenteCommand(this, doc);
            this.commandHistory.execute(cmd);
            return this.getDocumentoAtual();
        } catch (Exception e) {
            throw new FWDocumentException("Erro ao tornar urgente: " + e.getMessage());
        }
    }
    
    public void undo() {
        this.commandHistory.undo();
    }

    public void redo() {
        this.commandHistory.redo();
    }
    
    public void consolidar() {
        this.commandHistory.consolidate();
    }
    
    public void macroAlterarEAssinar(Documento doc, String conteudo) throws Exception {
        if (doc == null) return;
        this.atual = doc;
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");
        
        MacroCommand macro = new MacroCommand("Alterar e Assinar");
        macro.addCommand(new EditarConteudoCommand(doc, conteudo));
        macro.addCommand(new AssinarCommand(this, doc, operador));
        
        this.commandHistory.execute(macro);
    }
    
    public void macroPriorizar(Documento doc) throws Exception {
        if (doc == null) return;
        this.atual = doc;
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");
        
        MacroCommand macro = new MacroCommand("Priorizar");
        macro.addCommand(new TornarUrgenteCommand(this, doc));
        macro.addCommand(new AssinarCommand(this, doc, operador));
        
        this.commandHistory.execute(macro);
    }

    public void adicionarDocumentoAoRepositorio(Documento doc) {
        if(!repositorio.contains(doc)){
            repositorio.add(doc);
        }
    }

    public void removerDocumentoDoRepositorio(Documento doc) {
        repositorio.remove(doc);
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
    public CommandHistory getCommandHistory() { return this.commandHistory; }
}
