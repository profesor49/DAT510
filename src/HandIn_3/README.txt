The whole hand-in is coded with java using the eclipse environment
The content of the directory handIn_3 is:

* HandIn_3
	* run.java
	* Block.java
	* SHA.java
	* Merkle_tree.java
	* node.java
	* README.txt
	
There are no package that is require to be downloaded and implemented

Block.java is the block used in simplfied blockchain and uses the SHA.java,
Merkle_java. It is important to use a ArrayList object to create a block in
java, do not use a array. 

SHA.java is based on the agorithm used in SHA-2 where it produces a 256bit 
value in hexidecimal String.

Merkle_tree.java creates the whole tree from bottom-up, but since the assignment only
asked for the hashed value of the root, you can access onyl access the root with ease.
If desired to check the tree itself, alot of getLeft() and getRight() function is required
The Merkle_tree also uses the node.java class to create the nodes inside Merkle_tree.

To run the code, run the run.java file. the communication between the console and the user
done throught the use of a class called Scanner. The program should be able to handle errors
very well, but when asked for the number of transaction and to choose a option from the menu,
A int value is require, while the code wont break, it is important to know that to proceed, a
number is require. Anything else can be answear be using a String/number. To end the program,
just type the number 4 when in the menu.
