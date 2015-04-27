package tests;

import evolution.creatures.genetics.Genome;

public class TestGenome
{

	public static void main(String[] args) throws Exception
	{

		// is an empty genome valid?
		Genome genome = new Genome();
		System.out.println("Empty genome:");
		genome.print();

		// is a random genome being generated?
		genome.createRandomGenome();
		System.out.println("\nRandomly generated genome:");
		genome.print();

		// can genes be randomly linked?
		genome.linkRandomGenes();
		System.out.println("\nRandomly linked genes:");
		genome.print();

		// can a random gene be added?
		genome.addRandomGene();
		int size = genome.size();
		System.out.println("\nAn additional gene was added:");
		genome.print();

		// can we link a random gene to a specified gene?
		genome.linkRandomGeneTo(size - 1);
		System.out
				.println("\nA random gene is linked to the last newly added gene:");
		genome.print();

		// can we unlink a random gene from a specified gene?
		genome.unlinkRandomGeneFrom(0);
		System.out.println("\n A random gene is unlinked from the first gene:");
		genome.print();
	}

}
