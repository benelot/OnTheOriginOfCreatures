package evolution.creatures.morphology;

import java.util.ArrayList;
import evolution.creatures.Creature;
import evolution.creatures.genetics.Gene;
import evolution.creatures.genetics.Genome;

public abstract class Phenotype
{
	/*
	 * The genetic representation of this morphology is a directed graph of
	 * nodes and connections. Each graph contains the developmental instructions
	 * for growing a creature, and provides a way of reusing instructions to
	 * make similar or recursive components within the creature. A phenotype
	 * hierarchy of parts is made from a graph by starting at a defined
	 * root-node and synthesizing parts from the node information while tracing
	 * through the connections of the graph. The graph can be recurrent. Nodes
	 * can connect to themselves or in cycles to form recursive or fractal like
	 * structures. They can also connect to the same child multiple times to
	 * make duplicate instances of the same appendage.
	 */

	public Phenotype(Creature creature)
	{
		this.creature = creature;
		components = new ArrayList<>();
	}

	protected Creature creature;

	private ArrayList<Component> components;

	public abstract void buildPhenotypeFromGenotype(Genome genotype);

	public abstract Component addComponent(Gene<?> gene);

	public abstract void expand();

	public abstract void transferConnections(Genome genotype, int index);


	public ArrayList<Component> getComponents()
	{
		return components;
	}

	public void print()
	{
		System.out.println("Phenotype:");
		for (int componentIndex = 0; componentIndex < components.size(); componentIndex++)
		{
			Component component = components.get(componentIndex);
			System.out.print("|" + componentIndex);
			component.print();
		}
		System.out.println("---------");
		System.out.println("Phenotype size: " + size());
	}

	private int size()
	{
		return components.size();
	}

	public void update(float tpf)
	{
		for (Component component : components)
		{
			component.update(tpf);
		}

	}
}
