package handIn_1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DES {
	
	/*
	* 0 --> 6
	* 1 --> 2
	* 2 --> 0
	* 3 --> 4
	* 4 --> 1
	* 5 --> 9
	* 6 --> 3
	* 7 --> 8
	* 8 --> 7
	* 9 --> 5
	* 
	* 
	*  1 2 3 4 5 6 7 8
	*  3 4 5 6 7 8 1 2
	*  1111101010; simplifieddesisnotsecureenoughtoprovideyousufficientsecurity
	*/
	
	// Transform Binaryvalues written as String into decimals
	public static int BinaryStringToDecimal(String value) {
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
	
	// Transform Decimalvalues into binary written as String
	public static String DecimalToBinaryString(int value, int length) {
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
	
	//Algorithm used to brute-force the SDES
	public static String SimpleDESCracking(String crypto) {
		String[] temp = new String[crypto.length()/8];
		String raw = "";
		String text = "";
		int dec;
		SDES test = new SDES();
		int j = 8;
		for(int i = 0; i < temp.length;i++) {
			temp[i] = crypto.substring(i*8, j);
			j+=8;
		}
		
		for(int i = 0; i < 1024; i++) {
			raw = DecimalToBinaryString(i,10);
			test.setRaw(raw);
			for(int k = 0; k < temp.length; k++) {
				test.setCryptotext(temp[k]);
				test.decryption();
				dec = BinaryStringToDecimal(test.getPlaintext());
				
				if(valid(97, 122, dec)) {
					char characther = (char) dec;
					text += characther;
				} else {
					text = "";
					break;
				}
			}
			if(text.isEmpty() == false) break;
		}
		text = raw +"; " + text;
		return text;
	}
	
	// a filter used to find readable text
	public static boolean valid(int a, int b, int check) {
		if(check >= a && check <= b) {
			return true;
		}
		return false;
	}
	
	// algorithm used to brute-force TripleSDES by having some knoweledge of the plaintext
	public static String TripleDESCracking(String crypto) {
		String[] temp = new String[crypto.length()/8];
		String raw_1 = "";
		String raw_2 = "";
		String text = "";
		int dec;
		TripleSDES test = new TripleSDES();
		int j = 8;
		for(int i = 0; i < temp.length;i++) {
			temp[i] = crypto.substring(i*8, j);
			j+=8;
		}
		
		for(int i = 0; i < 1024;i++) {
			raw_1 = DecimalToBinaryString(i,10);
			test.setRaw_1(raw_1);
			text += raw_1;
			for(int x = 0; x < 1024; x++) {
				raw_2 = DecimalToBinaryString(x,10);
				test.setRaw_2(raw_2);
				for(int y = 0; y < temp.length; y++) {
					test.setCryptotext(temp[y]);
					test.decryption();
					dec = BinaryStringToDecimal(test.getPlaintext());
					if(valid(97, 122, dec)) {
						char characther = (char) dec;
						text += characther;
					} else {
						text = "";
						break;
					}
				}
				if(text.isEmpty() == false) break;
			}
			if(text.isEmpty() == false) break;
		}
		text = raw_1 + " and " + raw_2 + "; " + text;
		return text;
	}
	
	// write a string into a file
	public static void WriteToFile(String text,File file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
		if(file.length() == 0) {
			writer.append(text);
		}
		writer.close();
	}
	
	
	public static void main(String[] args) throws IOException {
		
		// Creating SDES object
		SDES oneSP = new SDES("0000000000","00000000",0);
		SDES twoSP = new SDES("0000011111","11111111",0);
		SDES threeSP = new SDES("0010011111","11111100",0);
		SDES fourSP = new SDES("0010011111 ","10100101",0);
		SDES oneSC = new SDES("1111111111","00001111",1);
		SDES twoSC = new SDES("0000011111","01000011",1);
		SDES threeSC = new SDES("1000101110","00011100",1);
		SDES fourSC = new SDES("1000101110","11000010",1);
		
		// encrypting and decrypting SDES object
		oneSP.encryption();
		twoSP.encryption();
		threeSP.encryption();
		fourSP.encryption();
		oneSC.decryption();
		twoSC.decryption();
		threeSC.decryption();
		fourSC.decryption();
		
		// write the ciphertext and plaintext to the commandline
		System.out.println("The use of Simple DES");
		System.out.println(oneSP.getCryptotext());
		System.out.println(twoSP.getCryptotext());
		System.out.println(threeSP.getCryptotext());
		System.out.println(fourSP.getCryptotext());
		System.out.println(oneSC.getPlaintext());
		System.out.println(twoSC.getPlaintext());
		System.out.println(threeSC.getPlaintext());
		System.out.println(fourSC.getPlaintext());
		
		// Creating TripleSDES object
		TripleSDES oneTP = new TripleSDES("1000101110","0110101110","11010111",0);
		TripleSDES twoTP = new TripleSDES("1000101110","0110101110","10101010",0);
		TripleSDES threeTP = new TripleSDES("1111111111","1111111111","00000000",0);
		TripleSDES fourTP = new TripleSDES("0000000000","0000000000","01010010",0);
		TripleSDES oneTC = new TripleSDES("1000101110","0110101110","11100110",1);
		TripleSDES twoTC = new TripleSDES("1011101111","0110101110","01010000",1);
		TripleSDES threeTC = new TripleSDES("1111111111","1111111111","00000100",1);
		TripleSDES fourTC = new TripleSDES("0000000000","0000000000","11110000",1);
		
		// encrypting and decrypting TripleSDES
		oneTP.encryption();
		twoTP.encryption();
		threeTP.encryption();
		fourTP.encryption();
		oneTC.decryption();
		twoTC.decryption();
		threeTC.decryption();
		fourTC.decryption();
		
		System.out.println();
		
		// Printing out the TripleSDES ciphertext and plaintext
		System.out.println("The use of Triple SDES");
		System.out.println(oneTP.getCryptotext());
		System.out.println(twoTP.getCryptotext());
		System.out.println(threeTP.getCryptotext());
		System.out.println(fourTP.getCryptotext());
		System.out.println(oneTC.getPlaintext());
		System.out.println(twoTC.getPlaintext());
		System.out.println(threeTC.getPlaintext());
		System.out.println(fourTC.getPlaintext());
		
		
		// Fetching files
		File simple = new File("C:\\Users\\joelj\\eclipse-workspace\\DAT510\\src\\handIn_1/SimpleDESData.txt");
		File triple = new File("C:\\Users\\joelj\\eclipse-workspace\\DAT510\\src\\handIn_1/TripleDESData.txt");
		
		// declaring the ciphertext
		String cryptoS = "010001110000000101000000110011011100101100000001011101000000000101101110010101110101011101101110010001110000000101000111101110100100111110001000010001110110111001001100101011111001011101101110011011101011101001001111101011110000100101001010100010000100111111001101100101110100111100110010000000010101011101101110100100000100111110101111010001111010111101110100011101000000000101001100000000010110111010111010100010000100011101101110010011001010111110010111000000011000100010010000";
		String cryptoT = "000000011010011100110010110001100110010010100111110101111010011110011100011101000111010010011100000000011010011100000001100110011010000111011010000000011001110011101111011111100010010010011100100111001001100110100001011111101010000010110011110110101010000111000110001001001010000100100011101001110111010010011100010000011010000101111110000000010111111011010111110101111010011111101111101001111001110010011001110110100000000110011100111011110111111000100100101001111101101001000001";
		
		// finding the keys fro SDES and TripleSDES
		String textS = SimpleDESCracking(cryptoS);
		String textT = TripleDESCracking(cryptoT);
	
		// Write to file if the file is empty
		WriteToFile(textS, simple);
		WriteToFile(textT, triple);
		
		// printing out the key for SDES and TripleSDES
		System.out.println("SDES: " + textS);
		System.out.println("TripleSDES: " + textT); // this takes some times, just wait 10-20sec

		

	}

}
