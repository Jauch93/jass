package jass;

public class JassTable extends ATable
{

	JassTable(int n, Deck deck) {
		super(n, deck);
	}
	
	public String getTableColor()
	{
		return karten[0].getFarbe();
	}

	public int getTableWinner() //Noch verschiedene Farben unterscheiden und Trumpf ausklammern!
	{
		int maxWert = 0;
		int winner = 0;
		for(int i = 0; i < kartenGespielt; i++)
		{
			if(karten[i].getWertigkeit() > maxWert)
			{
				maxWert = karten[i].getWertigkeit();
				winner = i;
			}
		}
		return winner;
	}
	
	public boolean checkForColorError(String trumpf, Karte[] comp)
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
		
}
