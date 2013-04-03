package org.pnm.dun.ui;

import javax.swing.JFrame;

import org.pnm.dun.Environment;

public class Frame extends JFrame{

	Display display;
	
	public Frame(Environment e){
		super("Hate this part");
		display = new Display(e);
		assemble();
		pack();
		setLocation(500,100);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void assemble() {
		getContentPane().add(display);
	}

	public void refreshDisplay() {
		display.repaint();
	}
	
	
}
