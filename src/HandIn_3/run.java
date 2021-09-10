package HandIn_3;

import java.util.ArrayList;
import java.util.Scanner;

public class run {
		
	
	public static void main(String[] args) {
		String transaction;
		ArrayList<Block> li = new ArrayList<Block>();
		ArrayList<String> trans = new ArrayList<String>();
		Scanner in = new Scanner(System.in);
		boolean first = false;
		int amount;
		
		// creates the general block ( the first block)
		while(first == false) {
			System.out.println("How many Transactions: ");
			try {
				amount = in.nextInt();
				in.nextLine();
				for(int i = 0; i < amount; i++) {
					System.out.println("Write the Transaction: ");
					transaction = in.nextLine();
					trans.add(transaction);
				}
				li.add(new Block(trans,""));
				first = true;
			} catch(Exception e) {
				in.nextLine();
				System.out.println("Invalid value, type in a number");
			}
		}
		
		boolean end = false;
		
		// the program itself, you choose the option by typing in the corresponding number
		while(end == false) {
			String pre;
			System.out.println("Choose an option between 1-4");
			System.out.println("1. Add a new block");
			System.out.println("2. Add and display a new block");
			System.out.println("3. Display the current chain");
			System.out.println("4. Display and end");
			try {
				int alt = in.nextInt();
				switch(alt) {
				case 1:
					trans = new ArrayList<String>();
					System.out.println("How many Transactions: ");
					amount = in.nextInt();
					in.nextLine();
					for(int i = 0; i < amount; i++) {
						System.out.println("Write the Transaction: ");
						transaction = in.nextLine();
						trans.add(transaction);
					}
					pre = li.get(li.size()-1).getHash();
					li.add(new Block(trans, pre));
					System.out.println();
					break;
				case 2:
					trans = new ArrayList<String>();
					System.out.println("How many Transactions: ");
					amount = in.nextInt();
					in.nextLine();
					for(int i = 0; i < amount; i++) {
						System.out.println("Write the Transaction: ");
						transaction = in.nextLine();
						trans.add(transaction);
					}
					pre = li.get(li.size()-1).getHash();
					li.add(new Block(trans, pre));
					System.out.println("Displaying the block");
					System.out.println(li.get(li.size()-1).toString());
					System.out.println();
					break;
				case 3:
					for(int i = 0; i < li.size(); i++) {
						System.out.print(li.get(i).toString());
						System.out.println("----------------------------------------------------");
					}
					break;
				case 4:
					for(int i = 0; i < li.size(); i++) {
						System.out.print(li.get(i).toString());
						System.out.println("----------------------------------------------------");
					}
					end = true;
					break;
				default: 
					System.out.println("You typed in " + alt + ", which is an invalid argument");
					System.out.println("Try again");
				}
			} catch(Exception e) {
				in.nextLine();
				System.out.println("Invalid value, type in a number");
				System.out.println("Restarting the process from the error");
			}
		}
		in.close();
	}

}
