/**
 * This class implements a queue of customers as a circular buffer.
 */
public class CustomerQueue {
	/**
	 * Creates a new customer queue.
	 * @param queueLength	The maximum length of the queue.
	 * @param gui			A reference to the GUI interface.
	 */
	private int queueLength; // Lengde p� k�en. Er satt av Globals til � v�re 18
    private int currentLength = 0; //L�pende oversikt av lengde p� k�en
    private Gui gui; //holder GUI
    
    private Customer[] buffer; //Array som holder customers
    private int head = 0; //F�rste customer i k�en
    private int tail = 0; //Siste customer i k�en
    
    private int payBarber1; //Betalingsvariabel for barber #pos
    private int payBarber2; 
    private int payBarber3;
    
	public CustomerQueue(int queueLength, Gui gui) { //Constructor som tar inn fast lengde p� k� og GUI
		this.queueLength = queueLength;
		this.gui = gui;
		buffer = new Customer[queueLength];
	}
	
	public synchronized void addCustomer(Customer c) {

		while(currentLength >= queueLength) { // venter dersom k�en er full
			gui.println("Doorman venter p� ledige stoler");
			try {
				wait();
		    } catch (InterruptedException e) {}
		    gui.println("Doorman ble varslet om ledig stol");
		}
		
		buffer[tail] = c; //Legger til ny customer i bunn av k�en
		gui.fillLoungeChair(tail, c); //Oppdaterer gui med ny customer
		tail = (tail+1)%queueLength; 
		currentLength++; //Oppdaterer k�lengde
		
		if(currentLength == 1) notifyAll();
		}

	public synchronized Customer removeCustomer(int barberPos) {
		while(currentLength == 0) { //Dersom det ikke er customers i k�en
			gui.println("Barber nr " + (barberPos + 1) + " venter p� nye kunder");
		    try {
		    	wait();
		    } catch (InterruptedException e) {}
		    gui.println("Barber nr " + (barberPos + 1) +" ble varslet om en ny kunde");
		}
		
		Customer c = buffer[head]; 
		gui.emptyLoungeChair(head);
		head = (head+1)%queueLength;
		currentLength--;
		
		if (currentLength == (queueLength-1)) notifyAll();
		return c;
	}
	
	public synchronized void pay(int pos){ //hver barber oppdaterer sin egen variabel ved avsluttet servecustomer
		switch (pos) {
		case 0: {
			payBarber1++;
			break;
		}
		case 1: {
			payBarber2++;
			break;
		}
		case 2: {
			payBarber3++;
			break;
		}
		default: {
			break;
		}
		}
	}
	public synchronized int getPay(int pos){ //Returnerer variabel for hver barber
		switch (pos) {
		case 0: {
			return payBarber1;
		}
		case 1: {
			return payBarber2;
			
		}
		case 2: {
			return payBarber3;
		}
		default: {
			return -1;
		}
		}
	}
}
