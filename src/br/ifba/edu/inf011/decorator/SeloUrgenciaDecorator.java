package br.ifba.edu.inf011.decorator;

import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Documento;

public class SeloUrgenciaDecorator extends DocumentoDecorator {
	
    public SeloUrgenciaDecorator(Documento d) {
    	super(d); 
    }
    
	@Override
	public String getConteudo() throws FWDocumentException{
		return "[URGENTE]\n" + super.getConteudo();
	}    

}
