package evolution.creatures.juries;

import configuration.FitnessConfiguration.JuryType;

public class Following extends Jury
{

	public Following(int weight)
	{
		super(JuryType.FOLLOWING, weight);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Another evaluation method is used to select for creatures that can
	 * adaptively follow a light source. Photosensors are enabled, so the
	 * effector output forces and resulting behavior can depend on the rel-
	 * ative direction of a light source to the creature. Several trials are run
	 * with the light source in different locations, and the speeds at which a
	 * creature moves toward it are averaged for the fitness value. Following
	 * behaviors can be evolved for both water and land environments. Fleeing
	 * creatures can also be generated in a similar manner, or following
	 * behavior can be transformed into fleeing behavior by simply negating a
	 * creature’s photo sensor signals. Once creatures are found that exhibit
	 * successful following behaviors, they can be led around in arbitrary paths
	 * by movement of the light sources.
	 */

	@Override
	public void evaluateFitness()
	{
		// TODO Auto-generated method stub

	}

}
