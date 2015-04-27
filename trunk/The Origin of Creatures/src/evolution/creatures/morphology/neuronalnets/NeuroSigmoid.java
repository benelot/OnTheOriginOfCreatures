package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroSigmoid extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		return sigmoid(sum(dendrite));
	}

	private double sigmoid(double x)
	{
		return 1.0d / (1.0d + Math.exp(-x));
	}

}
