package br.ifba.edu.inf011.strategy;

import java.time.LocalDate;

import br.ifba.edu.inf011.model.documentos.Documento;

public class PessoalStrategy implements AutenticadorStrategy{
  @Override
  public String gerarNumero(Documento documento){
    return "PES-" + LocalDate.now().getDayOfYear() + "-" + documento.getProprietario().hashCode();
  }
}