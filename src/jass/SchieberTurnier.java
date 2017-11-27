package jass;

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
	
	public static void main(String[] args)
	{
		SchieberTurnier test = new SchieberTurnier();
	}
}
