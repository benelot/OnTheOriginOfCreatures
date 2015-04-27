package evolution.creatures.genetics.genetypes;

import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.genetics.Gene;

public class ProprioceptoGene extends Gene<ProprioceptoGene>
{
	public ProprioceptoGene(ProprioceptoType proprioceptorType)
	{
		super(GeneType.PROPRIOCEPTOGENE);
		type = proprioceptorType;
	}

	public ProprioceptoType getType()
	{
		return type;
	}

	public enum ProprioceptoType
	{
		;
	}

	private ProprioceptoType type;

	@Override
	public ProprioceptoGene clone()
	{
		ProprioceptoGene newGene = new ProprioceptoGene(type);
		newGene.setLinkedGeneWeights(cloneLinkedGeneWeights());
		return newGene;
	}
}
