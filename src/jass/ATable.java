package jass;

public abstract class ATable 
{
	protected Karte[] karten;
	protected int kartenGespielt = 0;
	protected Deck deck;
	protected int punkte = 0;
	
	public abstract int getTableWinner();
	
	ATable(int n, Deck deck)
	{
		karten = new Karte[n];
		this.deck = deck;
	}
	
	public int getPunkte()
	{
		return punkte;
	}
	
	public void addKarte(Karte k)
	{
		karten[kartenGespielt] = k;
		punkte = punkte + k.getPunkte();
		kartenGespielt++;
	}
	
	public Karte returnLastCard()
	{
		Karte ret = karten[kartenGespielt-1];
		punkte = punkte - ret.getPunkte();
		kartenGespielt--;
		return ret;
	}

	public void clearTable()
	{
		for(int i = 0; i < kartenGespielt; i++)
			deck.addKarte(karten[i]);
	}
	
	public String getLastCardName()
	{
		return karten[kartenGespielt-1].getName();
	}
	
	public int getLastCardWert()
	{
		return karten[kartenGespielt-1].getWertigkeit();
	}
	
	public Karte[] getKarten()
	{
		return karten;
	}
	
	public String getLastCardColor()
	{
		return karten[kartenGespielt-1].getFarbe();
	}
}
