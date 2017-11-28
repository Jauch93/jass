package jass;

public class Spieler 
{
	protected int anzahlKarten = 0;
	protected Karte handKarten[];
	private int punkte = 0;
	private String name;	
	


	Spieler(String n, int maxKarten)
	{
		name = n;
		handKarten = new Karte[maxKarten];
		ASpiel.printString(name + " has been Created");
	}
	
	public String getName() 
	{
		return name;
	}

	public void resetPunkte() 
	{
		punkte = 0;		
	}

	public void addPunkte(int p)
	{
		punkte += p;
	}
	
	public int getPunkte()
	{
		return punkte;
	}
	
	public void addKarte(Karte k)
	{
			handKarten[anzahlKarten] = k;
			anzahlKarten++;
	}
	
	public Karte[] getKarten()
	{
		Karte[] ret = new Karte[anzahlKarten];
		for(int i = 0; i < anzahlKarten; i++)
			ret[i] = handKarten[i];
		return ret;
	}

	public Karte playCard() throws Exception
	{
		ASpiel.printKartenNumbered(this.getKarten());

		ASpiel.printString(name + ", Wähle eine Karte: ");
		Karte ret = null;
		int k = 0;
		for(;;)
		{
			try
			{
				java.io.BufferedReader cin;
				cin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
				k = Integer.parseInt(cin.readLine());
				if(k >= anzahlKarten || k < 0)
					throw new IndexOutOfBoundsException();
				break;
				
			}
			catch(IndexOutOfBoundsException e)
			{
				ASpiel.printString("Ungültige Eingabe");
			}
			catch(NumberFormatException e)
			{
				ASpiel.printString("Ungültige Eingabe");
			}
		}
			ret = handKarten[k];
			for(int i = k; i < anzahlKarten-1; i++)
				handKarten[i] = handKarten[i+1];			
		anzahlKarten--;
		return ret;
	}

}
