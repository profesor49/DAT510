package handIn_1;

import java.util.ArrayList;


/*
 * The Key is "BDLAEKCY"
 * Find the length
 */

public class poly {
	//C:\Users\joelj\eclipse-workspace\DAT510\src\Innlevering_1/polytext.txt
	
	
	/* A Method that count the amount of segment that is in the whole text.
	 * Innput is the text and a word,segment or a letter that you want to 
	 * find the amount of. 
	 */
	public static int lettercounter(String text, String check) {
		int sum = 0;
		String segment = "";
		for(int i = 0;i <= text.length()-check.length(); i++) {
			segment = text.substring(i, check.length()+i);
			if(segment.equals(check)) {
				sum++;
			}
		}
		return sum;
	}
	
	/* A method that return a list of amount of letter in the alphabet in the text.
	 * The first index is the amount of letter A and the last element is the amount of letter
	 * Z. It only count uppercase letters.
	 */
	public static ArrayList<Integer> totalletters(String text) {
		ArrayList<Integer> sum = new ArrayList<Integer>();
		char letter;
		int amount = 0;
		for(int i = 65;i <= 90;i++) {
			letter = (char)i;
			amount = lettercounter(text,String.valueOf(letter));
			sum.add(amount);
		}
		return sum;
	}
	
	/* A method that return a list of all the segment combination that exist in the text
	 * by a specified length. the innput is the text and the lenght of the segment
	 * and it will find all the possible combination and the amount of that combination
	 */
	public static ArrayList<pair> Ngram(String text,int length) {
		ArrayList<pair> counter = new ArrayList<pair>();
		String term = "";
		boolean check = false;
		for(int i = 0; i < text.length(); i++) {
			if((i+length) <= text.length()) {
				term = text.substring(i, i+length);
			} 
			for(pair e: counter) {
				if(e.getWord().equals(term)) {
					check = true;
					break;
				}
			}
			if(check == false) {
				counter.add(new pair(term, lettercounter(text, term)));
			} else {
				check = false;
			}
		}
		return counter;
	}
	
	/* This method just find the position and add it the the existing
	 *  list of pairs that exist in the document
	 */
	public static void position(ArrayList<pair> list, String text) {
		for(pair e : list) {
			for(int i = 0;i < text.length(); i++) {
				if((i+e.getWord().length()) <= text.length() && e.getWord().equals(text.substring(i, i + e.getWord().length()))) {
					e.add(i);
				}
			}
		}
	}
	
	/* This method just read a file, remove the space and put each
	 *  segment in a list and then return a new String where of the 
	 *  text with no space.
	 * 
	 */
	public static String preperation(String text) {
		String[] list = text.split(" ");
		String newText = "";
		for(int i = 0; i < list.length;i++) {
			newText+=list[i];
		}
		return newText;
	}
	
	/* This method takes a text and a key and comapare letter by letter 
	 * in the text that is supposed to be decrypted by transforming the 
	 * letter into decimal by using the ACII-table and the substract.
	 * If the word is less that 65, it's start from 91 and substract the remaining part. 
	 */
	public static String decrypt(String text, String key) {
		int value;
		int crypt;
		int sum;
		int j = 0;
		String newText = "";
		for(int i = 0; i < text.length(); i++) {
			value = text.charAt(i);
			crypt = key.charAt(j);
			sum = value-crypt;
			if(sum < 0) {
				sum += 91;
			} else {
				sum += 65;
			}
			j++;
			newText += (char) sum;
			if(j == key.length()) {
				j = 0;
				newText += " ";
			}
		}
		return newText;
		
	}
	
	/* This is where i test out every combination and decrypt the key
	 * 
	 */
	public static void main(String[] args) throws Exception {
		// creating a String containing the ciphertext
		String text = "BQZRMQ KLBOXE WCCEFL DKRYYL BVEHIZ NYJQEE BDYFJO PTLOEM EHOMIC UYHHTS GKNJFG EHIMK NIHCTI HVRIHA RSMGQT RQCSXX CSWTNK PTMNSW AMXVCY WEOGSR FFUEEB DKQLQZ WRKUCO FTPLOT GOJZRI XEPZSE ISXTCT WZRMXI RIHALE SPRFAE FVYORI HNITRG PUHITM CFCDLA HIBKLH RCDIMT WQWTOR DJCNDY YWMJCN HDUWOF DPUPNG BANULZ NGYPQU LEUXOV FFDCEE YHQUXO YOXQUO DDCVIR RPJCAT RAQVFS AWMJCN HTSOXQ UODDAG BANURR REZJGD VJSXOO MSDNIT RGPUHN HRSSSF VFSINH MSGPCM ZJCSLY GEWGQT DREASV FPXEAR IMLPZW EHQGMG WSEIXE GQKPRM XIBFWL IPCHYM OTNXYV FFDCEE YHASBA TEXCJZ VTSGBA NUDYAP IUGTLD WLKVRI HWACZG PTRYCE VNQCUP AOSPEU KPCSNG RIHLRI KUMGFC YTDQES DAHCKP BDUJPX KPYMBD IWDQEF WSEVKT CDDWLI NEPZSE OPYIW";
		text = preperation(text); // removing the space
		
		int nGramLength = 3; // change the number to decide the lenght of the Ngram
		int frequency = 3;  // change the number to filter the frequency of a term
		String key = "BDLAEKCY"; // change this to try out different keys for decryption
		
		ArrayList<pair> ngram = Ngram(text,nGramLength); // list of pairs
		position(ngram,text); // adding the position
		
		// printing out the ngram data
		for(pair e : ngram) {
			if(e.getFrequence() >= frequency) {
				e.distance();
				System.out.println(e.toString());
			}
		}
			System.out.println(text); // printing the ciphertext with no space
			System.out.println(decrypt(text,"AAAAAAAA")); // printing out the ciphertext spaced by the key length
			System.out.println(decrypt(text,key)); // printing out the plaintext
		
		
		
	}

}
