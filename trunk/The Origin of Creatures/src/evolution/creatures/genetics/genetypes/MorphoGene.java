package evolution.creatures.genetics.genetypes;

import misc.utils.Randomness;
import configuration.CreatureConfiguration;
import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.genetics.Gene;

public class MorphoGene extends Gene<MorphoGene>
{

	/*
	 * Each node in the graph contains information describing a rigid part. The
	 * dimensions determine the physical shape of the part. A recursive-limit
	 * parameter determines how many times this node should generate a phenotype
	 * part when in a recursive cycle. A set of local neurons is also included
	 * in each node, and will be explained further in the next section. Finally,
	 * a node contains a set of connections to other nodes.
	 */

	public MorphoGene()
	{
		super(GeneType.MORPHOGENE);
		setDimensions(Randomness.nextDouble(CreatureConfiguration.CREATURE_BODYPART_MINSIZE,
				CreatureConfiguration.CREATURE_BODYPART_MAXSIZE),
				Randomness.nextDouble(CreatureConfiguration.CREATURE_BODYPART_MINSIZE,
						CreatureConfiguration.CREATURE_BODYPART_MAXSIZE),
				Randomness.nextDouble(CreatureConfiguration.CREATURE_BODYPART_MINSIZE,
						CreatureConfiguration.CREATURE_BODYPART_MAXSIZE));
	}

	/**
	 * Length of the body segment
	 */
	private double length;

	/**
	 * Width of the body segment
	 */
	private double width;

	/**
	 * Height of the body segment.
	 */
	private double height;

	public double getLength()
	{
		return length;
	}

	public void setLength(double length)
	{
		this.length = length;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public void setDimensions(double length, double width, double height)
	{
		this.length = length;
		this.width = width;
		this.height = height;
	}

	@Override
	public MorphoGene clone()
	{
		MorphoGene newGene = new MorphoGene();
		newGene.width = width;
		newGene.length = length;
		newGene.height = height;
		newGene.setLinkedGeneWeights(cloneLinkedGeneWeights());
		return newGene;
	}
}
