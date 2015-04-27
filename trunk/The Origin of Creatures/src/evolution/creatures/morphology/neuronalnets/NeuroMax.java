package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroMax extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		double maximum = Double.MIN_VALUE;

		for (double input:dendrite.values())
		{
			double next = input;
			if (maximum < next)
			{
				maximum = next;
			}
		}
		return maximum;
	}

}
