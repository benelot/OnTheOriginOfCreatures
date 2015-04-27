package evolution.creatures.genetics;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

import misc.logging.CreatureLog;
import misc.utils.Pair;
import configuration.FitnessConfiguration.JuryType;
import evolution.Evolution;
import evolution.creatures.Creature;
import evolution.creatures.juries.Following;
import evolution.creatures.juries.Jumping;
import evolution.creatures.juries.Moving;
import evolution.creatures.juries.Velocity;
import evolution.environments.Environment;

public class Population
{

	/*
	 * Alternatively, fitness could be defined in a more biologically realistic
	 * way by allowing populations of virtual creatures to compete against each
	 * other within a physically simulated changing world. Competition has been
	 * shown to facilitate complexity, specialization, or even social
	 * interactions [17,22]. It becomes difficult to define explicit evaluations
	 * that can select for an interesting behavior, but perhaps systems like
	 * these could help produce such results.
	 */

	@SuppressWarnings("unchecked")
	public <T extends Creature> Population(Evolution evolution,
			int populationSize, Class<T> creatureClass, Environment environment)
	{
		generation = 0;
		desiredSize = populationSize;
		creatures = new ArrayList<>();
		this.environment = environment;
		juries = new ArrayList<>();
		this.creatureClass = (Class<Creature>) creatureClass;
		initialize(evolution);
	}

	public Class<Creature> getCreatureClass()
	{
		return creatureClass;
	}

	Class<Creature> creatureClass;

	/**
	 * Returns the environment the population lives in.
	 * 
	 * @return The environment the population lives in.
	 */
	public Environment getEnvironment()
	{
		return environment;
	}

	/**
	 * Returns the desired size of the population.
	 * 
	 * @return The desired size of the population.
	 */
	public int getDesiredSize()
	{
		return desiredSize;
	}

	/**
	 * @param desiredSize
	 *            the desired size to set
	 */
	public void setDesiredSize(int desiredSize)
	{
		this.desiredSize = desiredSize;
	}

	/**
	 * Add a jury to measure the fitness of the population.
	 * 
	 * @param juryType
	 *            The type of jury that is added.
	 * @param weight
	 *            The weight of the jury's fitness value.
	 */
	public void addFitnessFunction(JuryType juryType, int weight)
	{
		juries.add(new Pair<JuryType, Integer>(juryType, weight));

	}

	/**
	 * Initialize the population by instantiating the creature class up to the
	 * desiredSize.
	 * 
	 * @param evolution
	 * 
	 * @param creatureClass
	 *            The class of creature in the population.
	 */
	public <T extends Creature> void initialize(Evolution evolution)
	{
		// reset the population
		creatures.clear();

		// instantiate the creatures
		for (int i = 0; i < desiredSize; i++)
		{
			// instantiate the creature
			addNewCreature(evolution);
		}

	}

	public void addCreature(Creature creature)
	{
		// add juries to the creature
		for (Pair<JuryType, Integer> pair : juries)
		{
			switch (pair.x)
			{
			case FOLLOWING:
				creature.addJury(new Following(pair.y));
				break;
			case JUMPING:
				creature.addJury(new Jumping(pair.y));
				break;
			case MOVING:
				creature.addJury(new Moving(pair.y));
				break;
			case VELOCITY:
				creature.addJury(new Velocity(pair.y));
				break;
			default:
				System.err.println("This jury is not yet implemented.");
				break;

			}
		}

		creatures.add(creature);
	}

	public void addNewCreature(Evolution evolution)
	{
		Creature creature = null;

		Class[] cArg = new Class[1]; // Our constructor has 3 arguments
		cArg[0] = Evolution.class; // First argument is of *object* type Long

		try
		{
			creature = creatureClass.getDeclaredConstructor(cArg).newInstance(evolution);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addCreature(creature);
	}

	/**
	 * Return the creatures that live in the population.
	 * 
	 * @return the creatures that live in the population.
	 */
	public ArrayList<Creature> getCreatures()
	{
		return creatures;
	}

	/**
	 * Returns the size of the population.
	 * 
	 * @return The size of the population.
	 */
	public int size()
	{
		return creatures.size();
	}

	/**
	 * Print the creatures of the population and provide some statistics.
	 */
	public void print()
	{
		minGenes = Integer.MAX_VALUE;
		maxGenes = 0;
		creatureGenesQty = 0;
		for (Creature creature : creatures)
		{
			minGenes = (minGenes > creature.getGenotype().size()) ? creature
					.getGenotype().size() : minGenes;
			maxGenes = (maxGenes < creature.getGenotype().size()) ? creature
					.getGenotype().size() : maxGenes;
			creatureGenesQty += creature.getGenotype().size();
			//creature.print();
			System.out.println("--------------------------");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("--------------------------");
		}
		avgGenes = creatureGenesQty / ((float) size());

		// add some statistics
		System.out.println("Population size: " + size());
		System.out.println("Minimum number of genes: " + minGenes);
		System.out.println("Average number of genes: " + avgGenes);
		System.out.println("Maximum number of genes: " + maxGenes);
		CreatureLog.info(String.valueOf(size()) + "\t" + avgGenes + "\t" + minGenes + "\t" +maxGenes);

	}

	private ArrayList<Pair<JuryType, Integer>> juries;
	private int desiredSize;
	private Environment environment;
	private ArrayList<Creature> creatures;

	// statistics
	int creatureGenesQty;
	int maxGenes;
	int minGenes;
	float avgGenes;

	public void sortByFitness()
	{
		Collections.sort(creatures, Creature.CreatureFitnessComparator);

	}

	public int getGeneration()
	{
		return generation;
	}
	
	public void incrementGeneration()
	{
		generation++;
	}
	
	int generation;

}
