package evolution.creatures.morphology;

import java.util.HashMap;
import java.util.Map;

import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.Creature;

public abstract class Component
{

	public Component(Creature creature, GeneType geneType,int componentIndex)
	{
		this.componentIndex =  componentIndex;
		this.creature = creature;
		this.geneType = geneType;
		this.axon = new HashMap<>();
		this.dendrite = new HashMap<>();
	}

	protected int componentIndex;
	public int getComponentIndex()
	{
		return componentIndex;
	}

	public void setComponentIndex(int componentIndex)
	{
		this.componentIndex = componentIndex;
	}

	protected Creature creature;
	private GeneType geneType;
	private Map<Integer, Double> dendrite;
	private Map<Integer, Double> axon;

	public Map<Integer, Double> getDendrite()
	{
		return dendrite;
	}

	public abstract void update(double tpf);

	public Map<Integer, Double> getAxon()
	{
		return axon;
	}

	public GeneType getGeneType()
	{
		return geneType;
	}

	public Creature getCreature()
	{
		return creature;
	}

	public void print()
	{
		System.out.println("|" + getGeneType().toString() + "|"
				+ axon.toString() + "|"
						+ dendrite.toString());
	}
}
