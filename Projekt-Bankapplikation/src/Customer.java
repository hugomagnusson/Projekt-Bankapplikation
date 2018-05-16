
public class Customer {

	private String name;
	private long idNbr;
	private int customerNbr;
	private static int next = 1;

	public Customer(String name, long idNbr) {
		this.name = name;
		this.idNbr = idNbr;
		customerNbr = next;
		next++;
		// tilldela unikt customerNbr
	}

	public String getName() {
		return name;
	}

	public long getIdNbr() {
		return idNbr;
	}

	public int getCustomerNbr() {
		return customerNbr;
	}
	
	public String toString(){
		return "Kund " + customerNbr + "\t" + name + "\t ID  " + idNbr;
	}
	
	private static int aquireUnique() {
		next++;
		return next - 1;
	}

}
