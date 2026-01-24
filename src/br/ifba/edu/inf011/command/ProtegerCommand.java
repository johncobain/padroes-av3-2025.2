package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.GestorDocumento;
import br.ifba.edu.inf011.model.documentos.Documento;

public class ProtegerCommand implements Command {
    private GerenciadorDocumentoModel model;
    private Documento documentoOriginal;
    private Documento documentoProtegido;
    private GestorDocumento gestor;
    
    public ProtegerCommand(GerenciadorDocumentoModel model, Documento original) {
        this.model = model;
        this.documentoOriginal = original;
        this.gestor = new GestorDocumento();
    }
    
    @Override
    public void execute() {
        if (this.documentoProtegido == null) {
            this.documentoProtegido = gestor.proteger(documentoOriginal);
        }
        model.atualizarRepositorio(documentoOriginal, documentoProtegido);
        model.setDocumentoAtual(documentoProtegido);
    }
    
    @Override
    public void undo() {
        model.atualizarRepositorio(documentoProtegido, documentoOriginal);
        model.setDocumentoAtual(documentoOriginal);
    }
    
    @Override
    public String getDescription() {
        String numero = documentoOriginal.getNumero() != null ? 
                       documentoOriginal.getNumero() : "SEM-NUMERO";
        return "Proteger documento " + numero;
    }
}