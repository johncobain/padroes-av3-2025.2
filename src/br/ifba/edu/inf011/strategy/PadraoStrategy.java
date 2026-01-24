package br.ifba.edu.inf011.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;

public class PadraoStrategy implements AutenticadorStrategy{
  @Override
  public String gerarNumero(Documento documento){
    return "DOC-" + System.currentTimeMillis();
  }
}