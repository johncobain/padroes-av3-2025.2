package br.ifba.edu.inf011.ui;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelBarraSuperior<T> extends JPanel{
	
	private JComboBox<T> tipos;
	
	public JPanelBarraSuperior(T[] tipos) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createTitledBorder("Configuração de Autenticação"));
        this.add(new JLabel("Tipo de Protocolo: "));
        this.tipos = new JComboBox<>(tipos);
        this.add(this.tipos);
	}
	
	
	public T getSelected() {
		return (T) this.tipos.getSelectedItem();
	}
	
	public int getTipoSelecionadoIndice() {
		 return this.tipos.getSelectedIndex();
	}
	

}
