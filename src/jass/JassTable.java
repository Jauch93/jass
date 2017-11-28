package jass;

public class JassTable extends ATable
{

	JassTable(int n, Deck deck) {
		super(n, deck);
	}
	
	public String getTableColor()
	{
		return gespielteKarte[0].getFarbe();
	}

	public int getTableWinner() 
	{
		int maxWert = 0;
		int winner = 0;
		for(int i = 0; i < cardsPlayed; i++)
		{
			if(gespielteKarte[i].getWertigkeit() > maxWert)
			{
				maxWert = gespielteKarte[i].getWertigkeit();
				winner = i;
			}
		}
		return winner;
	}
	
	public boolean checkForColorError(String trumpf, Karte[] comp)
	{
		boolean holdColorError = false;
		if(cardsPlayed > 0)	//If - Nicht der erste Spieler dieser Runde
		{
			if(this.getLastCardColor().compareTo(this.getTableColor())!=0 		//Farben nicht gleich
					&& (this.getLastCardColor().compareTo(trumpf)!=0))			//UND kein Trumpf
			{
				for(int c = 0; c < comp.length; c++)			//Vergleiche TischFarbe mit Farben auf der Hand
				{
					if(this.getTableColor().equals(comp[c].getFarbe())&&(this.getLastCardName().compareTo("Buur") !=0))
						holdColorError = true;
				}
			}
		}
		return holdColorError;
	}
		
}
