package evolution.creatures.genetics;

import evolution.creatures.genetics.genetypes.ArticuloGene;
import evolution.creatures.genetics.genetypes.MorphoGene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;

import misc.utils.Randomness;
import configuration.GeneticsConfiguration;
import configuration.GeneticsConfiguration.GeneType;

public class Genome
{
	int unexpandedSize;
	boolean expanded;

	public Genome()
	{
		genes = new ArrayList<>();
		unexpandedSize = 0;
		expanded = false;
	}

	/*
	 * The genetic representation of this morphology is a directed graph of
	 * nodes and connections. Each graph contains the developmental instructions
	 * for growing a creature, and provides a way of reusing instructions to
	 * make similar or recursive components within the creature. A phenotype
	 * hierarchy of parts is made from a graph by starting at a defined
	 * root-node and synthesizing parts from the node information while tracing
	 * through the connections of the graph. The graph can be recurrent. Nodes
	 * can connect to themselves or in cycles to form recursive or fractal like
	 * structures. They can also connect to the same child multiple times to
	 * make duplicate instances of the same appendage.
	 */
	private MorphoGene morphoRoot;

	/**
	 * @return the morphoRoot
	 */
	public MorphoGene getMorphoRoot()
	{
		if(morphoRoot == null)
		{
			morphoRoot = (MorphoGene) genes.get(0);
		}
		return morphoRoot;
	}

	/**
	 * @param morphoRoot
	 *            the morphoRoot to set
	 */
	public void setMorphoRoot(MorphoGene morphoRoot)
	{
		this.morphoRoot = morphoRoot;
	}

	/**
	 * @return the genes
	 */
	public ArrayList<Gene<?>> getGenes()
	{
		return genes;
	}

	/**
	 * @param genes
	 *            the genes to set
	 */
	public void setGenes(ArrayList<Gene<?>> genes)
	{
		this.genes = genes;
	}

	private ArrayList<Gene<?>> genes;

	// TODO Subject of moving to Genetics
	public void createRandomGenome()
	{

		// reinitialize the whole genome
		genes = new ArrayList<>();

		// create at least one node by reinitializing the morphological root
		morphoRoot = new MorphoGene();
		genes.add(morphoRoot);

		// Add more genes with a probability GENOME_EXTENSION_PROBABILITY
		for (int i = 0; i < GeneticsConfiguration.GENOME_INITIAL_MAX_COMPONENTS; i++)
		{
			if (Randomness.nextDouble(0, 1) < GeneticsConfiguration.GENOME_EXTENSION_PROBABILITY)
			{
				addRandomGene();
			}
		}

	}

	// TODO Subject of moving to Genetics
	public void addRandomGene()
	{
		genes.add(Genetics.createRandomGene());
	}

	// TODO Subject of moving to Genetics
	public void linkRandomGenes()
	{
		// initiate random links
		for (int linkNumber = 0; linkNumber < GeneticsConfiguration.GENOME_INITIAL_MAX_LINKS; linkNumber++)
		{
			for (int geneIndex = 0; geneIndex < size(); geneIndex++)
			{
				if (Math.random() < GeneticsConfiguration.GENOME_INITIAL_CONNECTIONS_PROBABILITY)
				{
					linkGenes(
							geneIndex,
							Randomness.nextPosInt(0, size() - 1),
							Randomness
									.nextDouble(
											-GeneticsConfiguration.GENOME_INITIAL_LINK_WEIGHT_RANGE,
											GeneticsConfiguration.GENOME_INITIAL_LINK_WEIGHT_RANGE));
				}

			}
		}
	}

	// TODO Subject of moving to Genetics
	public void linkGenes(int geneIndex1, int geneIndex2, double linkWeight)
	{
		Gene<?> gene = genes.get(geneIndex1);
		if (gene == null)
		{
			return;
		}
		gene.getLinkedGeneWeights().put(geneIndex2, linkWeight);
	}

	// TODO Subject of moving to Genetics
	public void unlinkGenes(int geneIndex1, int geneIndex2)
	{
		Gene<?> gene = genes.get(geneIndex1);
		if (gene == null || gene.getLinkedGeneWeights().size() == 0)
		{
			return;
		}
		gene.getLinkedGeneWeights().remove(geneIndex2);
	}

	/**
	 * Links a random gene to another gene.
	 * 
	 * @param gene
	 */
	// TODO Subject of moving to Genetics
	public void linkRandomGeneTo(int geneIndex)
	{
		Gene<?> gene = genes.get(geneIndex);
		if (gene == null)
		{
			return;
		}
		gene.getLinkedGeneWeights()
				.put(Randomness.nextPosInt(0, size() - 1),
						Randomness
								.nextDouble(
										-GeneticsConfiguration.GENOME_INITIAL_LINK_WEIGHT_RANGE,
										GeneticsConfiguration.GENOME_INITIAL_LINK_WEIGHT_RANGE));
	}

