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
		this.supplyADeck();
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
		JassTable table = new JassTable(this.getAnzahlSpieler(), deck, trumpfArten[trumpf]);

		for(int i = offset; i < (offset + this.getAnzahlSpieler()); i++)
		{
			int k = i % this.getAnzahlSpieler();
			for(;;)
			{
				table.addKarte(spieler[k].playCard());
				if(table.checkForColorError(spieler[k].getKarten()))
				{
					ASpiel.printString("Fehler. Die Farbe auf dem Tisch muss gehalten Werden!");
					spieler[k].addKarte(table.returnLastCard());
					((JassSpieler)spieler[k]).sortiereKarten(deck.getFarben());
				}
				else if(table.checkForUnderTrumpfe(spieler[k].getKarten()))
				{
					ASpiel.printString("Fehler. UnderTrumpfen ist nicht erlaubt!");
					spieler[k].addKarte(table.returnLastCard());
					((JassSpieler)spieler[k]).sortiereKarten(deck.getFarben());
				}
				else
					break;		//Wurde die Farbe gehalten, oder ist das nichtHalten legitim, erfolgt hier der break.
			}
			ASpiel.printString(spieler[k].getName() + " hat " + table.getLastCardColor() + " " + table.getLastCardName() + " gespielt.");
			ASpiel.printString("");
			
		}
		offset = offset + table.getTableWinner();
		offset = offset%this.getAnzahlSpieler();
		int rundenPunkte = table.getPunkte();
		if(roundNumber == rundenProMatch-1)				//BonusPunkte für letzte Runde
		{
			rundenPunkte += (5 * trumpfWerte[trumpf]);
		}
		table.clearTable();
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
							karten[k].setWertigkeit(10);
							karten[k].setName("Nell");
							karten[k].setPunkte(14);
						}
						if(karten[k].getName().equals("Under"))			//handelt es sich um den Buur?
						{
							karten[k].setWertigkeit(11);
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
						if(karten[k].getName().equals("Ass"))
							karten[k].setPunkte(0);
						if(karten[k].getName().equals("6"))
							karten[k].setPunkte(11);
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
