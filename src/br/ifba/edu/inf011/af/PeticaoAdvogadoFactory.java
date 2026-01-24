package br.ifba.edu.inf011.af;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Peticao;
import br.ifba.edu.inf011.model.operador.Advogado;
import br.ifba.edu.inf011.model.operador.Operador;

public class PeticaoAdvogadoFactory implements DocumentOperatorFactory {
	
    @Override
    public Documento getDocumento() {
        return new Peticao();
    }
    
    @Override
    public Operador getOperador() {
        return new Advogado();
    }
}