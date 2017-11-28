package jass;

public class Deck 
{
	private int cardsInDeck = 0;
	private String defaultFarben[] = {"Eichle", "Rose", "Schilte", "Schalle" };
	private String defaultNamen[] = {"6","7","8","9","Banner","Under","Ober","König","Ass"};
	private int defaultPunkte[] = {0,0,0,0,10,2,3,4,11};
	private int defaultWertigkeit[] = {1,2,3,4,5,6,7,8,9};
	private Karte deck[];

	
	Deck()			//*Erstellt ein normales Deutschschweizer Jassdeck.
	{
		deck = new Karte[36];
		for(int i = 0; i < defaultFarben.length; i++)
		{
			for(int j = 0; j < defaultNamen.length; j++)
			{
				deck[cardsInDeck] = new Karte(defaultFarben[i], defaultNamen[j]);
				deck[cardsInDeck].setPunkte(defaultPunkte[j]);
				deck[cardsInDeck].setWertigkeit(defaultWertigkeit[j]);
				cardsInDeck++;
			}
		}
	}
	
	Deck(String farben[])		//*Erstellt ein JassSet, bei dem die Farben variabel einstellbar sind.
	{
		deck = new Karte[36];
		defaultFarben = farben;
		for(int i = 0; i < defaultFarben.length; i++)
		{
			for(int j = 0; j < defaultNamen.length; j++)
			{
				deck[cardsInDeck] = new Karte(defaultFarben[i], defaultNamen[j]);
				deck[cardsInDeck].setPunkte(defaultPunkte[j]);
				deck[cardsInDeck].setWertigkeit(defaultWertigkeit[j]);
				cardsInDeck++;
			}
		}
	}
	
	Deck(String farben[], String namen[], int punkte[], int wertigkeit[])   //*Erstellt ein Custom-Deck
	{
		deck = new Karte[farben.length * namen.length];
		defaultFarben = farben;
		defaultNamen = namen;
		defaultPunkte = punkte;
		defaultWertigkeit = wertigkeit;
		for(int i = 0; i < defaultFarben.length; i++)
		{
			for(int j = 0; j < defaultNamen.length; j++)
			{
				deck[cardsInDeck] = new Karte(defaultFarben[i], defaultNamen[j]);
				deck[cardsInDeck].setPunkte(defaultPunkte[j]);
				deck[cardsInDeck].setWertigkeit(defaultWertigkeit[j]);
				cardsInDeck++;

			}
		}
		
	}
	
	public void addKarte(Karte k)					//*Erweitert das Deck um eine Karte */
	{
		Karte[] tmp = new Karte[deck.length+1];
		for(int i = 0; i < deck.length; i++)
			tmp[i] = deck[i];
		tmp[deck.length] = k;
		deck = tmp;
		cardsInDeck++;
	}
	
	public void addDeck(Deck d)
	{
		int length = deck.length + d.length();
		Karte[] tmp = new Karte[length];
		for(int i = 0; i < deck.length; i++)
			tmp[i] = deck[i];
		for(int i = deck.length; i < length; i++)
			tmp[i] = d.draw();
		deck = tmp;
		cardsInDeck = length;
	}
	
	public void doubleDeck()
	{
		Karte[] tmp = new Karte[2*deck.length];
		for(int i = 0; i < deck.length; i++)
		{
			tmp[i] = deck[i];
			tmp[i+deck.length] = deck[i];
			cardsInDeck++;
		}
		deck = tmp;
	}
	
	public int length()
	{
		return cardsInDeck;
	}
	
	public String[] getFarben()
	{
		return defaultFarben;
	}
	
	public Karte draw() throws IndexOutOfBoundsException				/**Draws a Card from the top of the Deck */
	{
		Karte ret = deck[cardsInDeck-1];
		cardsInDeck--;
		return ret;
		//@TODO Exception auslösen, wenn Deck leer ist!
	}
	
	public Karte drawRandom() throws IndexOutOfBoundsException			/**Draws a random Card from the Deck */
	{
		int rand = (int)((Math.random() * (cardsInDeck)));
		Karte ret = deck[rand];
		
		for(int i = rand; i < cardsInDeck-1; i++)
			deck[i] = deck[i+1];
		
		cardsInDeck--;
		return ret;
	}

}