package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroSaw extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		return saw(currentTime, sum(dendrite)
				/ ((double) dendrite.keySet().size()));
	}

	private double saw(double time, double frequency)
	{
		return 2.0d * ((time / frequency) - Math.floor(1.0d / 2.0d + time / frequency));
	}

}
