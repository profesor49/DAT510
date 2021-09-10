package HandIn_3;

import java.math.BigInteger;

public class SHA {
	private String data;
	BigInteger mod = new BigInteger("4294967296");  // this is the value 2^32
	// these value are constant
	BigInteger h0 = new BigInteger("6a09e667",16);
	BigInteger h1 = new BigInteger("bb67ae85",16);
	BigInteger h2 = new BigInteger("3c6ef372",16);
	BigInteger h3 = new BigInteger("a54ff53a",16);
	BigInteger h4 = new BigInteger("510e527f",16);
	BigInteger h5 = new BigInteger("9b05688c",16);
	BigInteger h6 = new BigInteger("1f83d9ab",16);
	BigInteger h7 = new BigInteger("5be0cd19",16);

	// these values are list of constant
	int[] k = {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
			   0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
			   0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
			   0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
			   0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
			   0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
			   0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
			   0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};
	
	public SHA(String text) {
		data = Hash(text); // run the sha algortihm by taking the user input
	}
	
	// this is where the whole algorthm is run to create a hash
	private String Hash(String text) {
		String init = prepare(text); // preperation
		String[][] words = createword(init); // word creation and divident of list
		
		// defining the variable that are gonna be used outside the loops
		BigInteger A;
		BigInteger B;
		BigInteger C;
		BigInteger D;
		BigInteger E;
		BigInteger F;
		BigInteger G;
		BigInteger H;
		BigInteger S0;
		BigInteger S1;
		BigInteger ch;
		BigInteger maj;
		BigInteger temp1;
		BigInteger temp2;
		BigInteger word;
		BigInteger K;
		String a;
		String b;
		String c;
		String e;
		String f;
		String g;
		
		// This is where the changing of the value h0-h7 happen
		// this loops throug the chunks
		for(int i = 0; i < init.length() / 512; i++) {
			A = h0;
			B = h1;
			C = h2;
			D = h3;
			E = h4;
			F = h5;
			G = h6;
			H = h7;
			a = addZero(A.toString(2),32);
			b = addZero(B.toString(2),32);
			c = addZero(C.toString(2),32);
			e = addZero(E.toString(2),32);
			f = addZero(F.toString(2),32);
			g = addZero(G.toString(2),32);
			
			// this loops throught the word inside the chunks
			for(int j = 0; j < 64; j++) {
				// these are formulas, which can be found on wikipedia SHA-2, in the pseudocode
				S1 = new BigInteger(XOR(XOR(rightShiftN(e,6),rightShiftN(e,11)),rightShiftN(e,25)),2);
				ch = new BigInteger(XOR(AND(e,f),AND(NOT(e),g)),2);
				K = new BigInteger(Integer.toString(k[j]));
				word = new BigInteger(words[i][j],2);
				temp1 = H.add(S1).add(ch).add(K).add(word);
				S0 = new BigInteger(XOR(XOR(rightShiftN(a,2),rightShiftN(a,13)),rightShiftN(a,22)),2);
				maj = new BigInteger(XOR(XOR(AND(a,b),AND(a,c)),AND(b,c)),2);
				temp2 = S0.add(maj);
				H = G;
				G = F;
				F = E;
				E = D.add(temp1).mod(mod);
				D = C;
				C = B;
				B = A;
				A = temp1.add(temp2).mod(mod);
				a = addZero(A.toString(2),32);
				b = addZero(B.toString(2),32);
				c = addZero(C.toString(2),32);
				e = addZero(E.toString(2),32);
				f = addZero(F.toString(2),32);
				g = addZero(G.toString(2),32);
			}
			// summing the constant with the miksed word generated from the second loop
			// Does this until all the chunks have been looped throug
			h0 = h0.add(A).mod(mod);
			h1 = h1.add(B).mod(mod);
			h2 = h2.add(C).mod(mod);
			h3 = h3.add(D).mod(mod);
			h4 = h4.add(E).mod(mod);
			h5 = h5.add(F).mod(mod);
			h6 = h6.add(G).mod(mod);
			h7 = h7.add(H).mod(mod);
		}
		
		return h0.toString(16) + h1.toString(16) + h2.toString(16) + h3.toString(16) + h4.toString(16) + h5.toString(16) + h6.toString(16) + h7.toString(16);
	}
	
