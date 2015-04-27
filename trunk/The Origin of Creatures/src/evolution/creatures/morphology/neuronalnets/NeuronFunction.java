package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public abstract class NeuronFunction
{

	double perform(Map<Integer, Double> dendrite, double tpf)
	{
		currentTime += tpf;
		return 0;
	}

	protected double currentTime;

	double sum(Map<Integer, Double> dendrite)
	{
		double sum = 0;
		for (double input:dendrite.values())
		{
			sum += input;
		}
		return sum;

	}
}
