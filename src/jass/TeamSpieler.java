package jass;

public class TeamSpieler extends Spieler
{
	private int teamNr = 0;
	
	TeamSpieler(String n, int maxKarten, int teamNr)
	{
		super(n, maxKarten);
		this.teamNr = teamNr;
	}
	
	int getTeamNr()
	{
		return teamNr;
	}
	
}
