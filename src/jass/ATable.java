package jass;

public abstract class ATable 
{
	protected Karte[] gespielteKarte;
	protected int cardsPlayed = 0;
	protected Deck deck;
	protected int punkte = 0;
	
	public abstract int getTableWinner();
	
	ATable(int n, Deck deck)
	{
		gespielteKarte = new Karte[n];
		this.deck = deck;
	}
	
	public int getPunkte()
	{
		return punkte;
	}
	
	public void addKarte(Karte k)
	{
		gespielteKarte[cardsPlayed] = k;
		punkte = punkte + k.getPunkte();
		cardsPlayed++;
	}
	
	public Karte getLastCardBack()
	{
		Karte ret = gespielteKarte[cardsPlayed-1];
		punkte = punkte - ret.getPunkte();
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
		return gespielteKarte[cardsPlayed-1].getName();
	}
	
	public int getLastCardWert()
	{
		return gespielteKarte[cardsPlayed].getWertigkeit();
	}
	
	public Karte[] getKarten()
	{
		return gespielteKarte;
	}
	
	public String getLastCardColor()
	{
		return gespielteKarte[cardsPlayed-1].getFarbe();
	}
}
