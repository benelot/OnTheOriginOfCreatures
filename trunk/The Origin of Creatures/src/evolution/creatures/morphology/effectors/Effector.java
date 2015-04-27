package evolution.creatures.morphology.effectors;

import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.Creature;
import evolution.creatures.morphology.Component;

public abstract class Effector extends Component
{

	public Effector(Creature creature,int componentIndex)
	{
		super(creature, GeneType.EFFECTOGENE,componentIndex);
		// TODO Auto-generated constructor stub
	}
	/*
	 * Each effector simply contains a connection to a neuron or a sensor from
	 * which to receive a value. This input value is scaled by a constant
	 * weight, and then exerted as a joint force which affects the dynamic
	 * simulation and the resulting behavior of the creature. Different types of
	 * effectors, such as sound or scent emitters, might also be interesting,
	 * but only effectors that exert simulated muscle forces are used here. Each
	 * effector controls a degree of freedom of a joint. The effectors for a
	 * given joint connecting two parts, are contained in the part further out
	 * in the hierarchy, so that each non-root part operates only a single joint
	 * connecting it to its parent. The angle sensors for that joint are also
	 * contained in this part. Each effector is given a maximum-strength
	 * proportional to the maximum cross sectional area of the two parts it
	 * joins. Effector forces are scaled by these strengths and not permitted to
	 * exceed them. Since strength scales with area, but mass scales with vol-
	 * ume, as in nature, behavior does not always scale uniformly.
	 */
}
