package HandIn_2;

public class SDES {
	private final String[][] S0 = {{"01", "00", "11", "10"}, {"11", "10", "01", "00" }, {"00" ,"10", "01", "11"}, {"11", "01", "11", "10"}};
	private final String[][] S1 = {{"00", "01", "10", "11"}, {"10", "00", "01", "11" }, {"11", "00", "01", "00"}, {"10", "01", "00", "11"}};
	private final int[] IP_order = {1, 5, 2, 0, 3, 7, 4, 6};
	private final int[] IPInv_order = {3, 0, 2, 4, 6, 1, 7, 5};
	private final int[] SW = {4, 5, 6, 7, 0, 1, 2, 3};
	private String raw;
	private String plaintext;
	private String cryptotext;
	private String key_1;
	private String key_2;
	
	/* Takes in a raw key, text, and a number, if the number is 0,
	 * the text is defined a plaintext, if it is something else
	 * it is a cryptotext. When created, the key is then generated
	 */
	public SDES(String raw, String text, int type) {
		this.raw = raw;
		if(type == 0) {
			this.plaintext = text;
		} else {
			this.cryptotext = text;
		}
		generateKeys();
	}
	
	// Just to define an empty key, this is used to crack the cryptotext is task 3
	public SDES() {

	}
	
	// define a key, and the generate the key
	public void setRaw(String raw) {
		this.raw = raw;
		generateKeys();
	}

	// define a cryptotext
	public void setCryptotext(String cryptotext) {
		this.cryptotext = cryptotext;
	}
	
	/* This method is used to do a permutation. just send in a list of position
	 * and the text to permutated. it will then read each position, pick the element
	 * from the text, and put it in a new text, which at the end contain all the same
	 * element from the text, but in a new position
	 */
	private String permutation(int[] order, String data) {
		String temp = "";
		int value;
		for(int i = 0; i < order.length;i++) {
			value = order[i];
			temp += data.substring(value, value+1);
		}
		return temp;
	}
	
	/* This method left Shift an element N times. It does it by taking the
	 * element from position N and add it to the new list as the first element.
	 * Then it loops through the string, and if the word is out of bound, it just
	 * start from the beginning
	 */
	private String leftShiftN(String data, int N) {
		String temp = "";
		if(N > data.length()) {
			N %= data.length();
		}
		int j = 0;
		for(int i = 0; i < data.length();i++){
			if((i+N) == data.length()) {
				j = 0;
				N = 0;
			}
			temp += data.substring(j+N,(j+N+1));
			j++;
			
		}
		return temp;
	}
	
	/* Similar to the leftshift, but just a rightshift instead
	 * the reason for this is because during the permutate and expand 
	 * process. it supposed to do a permutation, but all it does is just 
	 * a rightshift 2 and 3, uses less memory by using the rightshift that
	 * permutation method, since the permutation require a list, while the
	 * rightshift just require an Integer
	 */
	private String rightShiftN(String data, int N) {
		String temp = "";
		if(N > data.length()) {
			N %= data.length();
		}
		int j;
		int sum = data.length()-N;
		for(int i = 0; i < data.length(); i++) {
			j = i + sum;
			if(j == data.length()) {
				j = 0;
				sum = -N;
			}
			temp += data.substring(j, j+1);
		}
		return temp;
	}
	
	// This method is used to add two list into a String
	private String reunite(String A, String B) {
		return A+B;
	}
	// This method is used to Split a String into two list by a specified length
	private String[] split(String temp, int size) {
		String first = temp.substring(0, size);
		String second = temp.substring(size);
		String[] div = {first,second};
		return div;
	}
	
	// this method is used to transform the 10bit raw key into two 8bit keys
	public void generateKeys() {
		int[] first_order = {2,4,1,6,3,9,0,8,7,5};
		int[] second_order = {5,2,6,3,7,4,9,8};
		
		// first we permutate the key by using the first order list, and split it by 5
		String list = permutation(first_order,raw);
		String[] div = split(list,5);
		String temp;
			
		// then we leftshift both the half by 1 bit and reuinte it into a temporary String 
		String firstShift = leftShiftN(div[0],1);
		String secondShift = leftShiftN(div[1],1);
		temp = reunite(firstShift,secondShift);
		
		// Then we permutate the temporary String and it becomes the first key
		this.key_1 = permutation(second_order, temp);
		
		// Then we left shift the two half by 2, they are supposed to be by three, but
		// we have already done it ones, and the reunite them into a String
		 firstShift = leftShiftN(firstShift,2);
		 secondShift = leftShiftN(secondShift,2);
		
		 // the second key becomes the reunited key that has been
		 // permutated by the second order
		temp = reunite(firstShift,secondShift);
		this.key_2 = permutation(second_order, temp);
	}
	
