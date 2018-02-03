package jass;

public class Karte
{
	private int wertigkeit = 0;
	private int punkte = 0;
	private String name;
	private String farbe;
	
	/**
	 * 
	 * @param f The Color of the Card
	 * @param n The Name of the Card
	 */
	Karte(String f, String n)
	{
		farbe = f;
		name = n;
	}
	
	/**
	 * @param k A ByteArray which represents a Card
	 * 
	 */
	Karte(byte[] k)		//Generates a Card from a Byte-Array
	{
		String buf = new String(k);
		StringBuffer karte = new StringBuffer(buf);
		int n = 0;
		this.wertigkeit = Integer.parseInt(karte.substring(0, n = karte.indexOf("-")));
		this.punkte = Integer.parseInt(karte.substring(n+1, n = karte.indexOf("-", n+1)));
		this. name = karte.substring(n+1, n = karte.indexOf("-", n+1));
		this.farbe = karte.substring(n+1, karte.length());
		
	}
	
	/**
	 * 
	 * @param p The Number of Points (Value), this Card counts.
	 */
	public void setPunkte(int p)
	{
		punkte = p;
	}
	
	/**
	 * 
	 * @return Returns the Number of Points (Value), this Card counts.
	 */
	public int getPunkte()
	{
		return punkte;
	}
	
	public void setWertigkeit(int w)
	{
		wertigkeit = w;
	}
	public int getWertigkeit()
	{
		return wertigkeit;
	}
	
	public void setName(String n)
	{
		name = n;
	}	
	public String toString()
	{
		return (farbe + " " + name);
	}
	
	public String getFarbe() {
		return farbe;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return Return a ByteArray that describes the Object, with which another card-Object can be constructed.
	 */
	public byte[] toByte()  ///Converts Card to a Byte-Array.
	{
		String buffer = new String();
		buffer = (buffer + wertigkeit + "-" + punkte + "-" + name + "-" + farbe);
		return buffer.getBytes();
	}
}
