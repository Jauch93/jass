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
	private int[] trumpfWerte = {1, 1, 1, 1, 1, 1};
	
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
		{
			String[] farben = {"Eichle", "Schilte", "Rose", "Schalle"};		//Deutschweizer set
			deck = new Deck(farben);
		}
		else
		{
			String[] farben = {"Herz", "Ecke", "Schuufle", "Pik"};		//französisches Deck
			deck = new Deck(farben);
		}
		String[] farben = deck.getFarben();
		for(int i = 0; i < farben.length; i++)
			this.OverrideTrumpfArten(farben[i], i);
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
			((JassSpieler)spieler[i]).sortiereKarten(deck.getFarben());
		
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
	
	public void OverrideTrumpfArten(String art, int n)
	{
		trumpfArten[n] = art;
	}
	
//--------------------------------------------------------------------------RundenAlgorithmus
	
	public int startAbstractRunde(int roundNumber) throws Exception
	{
		JassTable table = new JassTable(this.getAnzahlSpieler(), deck);

		for(int i = offset; i < (offset + this.getAnzahlSpieler()); i++)
		{
			int k = i % this.getAnzahlSpieler();
			for(;;)
			{
				table.addKarte(spieler[k].playCard());
				if(table.checkForColorError(trumpfArten[trumpf], spieler[k].getKarten()))
				{
					System.out.println("Fehler. Die Farbe auf dem Tisch muss gehalten Werden!");
					spieler[k].takeKarte(table.getLastCardBack());
					((JassSpieler)spieler[k]).sortiereKarten(deck.getFarben());
				}
				else
					break;		//Wurde die Farbe gehalten, oder ist das nichtHalten legitim, erfolgt hier der break.
			}
			System.out.println(spieler[k].getName() + " hat " + table.getLastCardColor() + " " + table.getLastCardName() + " gespielt.");
			System.out.println();
			
		}
		offset = offset + table.getTableWinner();
		offset = offset%this.getAnzahlSpieler();
		int rundenPunkte = table.getPunkte();
		if(roundNumber == rundenProMatch-1)				//BonusPunkte für letzte Runde
		{
			rundenPunkte += (5 * trumpfWerte[trumpf]);
		}
		table.cleanTable();
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
