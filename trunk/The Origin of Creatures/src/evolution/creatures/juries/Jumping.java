package evolution.creatures.juries;

import configuration.FitnessConfiguration.JuryType;

public class Jumping extends Jury
{

	public Jumping(int weight)
	{
		super(JuryType.JUMPING, weight);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jumping behavior can be selected for by measuring the maximum height
	 * above the ground of the lowest part of the creature. An alternative
	 * method is to use the average height of the lowest part of the creature
	 * during the duration of simulation.
	 */

	@Override
	public void evaluateFitness()
	{
		// TODO Auto-generated method stub

	}

}
