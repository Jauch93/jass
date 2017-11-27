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
	}
	
	public void startTurnier() throws Exception 
	{
		startAbstractTurnier();
		System.out.println(this.getLeader().getName() + " hat das Turnier gewonnen!");
	}


	public void startRunde(int roundNumber) throws Exception 
	{
		int rundenPunkte = startAbstractRunde(roundNumber);
		System.out.println(spieler[offset].getName() + " hat die Runde gewonnen. + " + rundenPunkte +  " Punkte.");		
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
