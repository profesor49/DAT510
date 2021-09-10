package handIn_1;

public class TripleSDES {
	private String raw_1;
	private String raw_2;
	private String plaintext;
	private String cryptotext;
	
	// declaring a TripleSDES, same as SDES but with a another key
	public TripleSDES(String raw_1, String raw_2, String text, int type) {
		this.raw_1 = raw_1;
		this.raw_2 = raw_2;
		if(type == 0) {
			this.plaintext = text;
		} else {
			this.cryptotext = text;
		}
		
	}
	
	// declaring a TripleSDES without values, needed for breaking the tripleSDES
	public TripleSDES() {
		
	}
	
	// get the first key
	public String getRaw_1() {
		return raw_1;
	}
	// set the first key
	public void setRaw_1(String raw_1) {
		this.raw_1 = raw_1;
	}
	
	// get the second key
	public String getRaw_2() {
		return raw_2;
	}

	//set the second key
	public void setRaw_2(String raw_2) {
		this.raw_2 = raw_2;
	}

	// get the ciphertext
	public String getCryptotext() {
		return cryptotext;
	}

	// set the ciphertext
	public void setCryptotext(String cryptotext) {
		this.cryptotext = cryptotext;
	}

	// get the plaintext
	public String getPlaintext() {
		return plaintext;
	}

	// encrypting the plaintext by using the SDES object
	public void encryption() {
		SDES first = new SDES(raw_1, plaintext, 0);
		first.encryption();
		SDES second = new SDES(raw_2, first.getCryptotext(), 1);
		second.decryption();
		first.setPlaintext(second.getPlaintext());
		first.encryption();
		this.cryptotext = first.getCryptotext();
	}
	
	// decrypting the ciphertext by using the SDES object
	public void decryption() {
		SDES first = new SDES(raw_1, cryptotext, 1);
		first.decryption();
		SDES second = new SDES(raw_2, first.getPlaintext(), 0);
		second.encryption();
		first.setCryptotext(second.getCryptotext());
		first.decryption();
		this.plaintext = first.getPlaintext();
	}

}
