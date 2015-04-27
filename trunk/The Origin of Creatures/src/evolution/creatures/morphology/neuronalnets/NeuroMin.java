package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroMin extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		double minimum = Double.MIN_VALUE;

		for (double input:dendrite.values())
		{
			double next = input;
			if (minimum > next)
			{
				minimum = next;
			}
		}
		return minimum;
	}

}
