package evolution.creatures.morphology;

import evolution.creatures.Creature;
import evolution.creatures.morphology.sensors.Sensor;

import java.util.ArrayList;

import configuration.GeneticsConfiguration.GeneType;

public abstract class Limb extends Component
{
	/*
	 * Each node in the graph contains information describing a rigid part. The
	 * dimensions determine the physical shape of the part. A recursive-limit
	 * parameter determines how many times this node should generate a phenotype
	 * part when in a recursive cycle. A set of local neurons is also included
	 * in each node, and will be explained further in the next section. Finally,
	 * a node contains a set of connections to other nodes.
	 */

	public Limb(Creature creature,int componentIndex, double length, double width, double height)
	{
		super(creature, GeneType.MORPHOGENE, componentIndex);
		this.length = length;
		this.width = width;
		this.height = height;
		this.sensors = new ArrayList<>();
	}

	private double length;
	private double width;
	private double height;
	private ArrayList<Sensor> sensors;

	/**
	 * @return the sensors
	 */
	public ArrayList<Sensor> getSensors()
	{
		return sensors;
	}

	/**
	 * position must be inside of the cube or on the surface
	 */
	protected double posX;
	protected double posY;
	protected double posZ;

	/**
	 * Normal serves as initial direction of the joint
	 */
	protected double normalX;
	protected double normalY;
	protected double normalZ;

	/**
	 * Direction of the primary axis of rotation
	 */
	protected double dirX;
	protected double dirY;
	protected double dirZ;

	public double getPosX()
	{
		return posX;
	}

	public void setPosX(double posX)
	{
		this.posX = posX;
	}

	public double getPosY()
	{
		return posY;
	}

	public void setPosY(double posY)
	{
		this.posY = posY;
	}

	public double getPosZ()
	{
		return posZ;
	}

	public void setPosZ(double posZ)
	{
		this.posZ = posZ;
	}

	public double getDirX()
	{
		return dirX;
	}

	public void setDirX(double dirX)
	{
		this.dirX = dirX;
	}

	public double getDirY()
	{
		return dirY;
	}

	public void setDirY(double dirY)
	{
		this.dirY = dirY;
	}

	public double getDirZ()
	{
		return dirZ;
	}

	public void setDirZ(double dirZ)
	{
		this.dirZ = dirZ;
	}

	public double getLength()
	{
		return length;
	}

	public double getWidth()
	{
		return width;
	}

	public double getHeight()
	{
		return height;
	}

	@Override
	public abstract void update(double tpf);
}
