package br.ifba.edu.inf011.model;

import java.time.LocalDateTime;

import br.ifba.edu.inf011.decorator.AssinaturaDecorator;
import br.ifba.edu.inf011.decorator.SeloUrgenciaDecorator;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.proxy.DocumentoConfidencial;

public class GestorDocumento {
	
	public Documento assinar(Documento documento, Operador operador) {
		Assinatura assinatura = new Assinatura(operador, LocalDateTime.now());
		Documento assinado = new AssinaturaDecorator(documento, assinatura);
		return assinado;
	}
	
	public Documento proteger(Documento documento) {
		return new DocumentoConfidencial(documento);
	}	
	
	
	public Documento tornarUrgente(Documento documento) {
		return new SeloUrgenciaDecorator(documento);
	}	
}
