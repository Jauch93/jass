package jass;

public abstract class ATable 
{
	protected Karte[] gespielteKarte;
	protected int cardsPlayed = 0;
	protected Deck deck;
	protected int punkte = 0;
	
	ATable(int n, Deck deck)
	{
		gespielteKarte = new Karte[n];
		this.deck = deck;
	}
	
	public int getPunkte()
	{
		return punkte;
	}
	
	public void playKarte(Karte k)
	{
		gespielteKarte[cardsPlayed] = k;
		punkte =+ k.getPunkte();
		cardsPlayed++;
	}
	
	public Karte getLastCardBack()
	{
		Karte ret = gespielteKarte[cardsPlayed];
		cardsPlayed--;
		return ret;
	}

	public void cleanTable()
	{
		for(int i = 0; i < cardsPlayed; i++)
			deck.addKarte(gespielteKarte[i]);
	}
	
	public String getLastCardName()
	{
		return gespielteKarte[cardsPlayed].getName();
	}
	
	public int getLastCardWert()
	{
		return gespielteKarte[cardsPlayed].getWertigkeit();
	}
}
