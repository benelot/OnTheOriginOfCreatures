package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroWave extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		return Math.sin(currentTime
				* (sum(dendrite) / dendrite.keySet().size()));
	}

}
