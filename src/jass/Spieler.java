package jass;

import java.io.IOException;

public class Spieler 
{
	private int anzahlKarten = 0;
	private Karte handKarten[];
	private int punkte = 0;
	private String name;
	//private static int maxKarten;
	

	Spieler(String n, int maxKarten)
	{
		//maxKarten = max;
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
	
	public void sortiereKarten()					//Karten auf Hand werden  nach Farbe sortiert, aber noch nicht nach Punkten
	{
		Karte[] tmp = new Karte[handKarten.length];
		int k = 0;													//Nach Farben sortieren
		for(int i = 0; i < handKarten.length; i++)
		{
			if(handKarten[i].getFarbe().equals("Eichle"))
			{
				tmp[k] = handKarten[i];
				k++;
			}
		}
		for(int i = 0; i < handKarten.length; i++)
		{
			if(handKarten[i].getFarbe().equals("Schilte"))
			{
				tmp[k] = handKarten[i];
				k++;
			}
		}
		for(int i = 0; i < handKarten.length; i++)
		{
			if(handKarten[i].getFarbe().equals("Rose"))
			{
				tmp[k] = handKarten[i];
				k++;
			}
		}
		for(int i = 0; i < handKarten.length; i++)
		{
			if(handKarten[i].getFarbe().equals("Schalle"))
			{
				tmp[k] = handKarten[i];
				k++;
			}
		}
		for(;;)						//Nach Wertigkeit sortieren
		{
			boolean everythingSorted = true;
			for(int i = 0; i < tmp.length-1; i++)
			{
				if(tmp[i].getFarbe() == tmp[i+1].getFarbe())
				{
					if(tmp[i].getWertigkeit() > tmp[i+1].getWertigkeit())
					{
						Karte copy = tmp[i];
						tmp[i] = tmp[i+1];
						tmp[i+1] = copy;
						everythingSorted = false;
					}
				}
			}
			if(everythingSorted)
				break;
		}
		handKarten = tmp;		
	}
	
	public Karte[] showCards()
	{
		for(int i = 0; i < anzahlKarten; i++)
			System.out.println(i + " - " + handKarten[i].toString());
		return handKarten;
	}

	public Karte playCard() throws Exception
	{
		this.showCards();
		System.out.print(name + ", Wähle eine Karte: ");
		java.io.BufferedReader cin;
		cin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		int k = Integer.parseInt(cin.readLine());
		Karte ret = null;
			if(k >= anzahlKarten)
				throw new IndexOutOfBoundsException();
			ret = handKarten[k];
			for(int i = k; i < anzahlKarten-1; i++)
				handKarten[i] = handKarten[i+1];
		anzahlKarten--;
		return ret;
	}

	public String setTrumpf() throws IOException {
		System.out.println(this.getName() + " wähle einen Trumpf: [Eichle, Schilte, Rose, Schalle, UnneUfe, ObeAbe]");
		this.showCards();
		java.io.BufferedReader cin;
		cin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		String trumpf = cin.readLine();
		return trumpf;
	}

}
