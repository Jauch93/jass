package jass;

public abstract class ASpiel 
{
	protected Deck deck;
	protected Spieler spieler[];
	
	public abstract void supplyADeck();						//Abstrakt, denn je nach SpielArt wird ein anderes Deck benötigt.
	public abstract void startTurnier() throws Exception;
	public abstract void startMatch() throws Exception;
	public abstract void startRunde(int roundNumber) throws Exception;	
	
	
	
	ASpiel(int anzahlSpieler, int handKarten)
	{
		spieler = new Spieler[anzahlSpieler];
		for(int i = 0; i < anzahlSpieler; i++)
		{
			String name = ("Spieler " + i);							//SpielerName hier anwählen, mit cin noch ergänzen!
			spieler[i] = new Spieler(name, handKarten);
		}
	}
	
	public void printPunkte()
	{
		for(int i = 0; i < getAnzahlSpieler(); i++)
			System.out.print(spieler[i].getName() + " " + spieler[i].getPunkte() + " -- ");
		System.out.println();
	}
	
	public void verteileKarten()							//*Verteilt Alle Karten unter den Spielern
	{
		int anzahlKarten = deck.length()/this.getAnzahlSpieler();
		for(int i = 0; i < this.getAnzahlSpieler(); i++)
		{
			for(int j = 0; j < anzahlKarten; j++)
			{
				spieler[i].takeKarte(deck.drawRandom());
			}
		}
	}
	
	public void verteileKarten(int n)						//*Verteilt allen Spielern n Karten
	{
		for(int i = 0; i < spieler.length; i++)
		{
			for(int j = 0; j < n; j++)
				spieler[i].takeKarte(deck.drawRandom());
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
		return spieler.length;
	}
}
