package evolution.creatures.morphology.sensors.exteroceptors;

import configuration.GeneticsConfiguration;
import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.Creature;
import evolution.creatures.morphology.sensors.Sensor;

public abstract class TactileReceptor extends Sensor
{

	public TactileReceptor(Creature creature,int componentIndex)
	{
		super(creature, componentIndex, GeneType.EXTEROCEPTOGENE, GeneticsConfiguration.SensorType.TACTION);
	}

	public abstract boolean sense();
}
