package evolution.creatures;

import com.jme3.math.Vector3f;

import evolution.Evolution;
import evolution.EvolutionjME3;
import evolution.creatures.genetics.Genome;
import evolution.creatures.morphology.PhenotypejME3;

public class CreaturejME3 extends Creature
{

	public CreaturejME3()
	{
		super();
	}
	
	public CreaturejME3(Evolution evolution)
	{
		super();
		this.evolution = evolution;

		phenotype = new PhenotypejME3(this, genotype);
	}

	public CreaturejME3(Evolution evolution, Genome genotype)
	{
		super(genotype);

		this.evolution = evolution;

		phenotype = new PhenotypejME3(this, genotype);
	}

	public CreaturejME3(Genome genotype)
	{
		super(genotype);

		phenotype = new PhenotypejME3(this, genotype);
	}
	

	@Override
	public void update(float tpf)
	{
		getPhenotype().update(tpf);
		updateCreaturePosition();
		super.update(tpf);
	}

	public void updateCreaturePosition()
	{
		Vector3f position = ((PhenotypejME3) phenotype).getBody()
				.getWorldTranslation();
		x = position.x;
		y = position.y;
		z = position.z;
	}
	
	public EvolutionjME3 getEvolution()
	{
		return (EvolutionjME3) evolution;
	}
}