	/**
	 * Links a random gene of a certain gene type to another gene.
	 * 
	 * @param gene
	 */
	// TODO Subject of moving to Genetics
	public void linkGeneTypeTo(int geneIndex, GeneType geneType)
	{
		Hashtable<Integer, Gene<?>> subset = new Hashtable<>();
		for (int i = 0; i < genes.size(); i++)
		{
			Gene<?> currentGene = genes.get(i);
			if (currentGene.getGeneType().equals(geneType))
			{
				subset.put(i, currentGene);
			}
		}
		Gene<?> gene = genes.get(geneIndex);
		if (gene == null || subset.isEmpty())
		{
			return;
		}
		gene.getLinkedGeneWeights()
				.put((Integer) subset.keySet().toArray()[Randomness.nextPosInt(
						0, subset.keySet().toArray().length - 1)],
						Randomness
								.nextDouble(
										-GeneticsConfiguration.GENOME_INITIAL_LINK_WEIGHT_RANGE,
										GeneticsConfiguration.GENOME_INITIAL_LINK_WEIGHT_RANGE));
	}

	/**
	 * Unlink a random gene from the gene.
	 * 
	 * @param geneIndex
	 *            The index of the gene to unlink.
	 */
	// TODO Subject of moving to Genetics
	public void unlinkRandomGeneFrom(int geneIndex)
	{
		Gene<?> gene = genes.get(geneIndex);
		if (gene == null || gene.getLinkedGeneWeights().size() == 0)
		{
			return;
		}
		gene.getLinkedGeneWeights()
				.remove(gene.getLinkedGeneWeights().keySet().toArray()[Randomness
						.nextPosInt(0, gene.getLinkedGeneWeights().size() - 1)]);
	}

	/**
	 * Print the genome into the console.
	 */
	public void print()
	{
		System.out.println("Genome:");
		for (int geneIndex = 0; geneIndex < genes.size(); geneIndex++)
		{
			Gene<?> gene = genes.get(geneIndex);
			System.out.print("|" + geneIndex);
			gene.print();
		}
		System.out.println("---------");
		System.out.println("Genome size: " + size());
	}

	/**
	 * Returns the size of the genome.
	 * 
	 * @return the size of the genome
	 */
	public int size()
	{
		return genes.size();
	}

	public void append(Genome mother, int motherStart, int motherEnd)
	{
		for (int i = motherStart; i < motherEnd; i++)
		{
			genes.add(mother.getGenes().get(i).clone());
		}

	}

	public void rewire()
	{
		boolean errorsFixed = false;
		if (!expanded)
		{
			expand();
		}

		for (int i = 0; i < genes.size(); i++)
		{
			Gene<?> gene = genes.get(i);
			HashMap<Integer, Double> newLinkedGeneWeights = new HashMap<>();

			for (Entry<Integer, Double> entry : gene.getLinkedGeneWeights()
					.entrySet())
			{
				int key = entry.getKey();
				double weight = entry.getValue();
				if (key >= genes.size())
				{
					key = key % genes.size();
					errorsFixed = true;
				}

				if (!gene.getLinkedGeneWeights().containsKey(key))
				{
					newLinkedGeneWeights.put(key, weight);
				}

			}
			gene.setLinkedGeneWeights(newLinkedGeneWeights);
		}
		System.out.println((errorsFixed) ? "Link errors found and fixed."
				: "No link errors found.");
	}

	public void compact()
	{
		if (expanded)
		{
			int expandedSize = genes.size();
			for (int i = expandedSize - 1; i >= unexpandedSize; i--)
			{
				genes.remove(i);
			}
			expanded = false;
		}
	}

	public void expand()
	{
		if (!expanded)
		{
			unexpandedSize = genes.size();
			for (int i = 0; i < unexpandedSize; i++)
			{
				Gene<?> gene = genes.get(i);
				switch (gene.getGeneType())
				{
				case ARTICULOGENE:
					genes.addAll(((ArticuloGene) gene).getAllMuscleComponents());
					break;
				/* The others do not need expansion */
				case EFFECTOGENE:
				case EXTEROCEPTOGENE:
				case MORPHOGENE:
				case MUSCULOGENE:
				case NEUROGENE:
				case PROPRIOCEPTOGENE:
					break;
				default:
					break;
				}

			}
			expanded = true;
		}
	}
}
