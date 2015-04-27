package evolution.creatures.morphology.neuronalnets;

import java.util.Map;

public class NeuroSumThreshold extends NeuronFunction
{
	private int threshold;

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		return (sum(dendrite) >= threshold) ? 1 : 0;
	}

	public int getThreshold()
	{
		return threshold;
	}

	public void setThreshold(int threshold)
	{
		this.threshold = threshold;
	}

}
