package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.af.CalculoPericialPeritoFactory;
import br.ifba.edu.inf011.model.documentos.CalculoPericial;
import br.ifba.edu.inf011.model.operador.Perito;
import br.ifba.edu.inf011.prototype.GenericDocumentOperatorFactory;

public class AppAvaliacoes {
	
	private AppAvaliacaoI avaliacaoI;
	private AppAvaliacaoII avaliacaoII;
	
	
	public AppAvaliacoes() {
		this.avaliacaoI = new AppAvaliacaoI();
		this.avaliacaoII = new AppAvaliacaoII();
	}
	
	
	public void runAvaliacaoI() throws FWDocumentException {
		this.avaliacaoI.runQ1(new CalculoPericialPeritoFactory());
		this.avaliacaoI.runQ2(new GenericDocumentOperatorFactory(new CalculoPericial(), new Perito()));
	}
	
	public void runAvaliacaoII() throws FWDocumentException {
		this.avaliacaoII.runQ1(new CalculoPericialPeritoFactory());
		this.avaliacaoII.runQ2(new GenericDocumentOperatorFactory(new CalculoPericial(), new Perito()));
	}
	
	
	public void run() throws FWDocumentException {
		this.runAvaliacaoI();
		this.runAvaliacaoII();		
	}
	
	
	public static void main(String[] args) throws FWDocumentException {
		new AppAvaliacoes().run();
	}


	
	

}
