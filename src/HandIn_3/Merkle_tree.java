package HandIn_3;

import java.util.ArrayList;

public class Merkle_tree {
	private node root;
	
	public Merkle_tree(ArrayList<String> data) {
		ArrayList<node> temp = initial(data);
		this.root = complete(temp);
	}
	
	// hash the data and encapsulate it
	private ArrayList<node> initial(ArrayList<String> data) {
		ArrayList<node> temp = new ArrayList<node>();
		int len = data.size();
		for(int i = 0; i < len; i++) {
			temp.add(new node(new SHA(data.get(i))));
		}
		return temp;
	}
	
	// creating the whole tree from bottom to the root
	private node complete(ArrayList<node> data) {
		ArrayList<node> res = data;
		int len = data.size();
		while(len != 1) {
			ArrayList<node> temp = new ArrayList<node>();
			for(int i = 0; i < len; i += 2) {
				try {
				temp.add(new node(res.get(i),res.get((i+1))));
				} catch(Exception e) {
					temp.add(new node(res.get((len-1))));
				}
			}
			
			res = temp;
			len = res.size();
		}
		return res.get(0);
	}
	
	// accessing the root node
	public node getRoot() {
		return root;
	}
	
}
