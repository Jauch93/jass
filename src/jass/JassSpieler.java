package jass;

import java.io.IOException;

public class JassSpieler extends Spieler
{
	JassSpieler(String n, int maxKarten)
	{
		super(n,maxKarten);
	}
	
	public int setTrumpf(boolean schiebenAllowed, String[] trumpfArten) throws IOException {
		System.out.println(this.getName() + " wähle einen Trumpf: ");
		for(int i = 0; i < trumpfArten.length; i++)
			System.out.print(trumpfArten[i] + " - ");
		if(schiebenAllowed)
			System.out.print("Schieben");
		System.out.println();
		this.printCards();
		String trumpfEingabe = null;
		int trumpf = 0;
		for(;;)
		{
			boolean validTrumpf = false;
			java.io.BufferedReader cin;
			cin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			trumpfEingabe = cin.readLine();
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
			if(schiebenAllowed && trumpfEingabe.equals("Schieben"))
			{
				trumpf = -1;
				validTrumpf = true;
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
	
	public void sortiereKarten(String farben[])					
	{
		Karte[] tmp = new Karte[handKarten.length];
		int k = 0;	
		//Nach Farben sortieren
		for(int i = 0; i < handKarten.length; i++)
		{

			if(handKarten[i].getFarbe().equals(farben[0]))
			{
				tmp[k] = handKarten[i];
				k++;
			}
		}

		for(int i = 0; i < handKarten.length; i++)
		{
			if(handKarten[i].getFarbe().equals(farben[1]))
			{
				tmp[k] = handKarten[i];
				k++;
			}
		}
		for(int i = 0; i < handKarten.length; i++)
		{
			if(handKarten[i].getFarbe().equals(farben[2]))
			{
				tmp[k] = handKarten[i];
				k++;
			}
		}
		for(int i = 0; i < handKarten.length; i++)
		{
			if(handKarten[i].getFarbe().equals(farben[3]))
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
