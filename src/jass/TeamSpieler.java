package jass;

public class TeamSpieler extends JassSpieler 
{
	private int teamNr;
	
	TeamSpieler(String n, int maxKarten, int teamNr) {
		super(n, maxKarten);
		this.teamNr = teamNr;
	}

	public int getTeamNr()
	{
		return teamNr;
	}
	
	public TeamSpieler getPartner(Team team)
	{
		if((team.getMitglieder())[0].getName().equals(this.getName()))
			return (team.getMitglieder())[1];
		else 
			return (team.getMitglieder())[0];
	}
}
