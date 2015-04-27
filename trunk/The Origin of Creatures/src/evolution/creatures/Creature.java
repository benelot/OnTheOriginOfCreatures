package evolution.creatures;

import java.util.ArrayList;
import java.util.Comparator;

import evolution.Evolution;
import evolution.creatures.genetics.Genome;
import evolution.creatures.juries.Jury;
import evolution.creatures.juries.Velocity;
import evolution.creatures.morphology.Phenotype;

public class Creature
{

	/*
	 * Much work could be done to dress up these virtual creatures to give them
	 * different shapes and improved rendered looks. Flexible skin could
	 * surround or be controlled by the rigid components. Various materials
	 * could be added such as scales, hair, fur, eyes, or tentacles, and they
	 * might flow or bounce using simple local dynamic simulations, even if they
	 * did not influence the overall dynamics. The shape details and external
	 * materials could also be included in the creatures’ genetic descriptions
	 * and be determined by artificial
	 */

	public Creature()
	{
		developed = false;
		genotype = new Genome();
		genotype.createRandomGenome();
		genotype.expand();
		genotype.linkRandomGenes();
		juries = new ArrayList<>();
	}

	public Creature(Genome genotype)
	{
		this.genotype = genotype;
		this.genotype.expand();
		genotype.rewire();
		juries = new ArrayList<>();
	}

	public int generation;
	protected Genome genotype;

	protected Phenotype phenotype;

	public Genome getGenotype()
	{
		return genotype;
	}

	public void setGenotype(Genome genotype)
	{
		this.genotype = genotype;
	}

	public Phenotype getPhenotype()
	{
		return phenotype;
	}

	/**
	 * This method evaluates the fitness every time the physics is updated. This
	 * method should therefore be made lightweight.
	 */
	// public void evaluateFitness()
	// {
	// }

	/**
	 * This method evaluates the fitness at the end of the evaluation time. This
	 * method can be less lightweight than the evaluateFitness() method.
	 * 
	 */
	public void evaluateFinalFitness()
	{
		for (Jury jury : juries)
		{
			jury.evaluateFitness();
		}
		// iterate over juries and weight their fitness rating with their
		// weight.
		fitness = 0;
		double weights = 0;
		for (Jury jury : juries)
		{
			fitness += jury.getWeight() * jury.getFitness();
			weights += jury.getWeight();
		}
		if (weights != 0)
		{
			fitness = fitness / weights;
		}
	}

	public double getFitness()
	{
		return fitness;
	}

	private ArrayList<Jury> juries;

	public void addJury(Jury jury)
	{
		juries.add(jury);

	}

	public void update(float tpf)
	{
		for (Jury jury : juries)
		{
			switch (jury.getJuryType())
			{
			case FOLLOWING:
				// ((Velocity)jury).calculateAvgDistance(x,y,z,targetX,targetY,targetZ);;
				break;
			case JUMPING:
				break;
			case MOVING:
				break;
			case VELOCITY:
				((Velocity) jury).calculateAvgVelocity(x, y, z, tpf);
				break;
			default:
				break;

			}
		}

	}

	protected double x, y, z;
	protected boolean developed;

	public boolean isDeveloped()
	{
		return developed;
	}

	public void setDeveloped(boolean developed)
	{
		this.developed = developed;
	}

	public void print()
	{
		genotype.print();
		System.out.println();
		System.out.println("###############################");
		System.out.println();
		phenotype.print();
	}

	public static Comparator<Creature> CreatureFitnessComparator = new Comparator<Creature>()
	{

		public int compare(Creature creature1, Creature creature2)
		{
			// ascending order
			return (int) (1000000*(creature2.getFitness() - creature1.getFitness()));
		}

	};

	Evolution evolution;

	public void setEvolution(Evolution evolution)
	{
		this.evolution = evolution;
	}
	
	double fitness;
}
