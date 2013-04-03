package org.pnm.dun.ui;

public class DisplayRunner implements Runnable{

	private Frame frame;

	public DisplayRunner(Frame frame){
		this.frame = frame;
	}
	
	public void run(){
		for(;;){
			frame.refreshDisplay();
			sleep();
		}
	}

	private static void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
