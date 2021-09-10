package handIn_1;

import java.util.ArrayList;

public class pair {
	private String word;
	private int frequence;
	private ArrayList<Integer> position;
	private ArrayList<Integer> distance;
	
	/* 
	 * A method that was created to make a pair of segment and 
	 * the total amount of that segment. Also has the ability to
	 * store position and calculate the distance  
	 */
	public pair(String word, int frequence) {
		this.word = word;
		this.frequence = frequence;
		position = new ArrayList<Integer>();
		distance = new ArrayList<Integer>();
	}
	
	// Gets the String of the word segment
	public String getWord() {
		return word;
	}
	// set the String of the word segment
	public void setWord(String word) {
		this.word = word;
	}
	
	// get the frequency of the word segment
	public int getFrequence() {
		return frequence;
	}
	
	// sets the frequecy of the word segment
	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	// get a list of postion for the word segment
	public ArrayList<Integer> getPosition() {
		return position;
	}
	
	// add a position into the list of position
	public void add(int pos) {
		position.add(pos);
	}
	
	// calculate the distance between each position by taking the x_(n+1) - x_(n)
	public void distance() {
		if(position.size() > 1) {
			for(int i = 0; i < position.size()-1; i++) {
				distance.add(position.get(i+1)-position.get(i));
			}
		}
	}
	
	// this just transform all the information into a String
	// to make it easier to read
	public String toString() {
		String text = this.word + "; Frequency: " + this.frequence + "; Position: ";
		for(int e : this.position) {
			text += e +" ";
		}
		
		text += "; Distance: ";
		for(int e : this.distance) {
			text += e + " ";
		}
		return text;
		
	}
	
	
}
