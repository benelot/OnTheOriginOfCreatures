package evolution.creatures.genetics;

public class Reproduction
{
	/*
	 * An evolution of virtual creatures is begun by first creating an initial
	 * population of genotypes. These initial genotypes can come from several
	 * possible sources: new genotypes can be synthesized “from scratch” by
	 * random generation of sets of nodes and connections, an existing genotype
	 * from a previous evolution can be used to seed the initial population of a
	 * new evolution, or a seed genotype can be designed by hand. A
	 * survival-ratio determines the percentage of the population that will
	 * survive each generation. In this work, population sizes were typically
	 * 300, and the survival ratio was 1/5. If the initially generated
	 * population has fewer individuals with positive fitness than the number
	 * that should survive, another round of seed genotypes is generated to
	 * replace those with zero fitness. For each generation, creatures are grown
	 * from their genetic descriptions, and their fitness values are measured by
	 * a method such as those described in the previous section. The individuals
	 * whose fitnesses fall within the survival percentile are then reproduced,
	 * and their offspring fill the slots of those individuals that did not
	 * survive. The survivors are kept in the population for the next
	 * generation, and the total size of the population is maintained. The
	 * number of offspring that each surviving individual generates is
	 * proportional to its fitness – the most successful creatures make the most
	 * children. Offspring are generated from the surviving creatures by copy-
	 * ing and combining their directed graph genotypes. When these graphs are
	 * reproduced they are subjected to probabilistic variation or mutation, so
	 * the corresponding phenotypes are similar to their parents but have been
	 * altered or adjusted in random ways.
	 */

	public Genome reproduce(Genome parent1, Genome parent2)
	{
		return parent1;
	}

	public Genome cross(Genome parent1, Genome parent2, int startP1, int endP1,
			int startP2, int endP2, boolean copyLinks)
	{
		return parent2;

	}

	public Genome mutateGene(Genome genome, int genePosition)
	{
		return genome;
	}

	public Genome mutateLink(Genome genome, int geneIndex, int linkIndex)
	{

		return genome;
	}

	/**
	 * Grafting copies a feature of the donator over to the receiver beginning
	 * at the indicated gene by copying the gene and its directed subgraph over,
	 * by following the links up to the maximum link depth and by copying all
	 * the genes that are met on the way. Then the newly added feature is
	 * connected randomly to the other genes to integrate it.
	 * 
	 * @param receiver
	 *            receives the new feature donated by the donator
	 * @param donator
	 *            donates the new feature
	 * @param maxLinkDepth
	 *            the maximum link depth to follow
	 * @return returns the receiver genotype with the new feature added.
	 */
	public Genome graftFeature(Genome receiver, Genome donator, int geneIndex,
			int maxLinkDepth)
	{
		return receiver;
	}

}
