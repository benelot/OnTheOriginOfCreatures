package tests;

import evolution.creatures.CreaturejME3;
import evolution.creatures.genetics.Population;

public class TestPopulation
{

	public static void main(String[] args)
	{
		Population population = new Population(null, 50, CreaturejME3.class,
				null);
		population.print();
	}

}
