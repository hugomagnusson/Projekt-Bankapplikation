
public class BankAccount {

	private int accountNumber;
	private double amount;
	private Customer holder;
	private static int next = 1;

	public BankAccount(String holderName, long holderId) {
		holder = new Customer(holderName, holderId);
		amount = 0;
		accountNumber = next;
		next++;
		// tilldela unikt accountNumber
	}

	public BankAccount(Customer holder) {
		this.holder = holder;
		amount = 0;
		accountNumber = BankAccount.aquireUnique();
		// tilldela unikt accountNumber
	}
	
	public void deposit(double amount) {
		this.amount += amount;
	}
	
	public void withdraw(double amount) {
		this.amount -= amount;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public Customer getHolder() {
		return holder;
	}
	
	public String toString() {
		String accNbrString = String.format("%04d", accountNumber);
		String idString = String.format("%010d", holder.getIdNbr());
		String nbrString = String.format("%04d", holder.getCustomerNbr());
//		String nameFormatteded = String.format("%10s", holder.getName());
		String formattedAmount = String.format("%.2f", amount);
		
		return String.format("%-15s%-42.40s%-12s%-12s%10s", accNbrString, holder.getName(), idString, nbrString, formattedAmount);
		
		//return "Konto " + accNbrString + "\t(" + nameFormatteded + "\tid " + idString + "\t customer " + nbrString + "):\t" + amount + " kr\t";
		//Fader Abraham Fader Abraham Fyra Söner Hade Abraham Och De Åt Och Drack Och De Drack Och åt
	}
	
	private static int aquireUnique() {
		next++;
		return next - 1;
	}

}
