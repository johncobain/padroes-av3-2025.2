package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.GestorDocumento;
import br.ifba.edu.inf011.model.documentos.Documento;

public class TornarUrgenteCommand implements Command {
    private GerenciadorDocumentoModel model;
    private Documento documentoOriginal;
    private Documento documentoUrgente;
    private GestorDocumento gestor;
    
    public TornarUrgenteCommand(GerenciadorDocumentoModel model, Documento original) {
        this.model = model;
        this.documentoOriginal = original;
        this.gestor = new GestorDocumento();
    }
    
    @Override
    public void execute() {
        if (this.documentoUrgente == null) {
          this.documentoUrgente = gestor.tornarUrgente(documentoOriginal);
        }
        model.atualizarRepositorio(documentoOriginal, documentoUrgente);
        model.setDocumentoAtual(documentoUrgente);
    }
    
    @Override
    public void undo() {
        model.atualizarRepositorio(documentoUrgente, documentoOriginal);
        model.setDocumentoAtual(documentoOriginal);
    }
    
    @Override
    public String getDescription() {
        String numero = documentoOriginal.getNumero() != null ? 
                       documentoOriginal.getNumero() : "SEM-NUMERO";
        return "Tornar urgente documento " + numero;
    }
}