package br.ifba.edu.inf011.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;

public class ExportacaoStrategy implements AutenticadorStrategy{
  @Override
  public String gerarNumero(Documento documento){
    if (documento.getPrivacidade() == Privacidade.SIGILOSO) { 
      String sufixo = (documento.getNumero() != null) ? String.valueOf(documento.getNumero().hashCode()) : String.valueOf(documento.hashCode());
      return "SECURE-" + sufixo;
    } else {
      return "PUB-" + documento.hashCode();
    }
  }
}