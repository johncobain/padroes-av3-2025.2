package br.ifba.edu.inf011.af;

import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;

public interface DocumentOperatorFactory {
	
	   public Documento getDocumento() throws FWDocumentException;
	   public Operador getOperador() throws FWDocumentException;

}
