package br.ifba.edu.inf011.ui;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class JPanelOperacoes extends JPanel{
	
	public JPanelOperacoes() {
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBorder(BorderFactory.createTitledBorder("Ações"));
	}
	
	public void addOperacao(String operacao, ActionListener acao) {
		JButton btn = new JButton(operacao);
		btn.addActionListener(acao);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createVerticalStrut(10));
		this.add(btn);
		this.updateUI();
	}

}
