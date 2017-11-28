package jass;

import java.io.IOException;

public class JassTurnier extends AJass
{		
	JassTurnier(int anzahlSpieler, int handKarten, boolean deutschSchweizerSet, int maxPunkte)
	{
		super(anzahlSpieler, handKarten, deutschSchweizerSet, maxPunkte);
	}
	
	JassTurnier()		//*Normales JassSpiel mit DeutschweizerKarten
	{
		super(4, 9, true, 2000);
		int handKarten = 9;
		int anzahlSpieler = 4;
		for(int i = 0; i < anzahlSpieler; i++)
		{
			String name = ("Spieler " + (i+1));
			spieler[i] = new JassSpieler(name, handKarten);
		}
	}
	
	public void startTurnier() throws Exception 
	{
		startAbstractTurnier();
		ASpiel.printString(this.getLeader().getName() + " hat das Turnier gewonnen!");
	}
	
	public void startMatch() throws Exception
	{
		startAbstractMatch();
	}

	public void startRunde(int roundNumber) throws Exception 
	{
		int rundenPunkte = startAbstractRunde(roundNumber);
		ASpiel.printString(spieler[offset].getName() + " hat die Runde gewonnen. + " + rundenPunkte +  " Punkte.");		
		spieler[offset].addPunkte(rundenPunkte);
		ASpiel.printString("----------------------------------------------------------");
		ASpiel.printString("");
	}
	
	public void setTrumpf() throws IOException 
	{
		this.trumpf = ((JassSpieler)spieler[offset]).setTrumpf(false, this.getTrumpfArten());
	}
	
	public static void main(String[] args) throws Exception
	{
		JassTurnier test = new JassTurnier();
		test.startTurnier();
	}
}
