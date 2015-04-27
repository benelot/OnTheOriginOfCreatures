package evolution;

import java.util.ArrayList;

import misc.logging.CreatureLog;
import misc.utils.Randomness;
import configuration.EvolutionConfiguration;
import evolution.creatures.Creature;
import evolution.creatures.CreaturejME3;
import evolution.creatures.genetics.Genetics;
import evolution.creatures.genetics.Genome;
import evolution.creatures.genetics.Population;

public class Reaper
{
	/*
	 * Before creatures are simulated for fitness evaluation, some simple
	 * viability checks are performed, and inappropriate creatures are removed
	 * from the population by giving them zero fitness values. Those that have
	 * more than a specified number of parts are removed. A subset of genotypes
	 * will generate creatures whose parts initially interpenetrate. A short
	 * simulation with collision detection and response attempts to repel any
	 * intersecting parts, but those creatures with persistent interpenetrations
	 * are also discarded.
	 */

	Reaper(Evolution evolution)
	{
		// Keep a reference to the evolution.
		this.evolution = evolution;
	}

	/**
	 * A reference to the evolution the reaper belongs to.
	 */
	Evolution evolution;

	public <T extends Creature> void initPopulations(
			ArrayList<Population> populations)
	{
		for (Population population : populations)
		{
			population.initialize(evolution);
		}
	}

	public void reap(Population population)
	{
		// select all creatures to be not developed
		for (Creature creature : population.getCreatures())
		{
			creature.setDeveloped(false);

		}

		int headsToReap = (int) Math.ceil(population.getCreatures().size()
				* EvolutionConfiguration.REAPER_REAP_PERCENTAGE);
		for (int i = 0; i < headsToReap; i++)
		{
			// size() returns the creature with the lowest score. We remove
			// the worst creatures until all heads to reap are off.
			population.getCreatures().remove(
					population.getCreatures().size() - 1);
		}

	}

