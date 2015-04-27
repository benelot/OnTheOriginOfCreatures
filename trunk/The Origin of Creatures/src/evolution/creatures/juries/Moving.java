package evolution.creatures.juries;

import configuration.FitnessConfiguration.JuryType;

public class Moving extends Jury
{

	public Moving(int weight)
	{
		super(JuryType.MOVING, weight);
		// TODO Auto-generated constructor stub
	}

	/*
	 * The term walking is used loosely here to indicate any form of land
	 * locomotion. A land environment is simulated by including gravity, turning
	 * off the viscous water effect, and adding a static ground plane with
	 * friction. Additional inanimate objects can be placed in the world for
	 * more complex environments. Again, speed is used as the selection
	 * criteria, but the vertical component of velocity is ignored. For land
	 * environments, it can be necessary to prevent creatures from generating
	 * high velocities by simply falling over. This is accomplished by first
	 * running the simulation with no friction and no effector forces until the
	 * height of the center of mass reaches a stable minimum.
	 */

	@Override
	public void evaluateFitness()
	{
		// TODO Auto-generated method stub

	}

}
