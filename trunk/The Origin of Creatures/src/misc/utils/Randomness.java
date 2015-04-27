package misc.utils;

import java.util.Random;

public class Randomness
{
	public static Random random;

	public static double nextDouble(double lowerLimit, double upperLimit)
	{
		if (random == null)
		{
			random = new Random();
		}
		return lowerLimit + (random.nextDouble() * (upperLimit - lowerLimit));
	}

	/**
	 * This method returns a random integer between and including the limits.
	 * 
	 * @param lowerLimit
	 * @param upperLimit
	 * @return
	 */
	public static int nextPosInt(int lowerLimit, int upperLimit)
	{
		if (random == null)
		{
			random = new Random();
		}
		if (upperLimit == 0)
		{
			return 0;
		}
		// 1+ is necessary for the upperlimit to be reached
		return lowerLimit + random.nextInt(1 + upperLimit - lowerLimit);
	}
}
