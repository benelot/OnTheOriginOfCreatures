package tests;

import misc.utils.Randomness;

public class TestRandomnessPosInt
{

	public static void main(String[] args)
	{
		double random;
		do
		{
			random = Randomness.nextPosInt(1, Randomness.nextPosInt(2, 1000));
			System.out.println(random);
		} while (random != 0);
	}
}
