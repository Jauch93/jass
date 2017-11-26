package jass;

import java.io.IOException;

public class JassTurnier extends ASpiel
{
	private String trumpf;
	private int maxPunkte = 2000;
	private int offset = 0; //Gibt an, welcher Spieler als nächstes beginnen muss.
	private int rundenProMatch = 9;
	
	JassTurnier()
	{
		super(4,9);
	}
	
	JassTurnier(int anzahlSpieler, int handKarten, int maxP)
	{
		super(anzahlSpieler, handKarten);
		maxPunkte = maxP;
		rundenProMatch = handKarten;
	}

	public void supplyADeck() 
	{
		deck = new Deck();		
	}

	public void startTurnier() throws Exception 
	{
		this.resetTurnier();
		for(;;)
		{
			this.startMatch();
			
			if(this.getLeader().getPunkte() > maxPunkte)
				break;
		}
		System.out.println(this.getLeader().getName() + " hat das Turnier gewonnen!");
	}

	public void startMatch() throws Exception 
	{
		this.supplyADeck();
		this.verteileKarten();
		for(int i = 0; i < this.getAnzahlSpieler(); i++)
			spieler[i].sortiereKarten();
		this.setTrumpf();
		this.setKartenWerte();
		for(int i = 0; i < rundenProMatch; i++)			//Soviele Runden pro match, wie Karten auf der Hand.
		{
			this.startRunde();	
		}
	}

	private void setKartenWerte() 				//Hier werden die jeweiligen Trumpfregeln festgelegt.
	{
		switch(trumpf)
		{
			case "Schalle":
			case "Schilte":
			case "Rose":
			case "Eichle":
			for(int i = 0; i < this.getAnzahlSpieler(); i++)
			{
				Karte[] karten = spieler[i].showCards();
				for(int k = 0; k < karten.length; k++)
				{
					if(karten[k].getFarbe().equals(trumpf))
					{
						System.out.println("Trumpf gefunden!");
						
						if(karten[k].getName().equals("9"))			//handlet es sich um das Nell?
						{
							karten[k].setWertigkeit(9);
							karten[k].setName("Nell");
							karten[k].setPunkte(14);
						}
						if(karten[k].getName().equals("Under"))			//handelt es sich um den Buur?
						{
							karten[k].setWertigkeit(10);
							karten[k].setName("Buur");
							karten[k].setPunkte(20);
						}
						karten[k].setWertigkeit(karten[k].getWertigkeit() + 10);	//alle Trumpfkarten höher Werten als normale Karten.
					}
				}
			}
			break;
			case "UnneUfe":
				for(int i = 0; i < this.getAnzahlSpieler(); i++)
				{
					Karte[] karten = spieler[i].showCards();
					for(int k = 0; k < karten.length; k++)
					{						
						karten[k].setWertigkeit(10-karten[k].getWertigkeit());		//Invertierung der Werte
					}
				}
			case "ObeAbe":
				for(int i = 0; i < this.getAnzahlSpieler(); i++)
				{
					Karte[] karten = spieler[i].showCards();
					for(int k = 0; k < karten.length; k++)
					{						
							if(karten[k].getName().equals("8"))			//8er Im Wert raufsetzen.
							{
								karten[k].setPunkte(8);
							}
					}
				}
				break;
		}
	}

	public void startRunde() throws Exception 
	{
		Karte table[] = new Karte[this.getAnzahlSpieler()];
		int rundenPunkte = 0;
		int maxWert = 0;
		int rundenGewinner = 0;

		for(int i = offset; i < (offset + this.getAnzahlSpieler()); i++)
		{
			int k = i % this.getAnzahlSpieler();
			table[k] = spieler[k].playCard();
			rundenPunkte += table[k].getPunkte();
			if(i == offset)
				maxWert = table[k].getWertigkeit();
			if(i > offset)
			{
				if(table[k].getWertigkeit() > maxWert)
				{
					if(table[offset].getFarbe().equals(table[k].getFarbe()) || table[k].getFarbe().equals(trumpf))
					{
						rundenGewinner = k;
						maxWert = table[k].getWertigkeit();
					}
				}
			}
			System.out.println(spieler[k].getName() + " hat " + table[k].toString() + " gespielt.");
			System.out.println();
			
		}
		spieler[rundenGewinner].addPunkte(rundenPunkte);
		offset = rundenGewinner;
		System.out.println(spieler[rundenGewinner].getName() + " hat die Runde gewonnen. + " + rundenPunkte +  " Punkte.");
		
		
	}
	
	private void setTrumpf() throws IOException 
	{
		System.out.println("OFFSET = " + offset);
		this.trumpf = spieler[offset].setTrumpf();
	}
	
	public static void main(String[] args) throws Exception
	{
		JassTurnier test = new JassTurnier();
		test.startTurnier();
	}
}
