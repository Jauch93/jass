package jass;

import java.io.IOException;

public class SchieberTurnier extends AJass
{
	private Team[] team = new Team[2]; // zwei Teams initialisieren
	
	SchieberTurnier()
	{
		super(4, 9, true, 2000);
		int anzahlSpieler = 4;
		int handKarten = 9;
		for(int i = 0; i < team.length; i++)
			team[i] = new Team(2, ("Team " + (i+1)));
		for(int i = 0; i < anzahlSpieler; i++)
		{
			String name = ("Spieler " + (i+1));
			int teamNr = i%2;
			spieler[i] = new TeamSpieler(name, handKarten, teamNr);
			team[teamNr].addSpieler(((TeamSpieler)spieler[i]));
		}
	}

	public void startTurnier() throws Exception 
	{
		startAbstractTurnier();
		System.out.println(this.getLeaderTeam().getName() + " hat das Turnier gewonnen!");
	}

	public void startMatch() throws Exception 
	{
		startAbstractMatch();	
	}

	public void startRunde(int roundNumber) throws Exception 
	{
		int rundenPunkte = startAbstractRunde(roundNumber);
		Team winnerTeam = team[((TeamSpieler)spieler[offset]).getTeamNr()];
		System.out.println(winnerTeam.getName() + " hat die Runde gewonnen.  + " + rundenPunkte + " Punkte.");
		winnerTeam.addPunkte(rundenPunkte);
	}

	public void setTrumpf() throws IOException 
	{
		int t = ((JassSpieler)spieler[offset]).setTrumpf(true, this.getTrumpfArten());
		if(t == -1)
		{
			System.out.println(spieler[offset].getName() + " hat Geschoben!");
			t = ((JassSpieler)spieler[(offset%team.length)+2]).setTrumpf(false, this.getTrumpfArten());
		}
		trumpf = t;
	}
	
	public static void main(String[] args) throws Exception
	{
		SchieberTurnier test = new SchieberTurnier();
		test.startTurnier();
	}

	public Team getLeaderTeam()
	{
		int punkte = 0;
		int winner = 0;
		for(int i = 0; i < team.length; i++)
		{
			if(team[i].getPunkte() > punkte)
			{
				punkte = team[i].getPunkte();
				winner = i;
			}
		}
		return team[winner];		
	}

}
