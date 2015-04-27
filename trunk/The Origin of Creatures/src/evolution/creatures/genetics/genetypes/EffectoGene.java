package evolution.creatures.genetics.genetypes;

import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.genetics.Gene;

public class EffectoGene extends Gene<EffectoGene>
{
	public EffectoGene(EffectoType effectorType)
	{
		super(GeneType.EFFECTOGENE);
		type = effectorType;

	}

	public enum EffectoType
	{
		SOUND;
	};

	EffectoType type;

	@Override
	public EffectoGene clone()
	{
		EffectoGene newGene = new EffectoGene(type);
		newGene.setLinkedGeneWeights(cloneLinkedGeneWeights());
		return newGene;
	}
}
