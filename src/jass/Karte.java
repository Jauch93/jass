package jass;

public class Karte
{
	private int wertigkeit = 0;
	private int punkte = 0;
	private String name;
	private String farbe;
	
	Karte(String f, String n)
	{
		farbe = f;
		name = n;
	}
	
	public void setPunkte(int p)
	{
		punkte = p;
	}
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
}
