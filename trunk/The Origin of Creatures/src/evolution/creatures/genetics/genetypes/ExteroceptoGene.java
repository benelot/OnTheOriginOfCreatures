package evolution.creatures.genetics.genetypes;

import configuration.GeneticsConfiguration.ExteroceptoType;
import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.genetics.Gene;

public class ExteroceptoGene extends Gene<ExteroceptoGene>
{
	public ExteroceptoGene(ExteroceptoType exteroceptorType)
	{
		super(GeneType.EXTEROCEPTOGENE);
		type = exteroceptorType;
	}

	public ExteroceptoType getType()
	{
		return type;
	}

	private ExteroceptoType type;

	@Override
	public ExteroceptoGene clone()
	{
		ExteroceptoGene newGene = new ExteroceptoGene(getType());
		newGene.setLinkedGeneWeights(cloneLinkedGeneWeights());
		return newGene;
	}
}
