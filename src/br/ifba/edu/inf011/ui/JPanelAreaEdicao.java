package br.ifba.edu.inf011.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.ifba.edu.inf011.model.documentos.Documento;

public class JPanelAreaEdicao extends JPanel{
	
    private JTextArea txtEditor;
	
	public JPanelAreaEdicao() {
        super(new BorderLayout());
        this.txtEditor = new JTextArea();
        this.txtEditor.setFont(new Font("Monospaced", Font.PLAIN, 14));
        this.add(new JScrollPane(this.txtEditor), BorderLayout.CENTER);
        this.setBorder(BorderFactory.createTitledBorder("Edição do Documento"));
	}
	
	
	public void setConteudo(String conteudo) {
		if(conteudo == null) {
			this.txtEditor.setText("");
			return;
		}	
        this.txtEditor.setText(conteudo);
    	this.txtEditor.updateUI();		
	}
	
	
	public String getConteudo() {
    	return this.txtEditor.getText();
    }	
	
    public void atualizar(String conteudo) {
    	this.setConteudo(conteudo);
    	this.updateUI();
    }	
	
	

}
