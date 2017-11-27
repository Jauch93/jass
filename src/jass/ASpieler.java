package jass;

import java.io.IOException;

public abstract class ASpieler 
{
	protected int anzahlKarten = 0;
	protected Karte handKarten[];
	private int punkte = 0;
	private String name;	
	
	public abstract void sortiereKarten();	//Abstrakt. Je nach SpielForm wird anders geordnet!
	public abstract int setTrumpf() throws IOException;

	ASpieler(String n, int maxKarten)
	{
		name = n;
		handKarten = new Karte[maxKarten];
		System.out.println(name + " has been Created");
	}
	
	public String getName() 
	{
		return name;
	}

	public void resetPunkte() 
	{
		punkte = 0;		
	}

	public void addPunkte(int p)
	{
		punkte += p;
	}
	
	public int getPunkte()
	{
		return punkte;
	}
	
	public void takeKarte(Karte k)
	{
			handKarten[anzahlKarten] = k;
			anzahlKarten++;
	}
	
	public Karte[] getKarten()
	{
		return handKarten;
	}
	
	public void printCards()
	{
		for(int i = 0; i < anzahlKarten; i++)
			System.out.println(i + " - " + handKarten[i].toString());
	}

	public Karte playCard() throws Exception
	{
		this.printCards();
		System.out.print(name + ", Wähle eine Karte: ");
		Karte ret = null;
		int k = 0;
		for(;;)
		{
			try
			{
				java.io.BufferedReader cin;
				cin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
				k = Integer.parseInt(cin.readLine());
				if(k >= anzahlKarten)
					throw new IndexOutOfBoundsException();
				//if()
				break;
				
			}
			catch(IndexOutOfBoundsException e)
			{
				System.out.println("Ungültige Eingabe");
			}
		}
			ret = handKarten[k];
			for(int i = k; i < anzahlKarten-1; i++)
				handKarten[i] = handKarten[i+1];

			
		anzahlKarten--;
		return ret;
	}

}
