package evolution.creatures.morphology.sensors.proprioceptors;

import configuration.GeneticsConfiguration;
import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.Creature;
import evolution.creatures.morphology.sensors.Sensor;

public class Motorceptor extends Sensor
{

	public Motorceptor(Creature creature,int componentIndex)
	{
		super(creature, componentIndex, GeneType.PROPRIOCEPTOGENE, GeneticsConfiguration.SensorType.MOTORCEPTION);
	}

	public void setPosition(double position)
	{
		this.position = position;

	}

	public double getPosition()
	{
		return position;
	}

	double position;

	@Override
	public void update(double tpf)
	{
		// TODO Auto-generated method stub
		
	}

}
