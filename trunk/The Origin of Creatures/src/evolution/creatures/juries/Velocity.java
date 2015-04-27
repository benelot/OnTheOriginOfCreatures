package evolution.creatures.juries;

import configuration.FitnessConfiguration.JuryType;

public class Velocity extends Jury
{

	/*
	 * This jury evaluates the velocity of movement of the creature. It simply
	 * receives the current position of the creature, calculates the difference
	 * to the last position and divides it by the time that has passed between
	 * the measurements. Then it calculates the continuous average of the
	 * velocity.
	 */

	public Velocity(int weight)
	{
		super(JuryType.VELOCITY, weight);
		firstTime = true;
	}

	// the continuously called function
	public void calculateAvgVelocity(double x2, double y2, double z2,
			float diffTime)
	{
		if (!firstTime)
		{
			double distance = Math.sqrt(Math.pow(x2 - this.x, 2)
					+ Math.pow(y2 - this.y, 2) + Math.pow(z2 - this.z, 2));

			// continuous average avg = oldAvg * sampleQty +
			// velocity/(sampleQty+1)
			avgVelocity = (avgVelocity * sampleQty + distance
					/ ((double) diffTime))
					/ (sampleQty + 1);
			sampleQty++;
		}

		// update position
		this.x = x2;
		this.y = y2;
		this.z = z2;
		firstTime = false;
	}

	double avgVelocity;

	double x, y, z;
	float timestamp;
	int sampleQty;
	boolean firstTime;

	@Override
	public void evaluateFitness()
	{
		if (Double.isNaN(avgVelocity))
		{
			fitness = 0;
		} else
		{
			fitness = avgVelocity;
		}
		System.out.println(fitness);
	}

}
