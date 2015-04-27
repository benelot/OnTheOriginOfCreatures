package evolution.creatures.morphology.neuronalnets;

import java.util.Iterator;
import java.util.Map;

/*
 * Numbers could be interpreted as number pairs (x,y) between which you integrate against 0. 
 */
public class NeuroIntegrate extends NeuronFunction
{

	@Override
	public double perform(Map<Integer, Double> dendrite, double tpf)
	{
		super.perform(dendrite, tpf);
		Iterator<Integer> dendrator = dendrite.keySet().iterator();
		int area = 0;
		double x1 = 0;
		if (dendrator.hasNext())
		{
			x1 = dendrite.get(dendrator.next());
		}
		double y1 = 0;
		if (dendrator.hasNext())
		{
			y1 = dendrite.get(dendrator.next());
		}
		double x2 = 0;

		double y2 = 0;

		while (dendrator.hasNext())
		{
			if (dendrator.hasNext())
			{
				x2 = dendrite.get(dendrator.next());
			}
			if (dendrator.hasNext())
			{
				y2 = dendrite.get(dendrator.next());
			}

			double diffX = x2 - x1;
			double diffY = y2 - y1;

		}
		return 0;
	}

}
