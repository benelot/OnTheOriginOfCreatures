package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroGreaterThan extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		int sum = 0;
		if (dendrite.values().size() != 0)
		{
			double x1 = (double) dendrite.values().toArray()[0];
			for (double input : dendrite.values())
			{
				sum += (x1 > input) ? 1 : -1;
			}
		}
		return sum;
	}

}
