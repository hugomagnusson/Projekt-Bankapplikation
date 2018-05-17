
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
		
		String custNbrString = String.format("%04d", customerNbr);
		String idString = String.format("%010d", idNbr);
////		String nameFormatteded = String.format("%10s", holder.getName());
		
		//return "Kund " + customerNbr + "\t" + name + "\t ID  " + idNbr;
		return String.format("%-15s%-42s%-12s", custNbrString, name, idString);
		//return String.format("%-15s%-42.40s%-12s%-12s%10s", accNbrString, holder.getName(), idString, nbrString, formattedAmount);
	}
	
	private static int aquireUnique() {
		next++;
		return next - 1;
	}

}
