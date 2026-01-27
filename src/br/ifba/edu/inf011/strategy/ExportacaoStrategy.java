package br.ifba.edu.inf011.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;

import java.time.LocalDateTime;

public class ExportacaoStrategy implements AutenticadorStrategy{
  @Override
  public String gerarNumero(Documento documento){
    if (documento.getPrivacidade() == Privacidade.SIGILOSO) {
      return "SECURE-" + documento.hashCode() + LocalDateTime.now().hashCode();
    } else {
      return "PUB-" + documento.hashCode() + LocalDateTime.now().hashCode();
    }
  }
}