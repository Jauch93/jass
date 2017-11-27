package jass;

import java.io.IOException;

public class SchieberTurnier extends JassTurnier
{
	private Team[] team;
	
	SchieberTurnier()
	{
		super();
		team = new Team[super.getAnzahlSpieler()/2];
		for(int i = 0; i < super.getAnzahlSpieler()/2; i++)
		{
			JassSpieler[] mitglieder = new JassSpieler[super.getAnzahlSpieler()/2];
			mitglieder[0] = (JassSpieler) spieler[i];
			mitglieder[1] = (JassSpieler) spieler[i+2];
			
			team[i] = new Team(("Team " + (i+1)), mitglieder);
		}
	}
	
	public void startTurnier() throws Exception
	{
		this.resetTurnier();
		for(;;)
		{
			this.startMatch();
			
			if(this.getLeaderTeam().getPunkte() > maxPunkte)
				break;
		}
		System.out.println(this.getLeaderTeam().getName() + " hat das Turnier gewonnen!");
	}
	
	public void printPunkte()
	{
		for(int i = 0; i < team.length;i ++)
		{
			System.out.println(team[i].getName() + " hat " + team[i].getPunkte() + " Punkte.");
		}
	}
	
	public Team getLeaderTeam()
	{
		int maxPunkte = 0;
		int GewinnerTeam = 0;
		for(int i = 0; i < team.length; i++)
		{
			if(team[i].getPunkte() > maxPunkte)
				GewinnerTeam = i;
		}
		return team[GewinnerTeam];
	}
	
	@Override
	public void setTrumpf() throws IOException 
	{
		int t = ((JassSpieler)spieler[offset]).setTrumpf(true);
		if(t == -1)
		{
			System.out.println(spieler[offset].getName() + " hat Geschoben!");
			t = ((JassSpieler)spieler[(offset%team.length)+2]).setTrumpf(false);
		}
		trumpf = t;
	}
	
	public static void main(String[] args) throws Exception
	{
		SchieberTurnier test = new SchieberTurnier();
		test.startTurnier();
	}
}
