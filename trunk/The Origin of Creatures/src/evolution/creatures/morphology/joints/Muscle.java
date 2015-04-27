package evolution.creatures.morphology.joints;

import java.util.Map.Entry;

import configuration.GeneticsConfiguration.GeneType;
import configuration.MuscleConfiguration;
import evolution.creatures.Creature;
import evolution.creatures.morphology.Component;
import evolution.creatures.morphology.sensors.proprioceptors.Motorceptor;

public class Muscle extends Component
{

	// Hill's muscle model (v+b)(F+a) = b(F_0 + a)
	// v is the velocity of contraction
	// F is the current tension or load of the muscle

	// b = a*v_0/F_0
	double b_coefficient;

	// a is a coefficient shortening heat
	double shorteningHeatCoefficient;

	private double position;
	private double minLimit;
	private double maxLimit;

	// v_0 is the maximum velocity when F = 0
	private double maxVelocity;

	// F_0 is the maximum isometric tension or load
	// generated in the muscle when v = 0
	private double maxForce;
	private Motorceptor motorceptor;

	public Muscle(Creature creature,int componentIndex, double minLimit, double maxLimit,
			double maxForce)
	{
		super(creature, GeneType.MUSCULOGENE,componentIndex);
		this.motorceptor = new Motorceptor(creature, componentIndex+1);
		setPosition(0);
		this.setMinLimit(minLimit);
		this.setMaxLimit(maxLimit);
		this.setMaxForce(maxForce);
		maxVelocity = MuscleConfiguration.MUSCLE_MAX_SPEED;
		shorteningHeatCoefficient = MuscleConfiguration.SHORTENING_HEAT_COEFFICIENT;
		b_coefficient = shorteningHeatCoefficient * maxVelocity / maxForce;
	}

	public double getForceFromVelocity(double velocity)
	{
		return b_coefficient * (maxForce + shorteningHeatCoefficient)
				/ (velocity + b_coefficient) - shorteningHeatCoefficient;

	}

	public double getVelocityFromForce(double force)
	{
		return b_coefficient * (maxForce + shorteningHeatCoefficient)
				/ (force + shorteningHeatCoefficient) - b_coefficient;

	}

	public void setForce(double force)
	{
		force = (force > getMaxForce()) ? getMaxForce() : force;
		force = (force < 0) ? 0 : force;
		this.force = force;
	}

	double force;

	public double getForce()
	{
		return force;
	}

	public double getPosition()
	{
		return position;
	}

	public void setPosition(double position)
	{
		if (position < minLimit)
		{
			position = minLimit;
		}
		if (position > maxLimit)
		{
			position = maxLimit;
		}
		motorceptor.setPosition(position);
		this.position = position;
	}

	public double getMinLimit()
	{
		return minLimit;
	}

	public void setMinLimit(double minLimit)
	{
		this.minLimit = minLimit;
	}

	public double getMaxLimit()
	{
		return maxLimit;
	}

	public void setMaxLimit(double maxLimit)
	{
		this.maxLimit = maxLimit;
	}

	/**
	 * @return the maxForce
	 */
	public double getMaxForce()
	{
		return maxForce;
	}

	/**
	 * @param maxForce
	 *            the maxForce to set
	 */
	public void setMaxForce(double maxForce)
	{
		this.maxForce = maxForce;
	}

	public Motorceptor getMotorceptor()
	{
		return motorceptor;
	}

	@Override
	public void update(double tpf)
	{
		double sum = 0;
		for(Entry<Integer, Double> dendriteConnection:getDendrite().entrySet())
		{
			sum += dendriteConnection.getValue();
		}
		setForce(force+sum);
		//System.out.println("Spike sum " + sum);
		//System.out.println("Dendrite size " + getDendrite().entrySet().size());
		

	}

	public double getVelocity()
	{
		return getVelocityFromForce(getForce());
	}
}
