package tests;

import misc.utils.Randomness;

public class TestRandomnessDouble
{

	public static void main(String[] args)
	{
		double random;
		do
		{
			random = Randomness.nextDouble(0, 10);
			System.out.println(random);
		} while (random > 0);
	}
}
