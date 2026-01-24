package br.ifba.edu.inf011.model.operador;

import br.ifba.edu.inf011.prototype.Prototipavel;

public class Advogado implements Operador, Prototipavel{

	
	private String id;
	private String nome;
	
	public Advogado() {
		this(null, null);
	}
	
	public Advogado(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Advogado(Advogado perito) {
		this.id = perito.getId();
		this.nome = perito.getNome();
	}
	
	

	public void inicializar(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getNome() {
		return this.nome;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "{id=" + id + ", nome=" + nome + "}";
	}

	@Override
	public Prototipavel clonar() {
		return new Advogado(this);
	}
	
	

}
