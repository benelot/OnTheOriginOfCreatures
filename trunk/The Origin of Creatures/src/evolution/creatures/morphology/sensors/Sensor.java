package evolution.creatures.morphology.sensors;

import configuration.GeneticsConfiguration.GeneType;
import configuration.GeneticsConfiguration.SensorType;
import evolution.creatures.Creature;
import evolution.creatures.morphology.Component;

public abstract class Sensor extends Component
{

	public Sensor(Creature creature, int componentIndex, GeneType geneType,
			SensorType sensorType)
	{
		super(creature, geneType, componentIndex);
		this.sensorType = sensorType;
	}

	SensorType sensorType;

	public SensorType getSensorType()
	{
		return sensorType;
	}

	/*
	 * Each sensor is contained within a specific part of the body, and measures
	 * either aspects of that part or aspects of the world relative to that
	 * part.
	 */

	/**
	 * @return the posX
	 */
	public double getPosX()
	{
		return posX;
	}

	/**
	 * @return the posY
	 */
	public double getPosY()
	{
		return posY;
	}

	/**
	 * @return the posZ
	 */
	public double getPosZ()
	{
		return posZ;
	}

	/**
	 * @return the normalX
	 */
	public double getNormalX()
	{
		return normalX;
	}

	/**
	 * @return the normalY
	 */
	public double getNormalY()
	{
		return normalY;
	}

	/**
	 * @return the normalZ
	 */
	public double getNormalZ()
	{
		return normalZ;
	}

	/**
	 * position must be inside of the cube or on the surface values are between
	 * 0 and 1 and are scaled by the length, width and height
	 */
	private double posX;
	private double posY;
	private double posZ;

	/**
	 * Normal serves as initial direction of the joint
	 */
	private double normalX;
	private double normalY;
	private double normalZ;

	@Override
	public abstract void update(double tpf);

}
