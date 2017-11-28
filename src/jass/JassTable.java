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
	
	public String getLastColor()
	{
		return gespielteKarte[cardsPlayed].getFarbe();
	}
	
}