	public void sow(Population population)
	{
		int headsToSow = population.getDesiredSize() - population.size();
		int sown = 0;

		// #############
		// crossover
		// #############

		// calculate the number of offsprings for each ancestor
		int ancestorIndex = 0;
		ArrayList<Integer> offspringQts = new ArrayList<>();
		ArrayList<Creature> ancestors = new ArrayList<>();
		int offsprings = 0;
		for (int i = 0; i < EvolutionConfiguration.REAPER_SOW_OFFSPRING.length; i++)
		{
			double offspringPerc = EvolutionConfiguration.REAPER_SOW_OFFSPRING[i];
			int offspringQty = (int) (Math.ceil(offspringPerc * headsToSow
					* EvolutionConfiguration.REAPER_CROSSOVER_PERCENTAGE));

			// add number of offsprings for each ancestor
			if (offsprings + offspringQty < headsToSow)
			{
				offspringQts.add(offspringQty);
				offsprings += offspringQty;
			} else
			{
				// if we (nearly) reached the number heads to sow we add the
				// remaining heads
				offspringQts.add(headsToSow - offsprings);
				offsprings = headsToSow;
			}
			Creature creature = population.getCreatures().get(0);
			ancestors.add(creature);
			// remove creature from top
			population.getCreatures().remove(0);

			// and add to the end of the list
			population.addCreature(creature);
			creature.generation++;
		}

		// as long as we did not already generate all offspring or all heads to
		// be sown
		while (ancestorIndex != offspringQts.size() && sown < headsToSow)
		{
			if (offspringQts.get(ancestorIndex) != 0)
			{
				Creature mate1 = ancestors.get(ancestorIndex);
				Creature mate2 = ancestors.get(Randomness.nextPosInt(0,
						ancestors.size() - 1));
				Genome genotype = Genetics.performCrossOver(
						mate1.getGenotype(), mate2.getGenotype());

				if (genotype.size() > 1)
				{
					for (int i = 0; i < EvolutionConfiguration.REAPER_GENE_MUTATION_QTY; i++)
					{
						Genetics.performGeneMutation(genotype);
					}
				}
				for (int i = 0; i < EvolutionConfiguration.REAPER_GENE_MUTATION_QTY; i++)
				{
					Genetics.performAttributeMutation(genotype);
				}
				for (int i = 0; i < EvolutionConfiguration.REAPER_LINK_MUTATION_QTY; i++)
				{
					Genetics.performLinkMutation(genotype);
				}

				CreaturejME3 creature = new CreaturejME3(evolution, genotype);
				population.addCreature(creature);
				offspringQts.set(ancestorIndex,
						offspringQts.get(ancestorIndex) - 1);
				sown++;
			} else
			{
				ancestorIndex++;
			}
		}

		// gene mutation
		int geneMutationHeads = (int) (headsToSow * EvolutionConfiguration.REAPER_GENE_MUTATION_PERCENTAGE);
		for (int i = 0; i < geneMutationHeads; i++)
		{
			if (sown == headsToSow)
			{
				break;
			}

			Creature ancestor = population.getCreatures().get(0);

			// remove creature from top
			population.getCreatures().remove(0);

			// and add to the end of the list
			population.getCreatures().add(ancestor);

			Genome genotype = new Genome();
			genotype.append(ancestor.getGenotype(), 0, ancestor.getGenotype()
					.size());

			// Gene mutations
			for (int j = 0; j < EvolutionConfiguration.REAPER_GENE_MUTATION_QTY; j++)
			{
				Genetics.performGeneMutation(genotype);
			}

			// Attribute mutations
			for (int j = 0; j < EvolutionConfiguration.REAPER_ATTRIBUTE_MUTATION_QTY; j++)
			{
				Genetics.performAttributeMutation(genotype);
			}

			for (int j = 0; j < EvolutionConfiguration.REAPER_LINK_MUTATION_QTY; j++)
			{
				Genetics.performLinkMutation(genotype);
			}
			Creature creature = new CreaturejME3(genotype);
			population.addCreature(creature);
			sown++;
		}

		// attribute mutation
		int attributeMutationHeads = (int) (headsToSow * EvolutionConfiguration.REAPER_ATTRIBUTE_MUTATION_PERCENTAGE);
		for (int i = 0; i < attributeMutationHeads; i++)
		{
			if (sown == headsToSow)
			{
				break;
			}

			Creature ancestor = population.getCreatures().get(0);

			// remove creature from top
			population.getCreatures().remove(0);

			// and add to the end of the list
			population.addCreature(ancestor);

			Genome genotype = new Genome();
			genotype.append(ancestor.getGenotype(), 0, ancestor.getGenotype()
					.size());

			// Attribute mutations
			for (int j = 0; j < EvolutionConfiguration.REAPER_ATTRIBUTE_MUTATION_QTY; j++)
			{
				Genetics.performAttributeMutation(genotype);
			}

			for (int j = 0; j < EvolutionConfiguration.REAPER_LINK_MUTATION_QTY; j++)
			{
				Genetics.performLinkMutation(genotype);
			}
			Creature creature = new CreaturejME3(genotype);
			population.addCreature(creature);
			sown++;
		}

		while (sown < headsToSow)
		{
			population.addNewCreature(evolution);
		}

		// recreate the creature from the last generation to the next
		for (Creature creature : population.getCreatures())
		{
			if (!creature.isDeveloped())
			{
				creature.getGenotype().rewire();
				creature.getPhenotype().buildPhenotypeFromGenotype(
						creature.getGenotype());
			}

		}

	}

	public void evolvePopulations(ArrayList<Population> populations)
	{

		for (Population population : populations)
		{
			// sort population by the fitness
			population.sortByFitness();

			CreatureLog.info(population.getGeneration() + "\t");
			population.incrementGeneration();
			double i = 0;
			double fitness = 0;
			for (Creature creature : population.getCreatures())
			{
				i++;
				fitness += creature.getFitness();
				CreatureLog.info(creature.getFitness() + "\t");
			}

			CreatureLog.info((fitness / i) + "\t");
			//CreatureLog.info("-----------------------------");
			population.print();
			//CreatureLog.info("-----------------------------");
			
			CreatureLog.info("\r\n");

			reap(population);
			sow(population);

		}
	}
}
