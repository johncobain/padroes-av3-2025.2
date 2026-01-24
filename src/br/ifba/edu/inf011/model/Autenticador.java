package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.strategy.AutenticadorStrategy;
import br.ifba.edu.inf011.strategy.PadraoStrategy;

public class Autenticador {
	private AutenticadorStrategy estrategia;

	public Autenticador() {
		estrategia = new PadraoStrategy();
	}

	public void setEstrategia(AutenticadorStrategy estrategia) {
		this.estrategia = estrategia;
	}
	
	public void autenticar(Integer tipo, Documento documento) {
		String numero = this.estrategia.gerarNumero(documento);
		documento.setNumero(numero);
	}

}
