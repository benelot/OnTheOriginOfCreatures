package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroSmooth extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		double sum = sum(dendrite);

		double newSpike = (oldSpike + sum) / 2.0d;
		oldSpike = sum;
		return newSpike;
	}

	double oldSpike;

}
