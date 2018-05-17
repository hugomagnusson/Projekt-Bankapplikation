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

//		System.out.println("Välkommen till SWEDbank!\n");

//		bank.addAccount("Hugo Magnusson", 961120);
//		bank.addAccount("Lars Magnusson", 660110);
		// bank.addAccount("Katarina Öberg Magnusson", 650525);
		// bank.addAccount("Gustav Magnusson", 218L);
//		bank.getAllAccounts().get(0).deposit(1000000);
		//
		// String acc = bank.getAllAccounts().get(1).toString();
		// System.out.println(bank.getAllAccounts().toString());

		running = true;
		printMenu();
		menuChoice();
		while (running) {
			System.out.println("Tryck på ENTER för att fortsätta...");
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
		System.out.print("Val: ");
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
			printAccounts(); // Ordna konton i bokstavsordning!
			break;
		case 9:
			exit();
			break;
		}
	}

	// 1
	private static void findForHolder() {
		System.out.print("ID-nummer: ");
		ArrayList<BankAccount> foundHolder = bank.findAccountsForHolder(scan.nextLong());
		// scan.nextLine();
		// System.out.println();
		// printAccountTableHead();
		// for (int i = 0; i < foundHolder.size(); i++) {
		// System.out.println(foundHolder.get(i).toString());
		// }
		// System.out.println();
		printAccountTable(foundHolder);
	}

	// 2
	private static void findPartName() {
		System.out.print("Namn eller del av namn: ");
		ArrayList<Customer> customers = bank.findByPartofName(scan.nextLine());
		printCustomerTableHead();
		for (int i = 0; i < customers.size(); i++) {
			System.out.println(customers.get(i).toString());
		}
		System.out.println();
	}

	// 3
	private static void deposit() {
		System.out.print("Kontonummer: ");
		int accountNbr = scan.nextInt();
		// scan.nextLine();
		System.out.print("Belopp:  ");
		int amount = scan.nextInt();
		System.out.println();
		BankAccount account = bank.findByNumber(accountNbr);
		if (account != null) {
			account.deposit(amount);
			printAccountTableHead();
			System.out.println(account.toString());
		} else {
			System.out.println("Insättning misslyckades. Inget konto existerar med det angivna kontonumret");
		}
		System.out.println();
	}

	// 4
	private static void withdraw() {
		System.out.print("Kontonummer: ");
		int accountNbr = scan.nextInt();
		scan.nextLine();
		System.out.print("Belopp: ");
		int amount = scan.nextInt();
		System.out.println();
		BankAccount account = bank.findByNumber(accountNbr);

		if (account != null) {
			if (amount <= account.getAmount()) {
				account.withdraw(amount);
				printAccountTableHead();
				System.out.println(account.toString());
			} else {
				System.out.println("Uttag misslyckades, endast " + account.getAmount() + " på kontot");
			}
		} else {
			System.out.println("Inget konto existerar med det angivna kontonumret");
		}
		System.out.println();
	}

	// 5
	private static void transfer() {
		System.out.print("Från konto med kontonummer: ");
		BankAccount fromAcc = bank.findByNumber(scan.nextInt());
		if (fromAcc == null) {
			System.out.println("Inget konto med det angivna kontonumret existerar\n");
			return;
		}
		System.out.print("Till konto med kontonummer: ");
		BankAccount toAcc = bank.findByNumber(scan.nextInt());
		if (toAcc == null) {
			System.out.println("Inget konto med det angivna kontonumret existerar\n");
			return;
		}
		System.out.print("Belopp: ");
		int amount = scan.nextInt();
		System.out.println();
		if (fromAcc.getAmount() >= amount) {
			fromAcc.withdraw(amount);
			toAcc.deposit(amount);
			printAccountTableHead();
			System.out.println(fromAcc.toString());
			System.out.println(toAcc.toString());
		} else {
			System.out.println("Överföring misslyckades, endast " + fromAcc.getAmount() + " kr på kontot");
		}
		System.out.println();

	}

	// 6
	private static void createAccount() {
		System.out.print("Namn: ");
		String name = scan.nextLine().trim();
		String extract = name.replaceAll("[^a-zA-Zä-öÄ-Ö ]+", "");
		name = extract;
		String[] words = new String[10];
		words = name.split(" ");
		String result = "";
		for (String w : words) {
			// w = w.toUpperCase().replace(w.substring(1), w.substring(1).toLowerCase());
			//
			// name += w;

			result += (w.length() > 1 ? w.substring(0, 1).toUpperCase() + w.substring(1, w.length()).toLowerCase() : w) + " ";

		}
		System.out.print("ID-nummer: ");
		long id = scan.nextLong();
		scan.nextLine();
		System.out.print("Kontonummer: ");
		System.out.println(bank.addAccount(result, id) + "\n");
	}

	// 7
	private static void delete() {
		System.out.print("Kontonummer: ");
		int accountNbr = scan.nextInt();
		scan.nextLine();
		if (bank.removeAccount(accountNbr)) {
			System.out.println("Konto borttaget\n");
		} else {
			System.out.println("Kontot kunde inte tas bort, fel kontonummer\n");
		}
	}

	// 8
	private static void printAccounts() {
		ArrayList<BankAccount> accounts = bank.getAllAccounts();
		printAccountTable(accounts);
		// for (int i = 0; i < accounts.size(); i++) {
		// System.out.println(accounts.get(i).toString());
		// }

	}

	// lägg till exception för konton som inte finns

	private static void exit() {
		running = false;
		System.out.println("Avslutar...");
	}

	private static void printAccountTable(ArrayList<BankAccount> list) {
		System.out.println(
				"\n-------------------------------------------------------------------------------------------");
		System.out.println("Sökresultat");
		System.out
				.println("-------------------------------------------------------------------------------------------");
		printAccountTableHead();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}

		System.out.println("");

	}

	private static void printAccountTableHead() {
		System.out.println(
				String.format("%-15s%-42s%-12s%-12s%-15s", "Kontonr.", "Namn", "ID-nummer", "Kundnummer", "Saldo"));
		// accountNumber, holder.getName(), holder.getIdNbr(), holder.getCustomerNbr(),
		// amount);
	}

	private static void printCustomerTableHead() {
		System.out.println(
				"\n-------------------------------------------------------------------------------------------");
		System.out.println("Sökresultat");
		System.out
				.println("-------------------------------------------------------------------------------------------");
		System.out.println(String.format("%-15s%-42s%-12s", "Kundnr.", "Namn", "ID-nummer"));
	}

}
