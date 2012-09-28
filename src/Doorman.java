/**
 * This class implements the doorman's part of the
 * Barbershop thread synchronization example.
 */
public class Doorman extends Thread { //Extender Thread
	/**
	 * Creates a new doorman.
	 * @param queue		The customer queue.
	 * @param gui		A reference to the GUI interface.
	 */
	private CustomerQueue queue;
	private Gui gui;
	private boolean running;
	
	public Doorman(CustomerQueue queue, Gui gui) { 
		this.queue = queue;
		this.gui = gui;
	}

	/**
	 * Starts the doorman running as a separate thread.
	 */
	public void startThread() { //starter tråden
		running = true;
		start();
	}

	/**
	 * Stops the doorman thread.
	 */
	public void stopThread() {
		running = false;
	}


	@Override
	public void run () {
		while(running) {
			try {
				Thread.sleep(Globals.doormanSleep); //sover en viss tid, bestemmes av slider
		    } catch (InterruptedException e) {}
		   
		    queue.addCustomer(new Customer()); //legger til ny kunde i køen/array
		  }
		}
	}
