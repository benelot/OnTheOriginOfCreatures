package evolution.creatures.genetics.genetypes;

import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.genetics.Gene;

public class MotorceptoGene extends Gene<MotorceptoGene>
{

	public MotorceptoGene()
	{
		super(GeneType.PROPRIOCEPTOGENE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MotorceptoGene clone()
	{
		MotorceptoGene newGene = new MotorceptoGene();
		newGene.setLinkedGeneWeights(cloneLinkedGeneWeights());
		return newGene;
	}

}
