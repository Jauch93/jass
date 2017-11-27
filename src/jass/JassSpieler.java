package jass;

import java.io.IOException;

public class JassSpieler extends ASpieler
{
	JassSpieler(String n, int maxKarten)
	{
		super(n,maxKarten);
	}
	
	public int setTrumpf() throws IOException {
		System.out.println(this.getName() + " wähle einen Trumpf: [Eichle, Schilte, Rose, Schalle, UnneUfe, ObeAbe]");
		this.printCards();
		String trumpfEingabe = null;
		int trumpf = 0;
		for(;;)
		{
			boolean validTrumpf = false;
			java.io.BufferedReader cin;
			cin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			trumpfEingabe = cin.readLine();
			String[] trumpfArten = JassTurnier.getTrumpfArten();
			for(int i = 0; i < trumpfArten.length; i++)
			{
				if(trumpfEingabe.equals(trumpfArten[i]))
				{
					validTrumpf = true;	
					trumpf = i;
				}
				if(validTrumpf)
					break;
			}
			if(validTrumpf)
				break;
			else
			{
				System.out.println("Diese TrumpfArt gibt es nicht. Versuche es mit:");
				for(int i = 0; i < trumpfArten.length; i++)
					System.out.print(trumpfArten[i] + " - ");
			}
		}
		return trumpf;
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
}
