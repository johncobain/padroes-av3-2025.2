package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.af.CalculoPericialPeritoFactory;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.model.operador.Perito;

public class AppAvaliacaoII {

	private GestorDocumento gestorDocumento;
	
	public AppAvaliacaoII() {
		this.gestorDocumento = new GestorDocumento();
	}
	

	public void runQ1(DocumentOperatorFactory factory) throws FWDocumentException {
		System.out.println(" - Executando a Questão I - Usando Decorators para Assinar o documento");
		
		Operador operador = factory.getOperador();
		Documento documento = factory.getDocumento();
		documento.inicializar(operador, Privacidade.PUBLICO);
		System.out.println(" -- Documento sem assinatura");
		documento.setConteudo("Este é o conteudo do documento...");
		System.out.println(documento.getConteudo());
		
		System.out.println(" -- Documento sendo assinado por Perito 01");
		documento = this.gestorDocumento.assinar(documento, new Perito("perito01", "Perito 01"));
		System.out.println(documento.getConteudo());

		System.out.println(" -- Documento sendo assinado por Perito 02");
		documento = this.gestorDocumento.assinar(documento, new Perito("perito02", "Perito 02"));
		System.out.println(documento.getConteudo());
		
		System.out.println(" -- Documento sendo tornado urgente");
		documento = this.gestorDocumento.tornarUrgente(documento);
		System.out.println(documento.getConteudo());		
		
	}
	
	public void runQ2(DocumentOperatorFactory factory) throws FWDocumentException {
		System.out.println(" - Executando a Questão I - Usando Proxy pra restringir o acesso ao conteúdo do documento");
		
		Operador operador = factory.getOperador();
		Documento documento = factory.getDocumento();
		documento.inicializar(operador, Privacidade.PUBLICO);

		System.out.println(" -- Documento original sem assinatura e sem proxy");
		documento.setConteudo("Este é o conteudo do documento RESTRITO...");
		System.out.println(documento.getConteudo());

		System.out.println(" -- Documento original assinado e sem proxy");
		documento = this.gestorDocumento.assinar(documento, new Perito("user01", "Perito 01"));
		System.out.println(documento.getConteudo());

		System.out.println(" -- Documento protegido");
		documento = this.gestorDocumento.proteger(documento);
		System.out.println(documento.getConteudo());
	}	
	

	public static void main(String[] args) throws FWDocumentException {
		AppAvaliacaoII app = new AppAvaliacaoII();
		app.runQ1(new CalculoPericialPeritoFactory());
		app.runQ2(new CalculoPericialPeritoFactory());
	}
	
}
