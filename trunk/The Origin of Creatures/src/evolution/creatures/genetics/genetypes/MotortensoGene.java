package evolution.creatures.genetics.genetypes;

import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.genetics.Gene;

public class MotortensoGene extends Gene<MotortensoGene>
{

	public MotortensoGene()
	{
		super(GeneType.PROPRIOCEPTOGENE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MotortensoGene clone()
	{
		MotortensoGene newGene = new MotortensoGene();
		newGene.setLinkedGeneWeights(cloneLinkedGeneWeights());
		return newGene;
	}

}
