package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroMemory extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		this.perform(dendrite, tpf);
		double memory = 0;
		for(double input:dendrite.values())
		{
			memory = input;
		}
		return memory;
	}

}
