package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.af.CalculoPericialPeritoFactory;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.documentos.CalculoPericial;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.model.operador.Perito;
import br.ifba.edu.inf011.prototype.GenericDocumentOperatorFactory;

public class AppAvaliacaoI {
	
	public void runQ1(DocumentOperatorFactory factory) throws FWDocumentException {
		System.out.println(" - Executando a Questão I - Documento sendo criado com a AbstractFactory Fornecida");
		Operador operador = factory.getOperador();
		Documento documento = factory.getDocumento();
		operador.inicializar("jdc", "João das Couves");
		documento.inicializar(operador, Privacidade.PUBLICO);
		System.out.println(documento);
	}
	
	public void runQ2(DocumentOperatorFactory factory) throws FWDocumentException {
		System.out.println(" - Executando a Questão II - Documento sendo criado com a AbstractFactory Baseada em Prototipos Fornecida");
		Operador operador = factory.getOperador();
		Documento documento = factory.getDocumento();
		operador.inicializar("jdc", "João das Couves");
		documento.inicializar(operador, Privacidade.PUBLICO);
		System.out.println(documento);
	}	
	

	public static void main(String[] args) throws FWDocumentException {
		AppAvaliacaoI app = new AppAvaliacaoI();
		app.runQ1(new CalculoPericialPeritoFactory());
		app.runQ2(new GenericDocumentOperatorFactory(new CalculoPericial(), new Perito()));
	}
	
}
