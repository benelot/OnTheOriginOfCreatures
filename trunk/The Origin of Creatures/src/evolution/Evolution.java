package evolution;

import java.util.ArrayList;
import java.util.Iterator;

import configuration.EvolutionConfiguration.EvolutionState;
import misc.utils.EvaluationTimer;
import evolution.creatures.Creature;
import evolution.creatures.genetics.Population;
import evolution.environments.Environment;

/**
 * The evolution is the class that keeps all the components of the application
 * together, that proceeds in evaluating the creatures and initializes the
 * generations.
 * 
 * @author leviathan
 * 
 */
public abstract class Evolution
{

	public Evolution()
	{

		populations = new ArrayList<>();
		evaluationTimer = new EvaluationTimer();
		reaper = new Reaper(this);
	}

	/**
	 * Start the evolution.
	 */
	public abstract void start();

	/**
	 * Update the components of the evolution to represent the current evolution
	 * state.
	 */
	public abstract void update(float tpf);

	/**
	 * Proceed with the next creature of the currently tested population or
	 * proceed with reaping and sowing. This method is called by the
	 * EvaluationTimer after the predefined evaluation interval.
	 * 
	 * @throws Exception
	 */
	public abstract void proceed() throws Exception;

	/**
	 * Add a new population to the evolution.
	 * 
	 * @param population
	 *            The newly added population.
	 */
	public void addPopulation(Population population)
	{
		populations.add(population);
	}

	/**
	 * @return the reaper
	 */
	public Reaper getReaper()
	{
		return reaper;
	}

	public abstract void addToWorld(Creature creature);

	public abstract void removeFromWorld(Creature creature);

	/**
	 * The reaper of this evolution sows and reaps the creatures of the
	 * populations living in the world the evolution works in.
	 */
	protected Reaper reaper;

	protected ArrayList<Population> populations;

	protected EvaluationTimer evaluationTimer;

	protected Iterator<Population> populationIterator;
	protected Population currentPopulation;
	protected Iterator<Creature> creatureIterator;
	protected Creature currentCreature;

	public abstract void addEnvironment(Environment environment);

	public abstract void removeEnvironment(Environment environment);

	protected EvolutionState evolutionState;

}
