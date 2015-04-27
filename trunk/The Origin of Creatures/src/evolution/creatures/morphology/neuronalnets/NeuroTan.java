package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroTan extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		return Math.tan(sum(dendrite));
	}

}