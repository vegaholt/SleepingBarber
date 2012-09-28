/**
 * This class implements the barber's part of the
 * Barbershop thread synchronization example.
 */
public class Barber extends Thread{ //Extender thread
	/**
	 * Creates a new barber.
	 * @param queue		The customer queue.
	 * @param gui		The GUI.
	 * @param pos		The position of this barber's chair
	 */
	private CustomerQueue queue;	
	private Gui gui;
	private int pos;
	private boolean running;

	public Barber(CustomerQueue queue, Gui gui, int pos) { 
		this.queue = queue;
		this.gui = gui;
		this.pos = pos;
	}

	/**
	 * Starts the barber running as a separate thread.
	 */
	public void startThread() {  //starter tråden
		running = true;
		start();
	}

	/**
	 * Stops the barber thread.
	 */
	public void stopThread() {
		System.out.println("Barber nr " + (this.pos + 1) + ": betaling: " + queue.getPay(pos)); //printer total betaling for gjeldende barber
		running = false; //stopper tråd
	}

	public void run() {
		  while(running) {
			  try {
				  gui.barberIsSleeping(pos);
				  Thread.sleep(Globals.barberSleep); //sover en viss tid, bestemmes av slider
				  gui.barberIsAwake(pos);
			  } catch (InterruptedException e) {}
		
			  Customer c = queue.removeCustomer(pos); //får kunde
			  serveCustomer(c); //behandler kunde
		  }
	}
	
	public void serveCustomer(Customer c){ 
		gui.fillBarberChair(this.pos, c); //kunde settes i stol
		gui.println("Kunde nr " + c.getCustomerID() + " blir klippet");
		try {
			Thread.sleep(Globals.barberWork); //sover en viss tid, bestemmes av slider
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		queue.pay(pos); //registrerer betaling
		gui.emptyBarberChair(this.pos); //tømmer stol
	}	
}

