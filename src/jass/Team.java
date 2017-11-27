package jass;

public class Team 
{
	private TeamSpieler[] mitglieder;
	private int n = 0; //wieviele Mitglieder sind im team? nur wichtig zum initialisieren.
	private int maxMitglieder;
	private int teamPunkte = 0;
	private String name;

	Team(int maxMitglieder, String name)
	{
		this.maxMitglieder = maxMitglieder;
		mitglieder = new TeamSpieler[maxMitglieder];
		this.name = name;
	}
	
	public void addSpieler(TeamSpieler spieler)
	{
		if(n < this.maxMitglieder)
		{
			mitglieder[n] = spieler;
			n++;
		}
	}
	
	public TeamSpieler[] getMitglieder()
	{
		return mitglieder;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addPunkte(int p)
	{
		teamPunkte =+ p;
	}
	
	public int getPunkte()
	{
		return teamPunkte;
	}
}
