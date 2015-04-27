package evolution.creatures.genetics;

import java.util.HashMap;
import java.util.Map.Entry;

import configuration.GeneticsConfiguration.GeneType;

/**
 * A gene is part of the complete genome of a creature. It encodes for a certain
 * part of it, be it a certain body part, sensor type, an actuator or a neuronal
 * network connection.
 * 
 * @author leviathan
 * 
 */
public abstract class Gene<T extends Gene<?>>
{

	public Gene(GeneType geneType)
	{
		linkedGeneWeights = new HashMap<>();
		this.geneType = geneType;
		this.active = true;
	}

	private HashMap<Integer, Double> linkedGeneWeights;

	private GeneType geneType;

	private boolean active;

	public GeneType getGeneType()
	{
		return geneType;
	}

	public HashMap<Integer, Double> getLinkedGeneWeights()
	{
		return linkedGeneWeights;
	}

	public void setLinkedGeneWeights(HashMap<Integer, Double> linkedGeneWeights)
	{
		this.linkedGeneWeights = linkedGeneWeights;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public abstract T clone();

	public HashMap<Integer, Double> cloneLinkedGeneWeights()
	{
		HashMap<Integer, Double> newGeneLinkedWeights = new HashMap<>();
		for (Entry<Integer, Double> entry : getLinkedGeneWeights()
				.entrySet())
		{
			int index = entry.getKey();
			double weight = entry.getValue();
			newGeneLinkedWeights.put(index, weight);
		}
		return newGeneLinkedWeights;
	}

	public void print()
	{
		System.out.println("|" + getGeneType().toString() + "|"
				+ linkedGeneWeights.toString());

	}
}