	// This Does a XOR operation of each element in the key and text. 
	// if both values are different, the new value is 1, but if both
	// values are the same it becomes 0
	private String XOR(String key, String value) {
		String temp = "";
		for(int i = 0; i < value.length();i++) {
			if(key.substring(i, i+1).equals(value.substring(i, i+1))) {
				temp += "0";
			} else {
				temp += "1";
			}
		}
		return temp;
	}
	
	// This is used to translate 2 bit String into a decimal values used 
	// to fetch the values in the S-Box
	private int binarytranslator(String A, String B) {
		String check = A + B;
		switch(check) {
		case "00":
			return 0;
		case "01":
			return 1;
		case "10":
			return 2;
		default:
			return 3;
		}
	}
	
	// this takes a 4bit String and makes the first and last value the row, 
	// while the second and third is the col, then we fetch the values that 
	// are in the S-Box.
	private String SBox(String value, String[][] box) {
		int row = binarytranslator(value.substring(0, 1), value.substring(3));
		int col = binarytranslator(value.substring(1, 2), value.substring(2, 3));
		String term = box[row][col];
		return term;
	}
	
	// This is the process where it uses all the method to one time
	private String round(String Initial, String key) {
		int[] second_order = {1, 3, 2, 0};
		String second;
		String third;
		String uni;
		
		// split the text into length of 4bit
		String[] div = split(Initial,4);
		
		// This is the permutate and expand part. It rightshift the last half by 1 bit
		// then it create a copy of the rigthshifted part and rightshift it again by 2.
		// then it unite both of the rigthshifted part into a new 8 bit String called uni
		second = rightShiftN(div[1],1);
		third = rightShiftN(second,2);
		uni = reunite(second,third);
		
		// it does a XOR operation on a key and the uni, and split them both again
		uni = XOR(key,uni);
		String[] temp = split(uni,4);
		
		// this is the S-Box part. See the S-Box method to understand it
		// it then unite t2 bit value from S-Box one and two
		uni = reunite(SBox(temp[0],S0),SBox(temp[1],S1));
		
		// It then does a permutation using the second order, does a XOR operation
		// on the united S-Boxes a the first half from the beginning. Then united it
		uni = permutation(second_order,uni);
		uni = XOR(div[0],uni);
		uni = reunite(uni,div[1]);
		return uni;
			
		}
	
	/* this is the method we use to encrypt the plaintext into cryptotext
	 * It first does and initial permutation on the plaintext,
	 *  run a round using the first key and switch the position of the first 4 bit
	 *  and the second by using a permutation and ru na round using the second key.
	 *  At the end it does a permutation by using the inverse permutation 
	 *  of the initial permutation
	 */
	public void encryption() {
		String IP = permutation(IP_order,plaintext);
		String Initial = round(IP,this.key_1);
		Initial = permutation(SW,Initial);
		String last = round(Initial,this.key_2);
		cryptotext = permutation(IPInv_order,last);
	}
	
	// set the plaintext
	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}
	
	// does the same as encryption, but uses the cryptotext to decyrpt the key
	// It does the second key first, then the first
	public void decryption() {
		String IPInv = permutation(IP_order,cryptotext);
		String Initial = round(IPInv,this.key_2);
		Initial = permutation(SW, Initial);
		String last = round(Initial,this.key_1);
		plaintext = permutation(IPInv_order, last);
	}

	// get the raw 10bit key
	public String getRaw() {
		return raw;
	}
	
	// get the plaintext
	public String getPlaintext() {
		return plaintext;
	}

	// get the cryptotext
	public String getCryptotext() {
		return cryptotext;
	}
	
}
