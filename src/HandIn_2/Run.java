package HandIn_2;

import java.math.BigInteger;

public class Run {
	
	
	// Diffie-Hillman algorithm 
	public static void Exchange(BigInteger p, BigInteger g, Person one, Person two) {
		System.out.println("Take in the paramenter p = " + p + ", g = "+ g);
		
		// create the public key
		BigInteger A = one.transmitt(p, g);
		System.out.println(one.getName() + " is creating and sending the public key " + A);
		BigInteger B = two.transmitt(p, g);
		System.out.println(two.getName() + " is creating and sending the public key " + B);
		
		// takes the public key and generate the shared secret key
		one.shared(p, B);
		two.shared(p, A);
		System.out.println(one.getName() +  " has shared key " + one.getShared());
		System.out.println(two.getName() +  " has shared key " + two.getShared());
	}
	
	public static void main(String[] args) {
		// change these parameters to test different values
		String cha_p = "353";  // chage the prime in Diffi-Hillman
		String cha_g = "2";   // change the value g in Diffi-Hillman
		String cha_secA = "300";  // change the secret key for Alice
		String cha_secB = "249";  // change the secret key for Bob
		String message = "Where are you?";   // change the message to encrypt
		
		// creating the necessary objects 
		BigInteger p = new BigInteger(cha_p);
		BigInteger g = new BigInteger(cha_g);
		BigInteger secretA = new BigInteger(cha_secA);
		BigInteger secretB = new BigInteger(cha_secB);
		Person Alice = new Person("Alice", secretA, message);
		Person Bob = new Person("Bob", secretB);
		
		// the whole operation
		Exchange(p,g,Alice,Bob);
		Alice.Encrypt();
		Bob.setText(Alice.getCiphertext());
		
		System.out.print("The message is: " + Bob.getText());
	}

}
