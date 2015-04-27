package evolution.creatures.genetics.genetypes;

import misc.utils.Randomness;
import configuration.GeneticsConfiguration;
import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.genetics.Gene;

public class MusculoGene extends Gene<MusculoGene>
{

	public MusculoGene()
	{
		super(GeneType.MUSCULOGENE);

		// TODO: Double.MAX_VALUE might be a weird upper limit depending on how
		// the joint will be implemented

		// these are the muscle movement limits which determine the movement
		// limits of the joint. If the minimum limit is greater than the maximum
		// limit, the joint is locked, and can not be moved at all.
		this.minLimit = Randomness.nextDouble(0, Double.MAX_VALUE);
		this.maxLimit = Randomness.nextDouble(0, Double.MAX_VALUE);
		this.maxForce = Randomness.nextDouble(0,
				GeneticsConfiguration.GENOME_MUSCULOGENE_MAX_FORCE);
		this.motorceptoGene = new MotorceptoGene();
		// this.motortensoGene = new MotortensoGene();
	}

	public void setMaxForce(double maxForce)
	{
		this.maxForce = maxForce;
	}

	public void setMotorceptoGene(MotorceptoGene motorceptoGene)
	{
		this.motorceptoGene = motorceptoGene;
	}

	public void setMinLimit(double minLimit)
	{
		this.minLimit = minLimit;
	}

	public void setMaxLimit(double maxLimit)
	{
		this.maxLimit = maxLimit;
	}

	double maxForce;

	// private MotortensoGene motortensoGene;
	private MotorceptoGene motorceptoGene;

	double minLimit;
	double maxLimit;

	// /**
	// * @return the motortensoGene
	// */
	// public MotortensoGene getMotortensoGene() {
	// return motortensoGene;
	// }
	/**
	 * @return the motorceptoGene
	 */
	public MotorceptoGene getMotorceptoGene()
	{
		return motorceptoGene;
	}

	/**
	 * @return the maxForce
	 */
	public double getMaxForce()
	{
		return maxForce;
	}

	/**
	 * @return the minLimit
	 */
	public double getMinLimit()
	{
		return minLimit;
	}

	/**
	 * @return the maxLimit
	 */
	public double getMaxLimit()
	{
		return maxLimit;
	}

	@Override
	public MusculoGene clone()
	{
		MusculoGene newGene = new MusculoGene();
		newGene.setMaxForce(maxForce);
		newGene.setMinLimit(minLimit);
		newGene.setMaxLimit(maxLimit);
		// newGene.motortensoGene = motortensoGene.clone();
		newGene.motorceptoGene = motorceptoGene.clone();
		newGene.setLinkedGeneWeights(cloneLinkedGeneWeights());
		return newGene;
	}

}
