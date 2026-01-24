package br.ifba.edu.inf011.af;

import br.ifba.edu.inf011.model.documentos.CalculoPericial;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.model.operador.Perito;

public class CalculoPericialPeritoFactory implements DocumentOperatorFactory {
	
    @Override
    public Documento getDocumento() {
        return new CalculoPericial();
    }
    
    @Override
    public Operador getOperador() {
        return new Perito();
    }
}