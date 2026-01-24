package br.ifba.edu.inf011.model;

import java.time.LocalDateTime;

import br.ifba.edu.inf011.model.operador.Operador;

public record Assinatura(Operador usuario, LocalDateTime dataAssinatura) {
}
