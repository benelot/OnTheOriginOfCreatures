package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroLog extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		return Math.log(Math.abs(sum(dendrite)));
	}

}
