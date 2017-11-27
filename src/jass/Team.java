package jass;

public class Team 
{
	private JassSpieler[] teamMitglieder;
	private int teamPunkte;
	private String name;

	Team(String n, JassSpieler[] mitglieder)
	{
		name = n;
		teamMitglieder = new JassSpieler[mitglieder.length];
		for(int i = 0; i < mitglieder.length; i++)
			teamMitglieder[i] = mitglieder[i];
		System.out.print(getName() + " beinhaltet ");
		for(int i = 0; i < mitglieder.length; i++)
			System.out.print(teamMitglieder[i].getName() + ", ");
		System.out.println();
	}
	
	public void calcTeamPunkte()
	{
		teamPunkte = 0;
		for(int i = 0; i < teamMitglieder.length; i++)
		{
			teamPunkte += teamMitglieder[i].getPunkte();
		}
	}
	
	public int getPunkte()
	{
		calcTeamPunkte();
		return teamPunkte;
	}
	
	public String getName()
	{
		return name;
	}
}
