package HandIn_3;

import java.math.BigInteger;

public class node {
	private SHA data;
	private node left;
	private node right;
	
	// used to create the leaf node
	public node(SHA data) {
		this.data = data;
		left = null;
		right = null;
	}
	
	// used to creat the parent node with two children
	public node(node left, node right) {
		BigInteger le = new BigInteger(left.getData().getData(),16);
		BigInteger ri = new BigInteger(right.getData().getData(),16);
		this.data = new SHA(le.add(ri).toString());
		this.left = left;
		this.right = right;
	}
	
	// used to create the parent node with one child
	public node(node left) {
		BigInteger le = new BigInteger(left.getData().getData(),16);
		this.data = new SHA(le.toString());
		this.left = left;
		this.right = null;
	}

	public SHA getData() {
		return data;
	}
	
	// used to get the Hashed value inside the node
	public String Value() {
		return data.getData();
	}

	public node getLeft() {
		return left;
	}

	public node getRight() {
		return right;
	}

		

}
