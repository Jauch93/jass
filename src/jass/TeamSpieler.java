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
	
	public TeamSpieler getPartner()
	{
		return null;
		//@TODO Implement!
	}
}
