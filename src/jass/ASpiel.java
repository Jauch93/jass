package jass;

public abstract class ASpiel 
{
	
//-----------------------------------------------------------------Attribute
	protected Deck deck;
	protected Spieler spieler[];
	
//-----------------------------------------------------------------Abstrakte Methoden
	
	public abstract void supplyADeck();						//Abstrakt, denn je nach SpielArt wird ein anderes Deck benötigt.
	
//-----------------------------------------------------------------Methoden
	
	ASpiel(int anzahlSpieler, int handKarten)
	{
		spieler = new Spieler[anzahlSpieler];
	}
	
	public abstract void printPunkte();
	
	public static void printKarten(Karte[] karten)		//Für KonsolenAusgabe!!!!
	{
		for(int i = 0; i < karten.length; i++)
		{
			System.out.println(karten[i].getFarbe() + " " + karten[i].getName());
		}
	}
	
	public static void printKartenNumbered(Karte[] karten)
	{
		for(int i = 0; i < karten.length; i++)
		{
			System.out.println(i + " - " + karten[i].getFarbe() + " " + karten[i].getName());
		}
	}
	
	public static void printString(String str)
	{
		System.out.println(str);
	}
	
	public static void printStringOnLine(String str)
	{
		System.out.print(str);
	}
	
	public void verteileKarten()							//*Verteilt Alle Karten unter den Spielern
	{
		int anzahlKarten = deck.length()/this.getAnzahlSpieler();
		for(int i = 0; i < this.getAnzahlSpieler(); i++)
		{
			for(int j = 0; j < anzahlKarten; j++)
			{
				this.spieler[i].addKarte(deck.drawRandom());
			}
		}
	}
	
	public void verteileKarten(int n)						//*Verteilt allen Spielern n Karten
	{
		for(int i = 0; i < this.getAnzahlSpieler(); i++)
		{
			for(int j = 0; j < n; j++)
				this.spieler[i].addKarte(deck.drawRandom());
		}
	}
	
	public void resetTurnier()								//*Alle Punkte nullen, Karten frisch verteilen.
	{
		for(int i = 0; i < spieler.length; i++)
		{
			spieler[i].resetPunkte();
		}
	}
	
	public Spieler getLeader()							//*Gibt den Spieler mit den meisten Punkte zurück.
	{
		int punkte = 0;
		int winner = 0;
		for(int i = 0; i < spieler.length; i++)
		{
			if(spieler[i].getPunkte() > punkte)
			{
				punkte = spieler[i].getPunkte();
				winner = i;
			}
		}
		return spieler[winner];
	}
	
	public int getAnzahlSpieler()
	{
		return this.spieler.length;
	}
}
