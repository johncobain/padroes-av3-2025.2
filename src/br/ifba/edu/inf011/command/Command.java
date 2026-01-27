package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.documentos.Documento;

public interface Command {
    void execute();
    void undo();
    String getDescription();
    
    default Documento getDocumentoAfetado() {
        return null;
    }
}