package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Documento;

public class EditarConteudoCommand implements Command {
    private Documento documento;
    private String novoConteudo;
    private String conteudoAnterior;
    
    public EditarConteudoCommand(Documento documento, String novoConteudo) {
        this.documento = documento;
        this.novoConteudo = novoConteudo;
    }
    
    @Override
    public void execute() {
        try {
            this.conteudoAnterior = this.documento.getConteudo();
        } catch (FWDocumentException e) {
            this.conteudoAnterior = "";
        }
        this.documento.setConteudo(this.novoConteudo);
    }
    
    @Override
    public void undo() {
        this.documento.setConteudo(this.conteudoAnterior);
    }
    
    @Override
    public String getDescription() {
        String numero = this.documento.getNumero() != null ? 
                       this.documento.getNumero() : "SEM-NUMERO";
        return "Editar conteúdo do documento " + numero;
    }
    
    @Override
    public Documento getDocumentoAfetado() {
        return this.documento;
    }
}