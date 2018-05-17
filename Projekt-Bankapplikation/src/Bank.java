import java.util.ArrayList;
import java.util.Random;

import javax.xml.ws.Holder;

public class Bank {

	private ArrayList<BankAccount> accounts;
	private int n;
	private Random rand;

	public Bank() {
		accounts = new ArrayList<BankAccount>();
		rand = new Random();
		n = 0;
	}

	public int addAccount(String holderName, long idNbr) {
		// tilldela unikt accountNumber
//		boolean found = false;
//		while (!found) {
//			int accountNumber = rand.nextInt(1000000);
//			int i = 0;
//			while (i < accounts.size() && accountNumber != accounts.get(i).getAccountNumber()) {
//				i++;
//			}
//			if (i >= accounts.size()) {
//				found = true;
//			}
//		}
		
		int r = 0;
		while (r < accounts.size() && !(accounts.get(r).getHolder().getName().equals(holderName) && accounts.get(r).getHolder().getIdNbr() == idNbr)) {
			r++;
		}
		if (r >= accounts.size()) {
			BankAccount created = new BankAccount(holderName, idNbr);
			accounts.add(created);
			return created.getAccountNumber();
		}
		else {
			BankAccount created = new BankAccount(accounts.get(r).getHolder());
			accounts.add(created);
			return created.getAccountNumber();
		}

		
	}

	/**
	 * Returnerar den kontoinnehavaren som har det givna id-numret, eller null om
	 * ingen sådan finns.
	 */
	public Customer findHolder(long idNbr) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHolder().getIdNbr() == idNbr) {
				return accounts.get(i).getHolder();
			}
		}
		return null;
	}

	/**
	 * Tar bort konto med nummer ’number’ från banken. Returnerar true om kontot
	 * fanns (och kunde tas bort), annars false.
	 */
	public boolean removeAccount(int number) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountNumber() == number) {
				accounts.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returnerar en lista innehållande samtliga bankkonton i banken. Listan är
	 * sorterad på kontoinnehavarnas namn.
	 */
	public ArrayList<BankAccount> getAllAccounts() {
		
		ArrayList<BankAccount> sorted = new ArrayList<BankAccount>();
		for (BankAccount a : accounts) {
			int pos = 0;
			while (pos < sorted.size() && sorted.get(pos).getHolder().getName().compareToIgnoreCase(a.getHolder().getName()) < 0) {
				pos++;
			}
			sorted.add(pos, a);
			
		}
		
		return sorted;

	}

	/**
	 * Söker upp och returnerar bankkontot med kontonummer ’accountNumber’.
	 * Returnerar null om inget sådant konto finns.
	 */
	public BankAccount findByNumber(int accountNumber) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountNumber() == accountNumber) {
				return accounts.get(i); 
			}
		}
		return null;

	}

	/**
	 * Söker upp alla bankkonton som innehas av kunden med id-nummer ’idNr’. Kontona
	 * returneras i en lista. Kunderna antas ha unika id-nummer.
	 */
	public ArrayList<BankAccount> findAccountsForHolder(long idNr) {
		ArrayList<BankAccount> foundHolder = new ArrayList<BankAccount>();
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHolder().getIdNbr() == idNr) {
				foundHolder.add(accounts.get(i));
			}
		}
		return foundHolder;

	}

	/**
	 * Söker upp kunder utifrån en sökning på namn eller del av namn. Alla personer
	 * vars namn innehåller strängen ’namePart’ inkluderas i resultatet, som
	 * returneras som en lista. Samma person kan förekomma flera gånger i
	 * resultatet. Sökningen är "case insensitive", det vill säga gör ingen skillnad
	 * på stora och små bokstäver.
	 */
	public ArrayList<Customer> findByPartofName(String namePart) {
		ArrayList<Customer> foundCustomers = new ArrayList<Customer>();
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHolder().getName().toUpperCase().contains(namePart.toUpperCase())) {
				int r = 0;
				while (r < foundCustomers.size()) {
					if (foundCustomers.get(r).equals(accounts.get(i).getHolder())) {
						break;
					}
					r++;
				}
				if (r >= foundCustomers.size()) {
				foundCustomers.add(accounts.get(i).getHolder());
				}
			}
		}
		
		return foundCustomers;
	}

}
