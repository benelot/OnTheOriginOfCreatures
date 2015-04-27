package evolution.creatures.morphology.sensors.exteroceptors;

import configuration.GeneticsConfiguration;
import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.Creature;
import evolution.creatures.morphology.sensors.Sensor;

public abstract class PhotoReceptor extends Sensor
{

	public PhotoReceptor(Creature creature,int componentIndex)
	{
		super(creature,componentIndex, GeneType.EXTEROCEPTOGENE, GeneticsConfiguration.SensorType.VISION);
	}

	public abstract boolean sense();
}
