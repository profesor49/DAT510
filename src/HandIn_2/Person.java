package HandIn_2;

import java.math.BigInteger;
import java.util.ArrayList;

public class Person {
	private String name;
	private BigInteger secret;
	private BigInteger shared;
	private String text;
	private SDES crypt;
	private ArrayList<String> BinaryString = new ArrayList<String>();
	private ArrayList<SDES> ciphertext = new ArrayList<SDES>();
	
	// Used to define the sender
	public Person(String name, BigInteger secret, String text) {
		this.name = name;
		this.secret = secret;
		this.text = text;
		translateToBinaryString(text);
	}
	public ArrayList<SDES> getCiphertext() {
		return ciphertext;
	}
	
	// Used to define the reciever 
	public Person(String name, BigInteger secret) {
		this.name = name;
		this.secret = secret;
		this.text = "";
	}
	
	// Creating the Public key
	public BigInteger transmitt(BigInteger p, BigInteger g) {
		BigInteger PU;
		PU = g.modPow(secret,p);
		return PU;
	}
	
	// creating the shared secret key
	public void shared(BigInteger p, BigInteger recived) {
		this.shared = recived.modPow(secret, p);
	}
	
	// Transform the text into BinaryString
	private void translateToBinaryString(String text) {
		for(int i = 0; i < text.length(); i++) {
			int as = (int) text.charAt(i);
			BinaryString.add(DecimalToBinaryString(as,8));
		}
	}
	
	public BigInteger getShared() {
		return shared;
	}
	public String getName() {
		return name;
	}
	
	// Transform the decimal to BinarString
	private String DecimalToBinaryString(int value, int length) {
		String sum = "";
		int pow = length-1;
		for(int i = 0; i < length;i++) {
			if(value >= (int) Math.pow(2, pow)) {
				sum += "1";
				value-= (int) Math.pow(2, pow);
			} else {
				sum += "0";
			}
			pow--;
		}
		return sum;
	}
	
	// Encrypting the message using SDES
	public void Encrypt() {
		String key = BBS(); // 10 bits key generated using Blum Blum Shub
		System.out.println(name + " used BBS to randomize shared key " + shared + " and create the key " + key);
		SDES crypt;
		for(int i = 0; i < BinaryString.size(); i++) {
			crypt = new SDES(key,BinaryString.get(i),0);
			crypt.encryption();
			ciphertext.add(crypt);
		}
		System.out.println(name + " encrypt the message");
		getSecretMessage();
	}

	public SDES getCrypt() {
		return crypt;
	}
	public String getText() {
		return text;
	}

	// Recieve ad decrypt the message
	public void setText(ArrayList<SDES> cipher) {
		System.out.println(name + " recived the encrypted message");
		String key = BBS();  // 10 bit key generated using Blum Blum Shub
		System.out.println(name + " used BBS to randomize shared key " + shared + " and create the key " + key);
		SDES crypt;
		for(int i = 0; i < cipher.size(); i++) {
			crypt = new SDES(key, cipher.get(i).getCryptotext(), 1);
			crypt.decryption();
			BinaryString.add(crypt.getPlaintext());
		}
		
		String end = "";
		for(int i = 0; i < BinaryString.size(); i++) {
			int num =  BinaryStringToDecimal(BinaryString.get(i));
			char let = (char) num;
			end += let;
		}
		this.text = end;
		System.out.println(name + " decrypted the message");
	}
	
	// Transform BinaryString into decimal
	private int BinaryStringToDecimal(String value) {
		int sum = 0;
		int length = value.length()-1;
		int bin;
		for(int i = 0; i < value.length(); i++) {
			if(value.substring(i,i+1).equals("1")) {
				bin = (int) Math.pow(2, length);
				sum += bin;
			}
			length--;
		}
		return sum;
	}
	
	// The Algorithm for the Blum Blum Shub
	private String BBS() {
		BigInteger p = new BigInteger("3299");
		BigInteger q = new BigInteger("3911");
		BigInteger N = q.multiply(p);
		BigInteger mod = new BigInteger("2");
		BigInteger one = new BigInteger("1");
		String key = "";
		BigInteger initial = shared;
		for(int i = 0; i < 10; i++) {
			initial = initial.modPow(mod, N);
			if(initial.mod(mod).equals(one)) {
				key += "1";
			} else {
				key += "0";
			}
		}
		return key;
	}
	
	// write the message
	private void getSecretMessage() {
		String txt = "";
		for(SDES e : ciphertext) {
			txt += e.getCryptotext();
		}
		System.out.println("The encrypted message is:");
		System.out.println(txt);
	}
	
	
}
