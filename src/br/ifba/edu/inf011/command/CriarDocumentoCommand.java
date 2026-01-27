package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class CriarDocumentoCommand implements Command {
  private GerenciadorDocumentoModel model;
  private Documento documento;
  private Documento documentoAnterior;
  private boolean foiAdicionado;

  public CriarDocumentoCommand(GerenciadorDocumentoModel model, Documento documento) {
    this.model = model;
    this.documento = documento;
    this.foiAdicionado = false;
  }

  @Override
  public void execute() {
    if (!foiAdicionado) {
      this.documentoAnterior = model.getDocumentoAtual();
      model.adicionarDocumentoAoRepositorio(documento);
      model.setDocumentoAtual(documento);
      foiAdicionado = true;
    }
  }

  @Override
  public void undo() {
    if(foiAdicionado){
      model.removerDocumentoDoRepositorio(documento);
      model.setDocumentoAtual(documentoAnterior);
      foiAdicionado = false;
    }
  }

  @Override
  public String getDescription(){
    String numero = documento.getNumero() != null ? 
                    documento.getNumero() : "SEM-NUMERO";
    return "Criar documento " + numero;
  }

  @Override
  public Documento getDocumentoAfetado() {
    return foiAdicionado ? documento : documentoAnterior;
  }
}
