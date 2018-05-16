import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BankApplikation {

	private static Bank bank;
	private static Scanner scan;
	private static boolean running;

	public static void main(String[] args) {
		bank = new Bank();
		scan = new Scanner(System.in);
		runApplication();
	}

	private static void runApplication() {

		bank.addAccount("Hugo Magnusson", 961120);
		bank.addAccount("Lars Magnusson", 660110);
		bank.addAccount("Katarina Öberg Magnusson", 650525);
		bank.addAccount("Gustav Magnusson", 218);
		bank.getAllAccounts().get(0).deposit(1000000);
		//
		// String acc = bank.getAllAccounts().get(1).toString();
		// System.out.println(bank.getAllAccounts().toString());

		running = true;
		printMenu();
		menuChoice();
		while (running) {
			System.out.println("Tryck på ENTER för att fortsätta");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}

			printMenu();
			menuChoice();

		}
	}

	private static void printMenu() {
		System.out.print(
				"1. Hitta konto utifrån innehavare \n2. Sök kontoinnehavare utifrån (del av) namn \n3. Sätt in \n"
						+ "4. Ta ut \n5. Överföring \n6. Skapa konto \n7. Ta bort konto \n8. Skriv ut konton \n9. Avsluta \n");
	}

	private static void menuChoice() {
		int choice = scan.nextInt();
		scan.nextLine();

		switch (choice) {
		case 1:
			findForHolder();
			break;
		case 2:
			findPartName();
			break;
		case 3:
			deposit();
			break;
		case 4:
			withdraw();
			break;
		case 5:
			transfer();
			break;
		case 6:
			createAccount();
			break;
		case 7:
			delete();
			break;
		case 8:
			printAccounts();
			break;
		case 9:
			terminate();
			break;
		}

	}
	// 1
	private static void findForHolder() {
		System.out.println("ID-nummer: ");
		ArrayList<BankAccount> foundHolder = bank.findAccountsForHolder(scan.nextInt());
		for (int i = 0; i < foundHolder.size(); i++) {
			System.out.println(foundHolder.get(i).toString());
		}
	}
	// 2
	private static void findPartName() {
		System.out.print("Namn eller del av namn: ");
		ArrayList<Customer> customers = bank.findByPartofName(scan.nextLine());
		for (int i = 0; i < customers.size(); i++) {
			System.out.println(customers.get(i).toString());
		}
	}
	// 3
	private static void deposit() {
		System.out.print("Kontonummer: ");
		int accountNbr = scan.nextInt();
		scan.nextLine();
		System.out.print("Belopp:  ");
		int amount = scan.nextInt();
		BankAccount account = bank.findByNumber(accountNbr);
		if (account != null) {
			account.deposit(amount);
			System.out.println(account.toString());
		} else {
			System.out.println("Insättning misslyckades. Inget konto existerar med det angivna kontonumret");
		}
	}
	// 4
	private static void withdraw() {
		System.out.print("Kontonummer: ");
		int accountNbr = scan.nextInt();
		scan.nextLine();
		System.out.print("Belopp: ");
		int amount = scan.nextInt();
		BankAccount account = bank.findByNumber(accountNbr);

		if (account != null) {
			if (amount <= account.getAmount()) {
				account.withdraw(amount);
				System.out.println(account.toString());
			} else {
				System.out.println("Uttag misslyckades, endast " + account.getAmount() + " på kontot");
			}
		} else {
			System.out.println("Inget konto existerar med det angivna kontonumret");
		}
	}
	// 5
	private static void transfer() {
		System.out.println("Från konto med kontonummer: ");
		BankAccount fromAcc = bank.findByNumber(scan.nextInt());
		System.out.println("Till konto med kontonummer: ");
		BankAccount toAcc = bank.findByNumber(scan.nextInt());
		System.out.println("Belopp: ");
		int amount = scan.nextInt();
		if (fromAcc.getAmount() >= amount) {
			fromAcc.withdraw(amount);
			toAcc.deposit(amount);
		} else {
			System.out.println("Överföring misslyckades, otillräckliga tillgångar");
		}
		System.out.println(fromAcc.toString());
		System.out.println(toAcc.toString());
	}
	// 6
	private static void createAccount() {
		System.out.print("Namn: ");
		String name = scan.nextLine();
		System.out.print("ID-nummer: ");
		int id = scan.nextInt();
		System.out.print("Kontonummer: ");
		System.out.println(bank.addAccount(name, id));
	}
	// 7
	private static void delete() {
		System.out.print("Kontonummer: ");
		int accountNbr = scan.nextInt();
		if (bank.removeAccount(accountNbr)) {
			System.out.println("Konto borttaget");
		} else {
			System.out.println("Kontot kunde inte tas bort, fel kontonummer");
		}
	}
	// 8
	private static void printAccounts() {
		ArrayList<BankAccount> accounts = bank.getAllAccounts();
		printAccountTable(accounts);
//		for (int i = 0; i < accounts.size(); i++) {
//			System.out.println(accounts.get(i).toString());
//		}
		
	}
	
	// lägg till exception för konton som inte finns


	private static void terminate() {
		running = false;
		System.out.println("Avslutar...");
	}
	
	private static void printAccountTable(ArrayList<BankAccount> list) {
		System.out.println("\n----------------------------------------------------------------");
		System.out.println("Sökresultat");
		System.out.println("----------------------------------------------------------------");
		System.out.println(String.format("%-15s%-40s%-15s%-15s%-10s", "Kontonummer", "Namn", "ID-nummer", "Kundnummer", "Saldo\n"));
		// accountNumber, holder.getName(), holder.getIdNbr(), holder.getCustomerNbr(), amount);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
		
		System.out.println("");
		
	}

}
