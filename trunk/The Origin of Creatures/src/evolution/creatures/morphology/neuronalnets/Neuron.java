package evolution.creatures.morphology.neuronalnets;

import java.util.Map.Entry;
import configuration.GeneticsConfiguration.GeneType;
import configuration.NeuronalNetworkConfiguration;
import configuration.NeuronalNetworkConfiguration.NeuroType;
import evolution.creatures.Creature;
import evolution.creatures.genetics.genetypes.NeuroGene;
import evolution.creatures.morphology.Component;

public class Neuron extends Component
{

	public Neuron(Creature creature, int componentIndex,
			NeuronFunction neuronFunction)
	{
		super(creature, GeneType.NEUROGENE, componentIndex);
		this.neuronFunction = neuronFunction;
	}

	/*
	 * Internal neural nodes are used to give virtual creatures the possibility
	 * of arbitrary behavior. Ideally a creature should be able to have an
	 * internal state beyond its sensor values, or be affected by its history.
	 * different neural nodes can perform diverse functions on their inputs to
	 * generate their output signals. Because of this, a creature’s brain might
	 * resemble a dataflow computer program more than a typical neural network.
	 * This approach is probably less biologically realistic than just using sum
	 * and threshold functions, but it is hoped that it makes the evolution of
	 * interesting behaviors more likely. The set of functions that neural nodes
	 * can have is: sum, product, divide, sum-threshold, greater-than, sign-of,
	 * min, max, abs, if, interpolate, sin, cos, atan, log, expt, sigmoid,
	 * integrate, differentiate, smooth, memory, oscillate-wave, and
	 * oscillate-saw. Some functions compute an output directly from their
	 * inputs, while others such as the oscillators retain some state and can
	 * give time varying outputs even when their inputs are constant. The number
	 * of inputs to a neuron depends on its function, and here is at most three.
	 * Each input contains a connection to another neuron or a sensor from which
	 * to receive a value. Alternatively, an input can simply receive a constant
	 * value. The input values are first scaled by weights before being operated
	 * on. For each simulated time interval, every neuron computes its output
	 * value from its inputs. In this work, two brain time steps are performed
	 * for each dynamic simulation time step so signals can propagate through
	 * multiple neurons with less delay.
	 */

	/*
	 * When an axon of cell A is near enough to excite a cell B and repeatedly
	 * or persistently takes part in firing it , some growth process or
	 * metabolic change takes place in one or both cells such that A's
	 * efficiency, as one of the cells firing B, is increased (D.O. Hebb,The
	 * Organization of Behavior,p. 50).
	 */
	public Neuron(Creature creature, int componentIndex, NeuroGene neuroGene)
	{
		super(creature, GeneType.NEUROGENE, componentIndex);
		switch (neuroGene.getType())
		{
		case ABS:
			this.neuronFunction = new NeuroSum();
			break;
		case COS:
			this.neuronFunction = new NeuroCos();
			break;
		case EXP:
			this.neuronFunction = new NeuroExp();
			break;
		case GREATER_THAN:
			this.neuronFunction = new NeuroGreaterThan();
			break;
		case LOG:
			this.neuronFunction = new NeuroLog();
			break;
		case MAX:
			this.neuronFunction = new NeuroMax();
			break;
		case MIN:
			this.neuronFunction = new NeuroMin();
			break;
		case PRODUCT:
			this.neuronFunction = new NeuroProduct();
			break;
		case SAW:
			this.neuronFunction = new NeuroSaw();
			break;
		case SIGMOID:
			this.neuronFunction = new NeuroSigmoid();
			break;
		case SIGN:
			this.neuronFunction = new NeuroSign();
			break;
		case SIN:
			this.neuronFunction = new NeuroSin();
			break;
		case SMOOTH:
			this.neuronFunction = new NeuroSmooth();
			break;
		case SUM:
			this.neuronFunction = new NeuroSum();
			break;
		case SUM_THRESHOLD:
			this.neuronFunction = new NeuroSumThreshold();
			break;
		case WAVE:
			this.neuronFunction = new NeuroWave();
			break;
		}
		neuroType = neuroGene.getType();
	}

	private NeuronFunction neuronFunction;
	private NeuroType neuroType;

	private void performComputation(double tpf)
	{
		spikeInformation = neuronFunction.perform(getDendrite(), tpf);
		if (neuroType.equals(NeuroType.SUM_THRESHOLD)
				&& NeuronalNetworkConfiguration.USE_SYNAPSE_MUTATION)
		{
			if (spikeInformation == 1)
			{
				double rootSumOfSquares = 0;
				for (Entry<Integer, Double> dendriteConnection : getDendrite()
						.entrySet())
				{
					if (dendriteConnection.getValue() != 0)
					{
						rootSumOfSquares += Math.pow(dendriteConnection.getValue(),2);
					}
				}
				
				rootSumOfSquares = Math.sqrt(rootSumOfSquares);
				
				
				for (Entry<Integer, Double> dendriteConnection : getDendrite()
						.entrySet())
				{
					if (dendriteConnection.getValue() != 0)
					{
						// perform Oja's rule and strengthen increase the
						// strength of this double
						double oldStrength = getCreature().getPhenotype().getComponents()
						.get(dendriteConnection.getKey()).getAxon()
						.get(componentIndex);
						double newStrength = oldStrength + NeuronalNetworkConfiguration.LEARNINGRATE * spikeInformation * dendriteConnection.getValue();
						getCreature().getPhenotype().getComponents()
						.get(dendriteConnection.getKey()).getAxon().put(componentIndex, newStrength);
					}
				}
		
		

			}
		}

		for (Entry<Integer, Double> axonConnection:getAxon().entrySet())
		{
			Component component = getCreature().getPhenotype().getComponents()
					.get(axonConnection.getKey());
			component.getDendrite().put(componentIndex, spikeInformation);
			//System.out.println("Spike information "+spikeInformation);

		}
	}

	private double spikeInformation;

	@Override
	public void update(double tpf)
	{
		performComputation(tpf);

	}
}
