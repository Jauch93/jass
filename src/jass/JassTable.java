package jass;

public class JassTable extends ATable
{
	String trumpf;

	JassTable(int n, Deck deck, String trumpf) {
		super(n, deck);
		this.trumpf = trumpf;
	}
	
	public String getTableColor()
	{
		return karten[0].getFarbe();
	}

	public int getTableWinner() //Noch verschiedene Farben unterscheiden und Trumpf ausklammern!
	{
		int maxWert = karten[0].getWertigkeit();
		int winner = 0;
		for(int i = 1; i < kartenGespielt; i++)
		{
			if(karten[i].getFarbe().equals(getTableColor()) || karten[i].getFarbe().equals(trumpf))
			{
				if(karten[i].getWertigkeit() > maxWert)
				{
					maxWert = karten[i].getWertigkeit();
					winner = i;
				}
			}
		}
		return winner;
	}
	
	public boolean checkForColorError(Karte[] comp)
	{
		boolean holdColorError = false;
		if(kartenGespielt > 0)	//If - Nicht der erste Spieler dieser Runde
		{
			if(!this.getLastCardColor().equals(this.getTableColor())) 		//Farben nicht gleich
			{
				if(!this.getLastCardColor().equals(trumpf))				//UND kein Trumpf
				{
					for(int c = 0; c < comp.length; c++)				//Vergleiche TischFarbe mit allen Farben auf der Hand
					{
						if(this.getTableColor().equals(comp[c].getFarbe())) //hat der Spieler die geforderte Farbe?
						{
							if(comp[c].getName().compareTo("Buur") != 0)	//Buur muss nicht gelegt werden!	
								holdColorError = true;
						}
					}
				}
			}
		}
		return holdColorError;
	}
	
	public boolean checkForUnderTrumpfe(Karte[] comp)
	{
		boolean undertrumpft = false;
		if(getLastCardColor().equals(trumpf))			//Wurde als letztes eine Trumpfkarte gespielt?
		{
			if(!karten[0].getFarbe().equals(trumpf)) 	//Erste gespielte Karte kein Trumpf? ja-->dann wurde abgestochen!
			{	
				for(int i = 1; i < kartenGespielt - 1; i++)		//Gehe die gelegten Karten durch, bis auf die erste und letzte
				{
					if(karten[i].getFarbe().equals(trumpf))		//Wurde bereits eine TrumpfKarte ausgelegt?
					{
						if(karten[i].getWertigkeit() > getLastCardWert())	//hat die gefundene TrumpfKarte einen höheren Wert?
						{
							undertrumpft = true;
						}
					}
				}
				for(int i = 0; i < comp.length; i++)		//Hat der Spieler nur noch Trümpfe auf der Hand?
				{
					int k = 0;
					if(comp[i].getFarbe().equals(trumpf))
						k++;
					if(k == comp.length)
						undertrumpft = false;
				}
			}
		}
		return undertrumpft;
	}
		
}
