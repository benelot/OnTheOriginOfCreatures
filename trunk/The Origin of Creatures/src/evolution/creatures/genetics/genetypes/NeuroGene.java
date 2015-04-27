package evolution.creatures.genetics.genetypes;

import configuration.GeneticsConfiguration.GeneType;
import configuration.NeuronalNetworkConfiguration.NeuroType;
import evolution.creatures.genetics.Gene;

public class NeuroGene extends Gene<NeuroGene>
{
	public NeuroGene(NeuroType neuronType)
	{
		super(GeneType.NEUROGENE);
		type = neuronType;
	}

	public NeuroType getType()
	{
		return type;
	}

	private NeuroType type;

	@Override
	public NeuroGene clone()
	{
		NeuroGene newGene = new NeuroGene(type);
		newGene.setLinkedGeneWeights(cloneLinkedGeneWeights());
		return newGene;
	}

}
