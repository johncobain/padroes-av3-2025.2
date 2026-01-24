package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.GestorDocumento;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;

public class AssinarCommand implements Command {
    private GerenciadorDocumentoModel model;
    private Documento documentoOriginal;
    private Documento documentoDecorado;
    private Operador operador;
    private GestorDocumento gestor;
    
    public AssinarCommand(GerenciadorDocumentoModel model, Documento original, Operador operador) {
        this.model = model;
        this.documentoOriginal = original;
        this.operador = operador;
        this.gestor = new GestorDocumento();
    }
    
    @Override
    public void execute() {
        if (this.documentoDecorado == null) {
            this.documentoDecorado = gestor.assinar(documentoOriginal, operador);
        }
        model.atualizarRepositorio(documentoOriginal, documentoDecorado);

        model.setDocumentoAtual(documentoDecorado);
    }
    
    @Override
    public void undo() {
        model.atualizarRepositorio(documentoDecorado, documentoOriginal);
        model.setDocumentoAtual(documentoOriginal);
    }
    
    @Override
    public String getDescription() {
        String numero = documentoOriginal.getNumero() != null ? 
                       documentoOriginal.getNumero() : "SEM-NUMERO";
        return "Assinar documento " + numero + " por " + operador.getNome();
    }
}