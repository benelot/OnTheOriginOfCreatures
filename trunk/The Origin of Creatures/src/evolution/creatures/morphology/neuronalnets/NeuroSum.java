package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroSum extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		return sum(dendrite);
	}

}
