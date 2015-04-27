package evolution.creatures.juries;

import configuration.FitnessConfiguration.JuryType;

public abstract class Jury
{
	/*
	 * virtual creatures are evolved by optimizing for a specific task or
	 * behavior. A creature is grown from its genetic description as previously
	 * explained, and then it is placed in a dynamically simulated virtual
	 * world. The brain provides effector forces which move parts of the
	 * creature, the sensors report aspects of the world and the creature’s body
	 * back to the brain, and the resulting physical behavior of the creature is
	 * evaluated. After a certain duration of virtual time (perhaps 10 seconds),
	 * a fitness value is assigned that corresponds to the success level of that
	 * behavior. If a creature has a high fitness relative to the rest of the
	 * population, it will be selected for survival and reproduction as
	 * described in the next section. Computation can be conserved for most
	 * fitness methods by discontinuing the simulations of individuals that are
	 * predicted to be unlikely to survive the next generation. The fitness is
	 * periodically estimated for each simulation as it proceeds. Those are
	 * stopped that are either not moving at all or are doing somewhat worse
	 * than the minimum fitness of the previously surviving individuals.
	 * 
	 * One direction of future work would be to experiment with additional types
	 * of fitness evaluation methods. More complex behaviors might be evolved by
	 * defining fitness functions that could measure the level of success at
	 * performing more difficult tasks, or even multiple tasks. Fitness could
	 * also include the efficiency at which a behavior was achieved. For
	 * example, a fitness measure might be the distance traveled divided by the
	 * amount of energy consumed to move that distance.
	 */

	public Jury(JuryType type, int weight)
	{
		this.juryType = type;
		this.weight = weight;
	}

	/**
	 * @return the weight
	 */
	public int getWeight()
	{
		return weight;
	}

	private int weight;

	public double getFitness()
	{
		return fitness;
	}

	protected double fitness;

	private JuryType juryType;

	public JuryType getJuryType()
	{
		return juryType;
	}

	/**
	 * Method is called at the end of the evaluation
	 */
	public abstract void evaluateFitness();

}
