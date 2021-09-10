package HandIn_3;

import java.sql.Timestamp;
import java.time.Clock;
import java.util.ArrayList;

public class Block {
	private String hash;
	private Timestamp time;
	private String preHash;
	private ArrayList<String> data;
	private String root;
	
	public Block(ArrayList<String> data, String preHash) {
	time = new Timestamp(Clock.systemDefaultZone().millis());
	this.preHash = preHash;
	root = new Merkle_tree(data).getRoot().getData().getData();
	this.data = data;
	hash = hash();
	}
	
	// used to creat the current hash 
	private String hash() {
		String data = preHash + time.toString() + root;
		return new SHA(data).getData();
	}
	
	public String getHash() {
		return hash;
	}

	public Timestamp getTime() {
		return time;
	}

	public String getPreHash() {
		return preHash;
	}


	public String getRoot() {
		return root;
	}
	
	public ArrayList<String> getData() {
		return data;
	}
	
	// used to print out the text
	public String toString() {
		String text = "";
		text += "Header\n";
		text += "Hash: " + hash + "\n";
		text += "Timestamp: " + time.toString() + "\n";
		text += "Previous Hash: " + preHash + "\n";
		text += "Root: " + root + "\n";
		text += "Transaction: ";
		for(int i = 0; i < data.size(); i++) {
			text += (i+1) + ". " + data.get(i) + " ";
		}
		text += "\n";
		return text;
	}

}
