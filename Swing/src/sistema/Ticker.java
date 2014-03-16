package sistema;

import interfaz.Principal;

public class Ticker extends Thread {
	
	private static final long WAIT = 100;
	
	private static final int INTERVAL = 50;
	
	private int elapsed;
	
	private Principal principal;
	
	public Ticker(Principal pr) {
		elapsed = 0;
		principal = pr;
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(WAIT);
				elapsed++;
				if(elapsed % INTERVAL == 0) {
//					principal.avisar();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
