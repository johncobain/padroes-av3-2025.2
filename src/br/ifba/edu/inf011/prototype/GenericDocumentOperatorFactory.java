package br.ifba.edu.inf011.prototype;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;

public class GenericDocumentOperatorFactory implements DocumentOperatorFactory{
	
	private Documento docPrototipo;
	private Operador opPrototipo;
	
	
	public GenericDocumentOperatorFactory(Documento docPrototipo, Operador opPrototipo) {
		this.docPrototipo = docPrototipo;
		this.opPrototipo = opPrototipo;
	}
	

	@Override
	public Documento getDocumento() throws FWDocumentException{
		return (Documento) this.docPrototipo.clonar();
	}

	@Override
	public Operador getOperador() throws FWDocumentException{
		return (Operador) this.opPrototipo.clonar();
	}

}