	// transform, adding and padding the text to become x mod 512 = 0
	private String prepare(String text) {
		String pre = translateToBinaryString(text);
		String len = addZero(Integer.toUnsignedString(pre.length(), 2),64);
		pre += "1";
		return Padding(pre) + len;
	}
	
	// translate the text into BinaryString
	private String translateToBinaryString(String text) {
		String sum = "";
		for(int i = 0; i < text.length(); i++) {
			int as = (int) text.charAt(i);
			sum += addZero(Integer.toUnsignedString(as, 2),8);
		}
		return sum;
	}
	
	// adding zeros infront of the BinaryString, if it is to short
	private String addZero(String text, int len) {
		String temp = "";
		int real = text.length();
		
		while(real < len) {
			temp += "0";
			real += 1;
		}
		return temp += text;
	}
	
	// this is the padding algortthim
	private String Padding(String text) {
		String temp = text;
		while(temp.length() % 512 != 448) {
			temp += "0";
		}
		return temp;
	}
	
	// this is where it is divided by chunk, word and creating the words
	private String[][] createword(String text) {
		String[][] li = new String[text.length() / 512][64];
		BigInteger S0;
		BigInteger S1;
		BigInteger word1;
		BigInteger word2;
		BigInteger sum;
		BigInteger temp;
		int pos1;
		int pos2;
		int pos3;
		int pos4;
		// this create the chunks
		for(int i = 0; i < (text.length() / 512); i++) {
			// this divide the chunks into 16 word, and then add 48 new word
			for(int j = 0; j < 64; j++) {
				if(j < 16) {
					temp = new BigInteger(text.substring(j*32, (j+1)*32),2);
					li[i][j] = addZero(temp.toString(2),32);
				} else {
					// position to collect the word in the matrix
					pos1 = j - 15;
					pos2 = j - 2;
					pos3 = j - 16;
					pos4 = j - 7;
					// formulas to create the word, can be found on wikipedia SHA-2
					S0 = new BigInteger(XOR(XOR(rightShiftN(li[i][pos1], 7), rightShiftN(li[i][pos1], 18)), rightShift(li[i][pos1], 3)),2).mod(mod);
					S1 = new BigInteger(XOR(XOR(rightShiftN(li[i][pos2], 17), rightShiftN(li[i][pos2], 19)), rightShift(li[i][pos2], 10)),2).mod(mod);
					word1 = new BigInteger(li[i][pos3], 2);
					word2 = new BigInteger(li[i][pos4] ,2);
					sum = S0.add(S1).add(word1).add(word2).mod(mod);
					li[i][j] = addZero(sum.toString(2),32);
				}
			}
		}
		return li;
	}
	
	// right shift rotation
	private String rightShiftN(String data, int N) {
		if(N > data.length()) {
			N %= data.length();
		}
		String part1 = data.substring(0, data.length()-N);
		String part2 = data.substring(data.length()-N);
		return part2+part1;

	}
	
	// XOR operation
	private String XOR(String word1, String word2) {
		String temp = "";
		for(int i = 0; i < word2.length();i++) {
			if(word1.substring(i, (i+1)).equals(word2.substring(i, (i+1)))) {
				temp += "0";
			} else {
				temp += "1";
			}
		}
		return temp;
	}
	
	// AND operation
	private String AND(String word1, String word2) {
		String temp = "";
		for(int i = 0; i < word2.length();i++) {
			if(word1.substring(i, (i+1)).equals("1") && word2.substring(i, (i+1)).equals("1")) {
				temp += "1";
			} else {
				temp += "0";
			}
		}
		return temp;
	}
	
	// NOT operation
	private String NOT(String word) {
		String temp = "";
		for(int i = 0; i < word.length();i++) {
			if(word.substring(i, i+1).equals("1")) {
				temp += "0";
			} else {
				temp += "1";
			}
		}
		return temp;
	}
	
	// right shift operation
	private String rightShift(String data, int N) {
		String temp = data.substring(0, (data.length()-N));
		return addZero(temp,32);
	}

	// accessing the hashed value
	public String getData() {
		return data;
	}
	
	
}
