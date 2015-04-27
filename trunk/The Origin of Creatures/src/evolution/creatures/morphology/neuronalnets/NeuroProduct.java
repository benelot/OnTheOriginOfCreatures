package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroProduct extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		double product = 1;
		for (double input:dendrite.values())
		{
			product *= input;
		}
		return product;
	}

}
