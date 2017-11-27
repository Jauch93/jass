package jass;

import java.io.IOException;

public abstract class AJass extends ASpiel
{
	
//----------------------------------------------------------Attribute
	boolean deutschSchweizerKarten = true;
	protected int trumpf;
	protected int rundenProMatch = 9;
	protected int maxPunkte;
	protected int offset = 0; //Gibt an, welcher Spieler als nächstes beginnen muss.
	
	private String[] trumpfArten = {"Eichle", "Schilte", "Rose", "Schalle", "UnneUfe", "ObeAbe"};
	private int[] trumpfWerte = {1, 2, 1, 2, 2, 2};
	
//-----------------------------------------------------------Abstrakte Methoden	
	
	public abstract void setTrumpf() throws IOException;
	public abstract void startTurnier() throws Exception;
	public abstract void startMatch() throws Exception;
	public abstract void startRunde(int roundNumber) throws Exception;	
	
//-----------------------------------------------------------Methoden
	
	AJass(int anzahlSpieler, int handKarten, boolean deutschSchweizerKarten, int maxPunkte)
	{
		super(anzahlSpieler, handKarten);
		this.deutschSchweizerKarten = deutschSchweizerKarten;
		this.rundenProMatch = handKarten;
	}
	
	public void supplyADeck() 
	{
		if(deutschSchweizerKarten)
			deck = new Deck();
		else
			deck = new Deck(); //Hier französchises Deck initialisieren.
	}
	
	public void startAbstractTurnier() throws Exception 
	{
		this.resetTurnier();
		for(;;)
		{
			this.startMatch();
			
			if(this.getLeader().getPunkte() > maxPunkte)
				break;
		}
	}
	
	public void startAbstractMatch() throws Exception 
	{
		this.supplyADeck();
		this.verteileKarten();

		for(int i = 0; i < this.getAnzahlSpieler(); i++)
			((JassSpieler)spieler[i]).sortiereKarten();
		setTrumpf();
		this.setKartenWerte();
		for(int i = 0; i < rundenProMatch; i++)
		{
			this.startRunde(i);	
		}
		this.printPunkte();
	}
	
	public void printPunkte()
	{
		for(int i = 0; i < getAnzahlSpieler(); i++)
			System.out.print(spieler[i].getName() + ": " + spieler[i].getPunkte() + " - ");
		System.out.println();
	}
	
	
	public String[] getTrumpfArten()
	{
		return trumpfArten;
	}
	
//--------------------------------------------------------------------------RundenAlgorithmus
	
	public int startAbstractRunde(int roundNumber) throws Exception
	{
		Karte table[] = new Karte[this.getAnzahlSpieler()];
		int rundenPunkte = 0;
		int maxWert = 0;
		int rundenGewinner = 0;

		for(int i = offset; i < (offset + this.getAnzahlSpieler()); i++)
		{
			int k = i % this.getAnzahlSpieler();
			for(;;)		//In diesem EndlessLoop steck die Mechanik um nicht-Farben abzufangen.
			{
				boolean holdColorError = false;
				table[k] = spieler[k].playCard();
				
				if(i > offset)	//If - Nicht der erste Spieler dieser Runde
				{
					if(table[k].getFarbe().compareTo(table[offset].getFarbe())!=0 		//Farben nicht gleich
							&& (table[k].getFarbe().compareTo(trumpfArten[trumpf])!=0))	//UND kein Trumpf
					{
						Karte[] comp = spieler[k].getKarten();
						for(int c = 0; c < comp.length; c++)
						{
							if(table[offset].getFarbe().equals(comp[c].getFarbe())&&(table[k].getName().compareTo("Buur") !=0))
								holdColorError = true;
						}
					}
				}
				if(holdColorError)
				{
					System.out.println("Fehler. Die Farbe auf dem Tisch muss gehalten Werden!");
					spieler[k].takeKarte(table[k]);
					((JassSpieler)spieler[k]).sortiereKarten();
				}
				else
					break;		//Wurde die Farbe gehalten, oder ist das nichtHalten legitim, erfolgt hier der break.
			}
			
			rundenPunkte += table[k].getPunkte();
			if(i == offset)
				maxWert = table[k].getWertigkeit();
			if(i > offset)
			{
				if(table[k].getWertigkeit() > maxWert)
				{
					if(table[offset].getFarbe().equals(table[k].getFarbe()) || table[k].getFarbe().equals(trumpfArten[trumpf]))
					{
						rundenGewinner = k;
						maxWert = table[k].getWertigkeit();
					}
				}
			}
			System.out.println(spieler[k].getName() + " hat " + table[k].toString() + " gespielt.");
			System.out.println();
			
		}
		if(roundNumber == rundenProMatch-1)				//BonusPunkte für letzte Runde
		{
			rundenPunkte += (5 * trumpfWerte[trumpf]);
		}
		offset = rundenGewinner;
		return rundenPunkte;
	}

//------------------------------------------------------------------------------KartenWerte einstellen
	
	public void setKartenWerte() 				//Hier werden die jeweiligen Trumpfregeln festgelegt.
	{
		switch(trumpf)
		{
			case 0:
			case 1:
			case 2:
			case 3:
			for(int i = 0; i < this.getAnzahlSpieler(); i++)
			{
				Karte[] karten = spieler[i].getKarten();
				for(int k = 0; k < karten.length; k++)
				{
					if(karten[k].getFarbe().equals(this.getTrumpfArten()[trumpf]))
					{					
						if(karten[k].getName().equals("9"))			//handlet es sich um das Nell?
						{
							karten[k].setWertigkeit(9);
							karten[k].setName("Nell");
							karten[k].setPunkte(14);
						}
						if(karten[k].getName().equals("Under"))			//handelt es sich um den Buur?
						{
							karten[k].setWertigkeit(10);
							karten[k].setName("Buur");
							karten[k].setPunkte(20);
						}
						karten[k].setWertigkeit(karten[k].getWertigkeit() + 10);	//alle Trumpfkarten höher Werten als normale Karten.
					}
				}
			}
			break;
			case 4:			//UnneUfe
				for(int i = 0; i < this.getAnzahlSpieler(); i++)
				{
					Karte[] karten = spieler[i].getKarten();
					for(int k = 0; k < karten.length; k++)
					{						
						karten[k].setWertigkeit(10-karten[k].getWertigkeit());		//Invertierung der Werte
					}
				}
			case 5:			//ObeAbe
				for(int i = 0; i < this.getAnzahlSpieler(); i++)
				{
					Karte[] karten = spieler[i].getKarten();
					for(int k = 0; k < karten.length; k++)
					{						
							if(karten[k].getName().equals("8"))			//8er Im Wert raufsetzen.
							{
								karten[k].setPunkte(8);
							}
					}
				}
				break;
		}
		if(trumpfWerte[trumpf] != 1)		//Wie fest werden die einzelnen Farben gewertet?
		{
			int trumpfWert = trumpfWerte[trumpf];
			for(int i = 0; i < this.getAnzahlSpieler(); i++)
			{
				Karte[] karten = spieler[i].getKarten();
				for(int k = 0; k < karten.length; k++)
					karten[k].setPunkte(karten[k].getPunkte() * trumpfWert);
			}
		}
	}

}
