package evolution.creatures.morphology.joints;

import java.util.ArrayList;

import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.Creature;
import evolution.creatures.genetics.genetypes.MusculoGene;
import evolution.creatures.morphology.Component;

public abstract class Joint extends Component
{
	/*
	 * A joint-type determines the constraints on the relative motion between
	 * this part and its parent by defining the number of degrees of freedom of
	 * the joint and the movement allowed for each degree of freedom. The
	 * different joint-types allowed are: rigid, revolute, twist, universal,
	 * bend-twist, twist-bend, or spherical. Joint-limits determine the point
	 * beyond which restoring spring forces will be exerted for each degree of
	 * freedom.
	 * 
	 * Each connection also contains information. The placement of a child part
	 * relative to its parent is decomposed into position, orientation, scale,
	 * and reflection, so each can be mutated independently. The position of
	 * attachment is constrained to be on the surface of the parent part.
	 * Reflections cause negative scaling, and allow similar but symmetrical
	 * sub-trees to be described. A terminal-only flag can cause a connection to
	 * be applied only when the recursive limit is reached, and permits tail or
	 * hand-like components to occur at the end of chains or repeating units.
	 */
	Joint(Creature creature,int componentIndex, double posX1, double posY1, double posZ1,
			double normalX1, double normalY1, double normalZ1, double dirX1,
			double dirY1, double dirZ1, double posX2, double posY2,
			double posZ2, double normalX2, double normalY2, double normalZ2,
			double dirX2, double dirY2, double dirZ2,
			MusculoGene musculoGenePitchExt, MusculoGene musculoGenePitchFlex,
			MusculoGene musculoGeneYawExt, MusculoGene musculoGeneYawFlex,
			MusculoGene musculoGeneRollExt, MusculoGene musculoGeneRollFlex)
	{
		super(creature, GeneType.ARTICULOGENE,componentIndex);
		this.posX1 = posX1;
		this.posX2 = posX2;
		this.posY1 = posY1;
		this.posY2 = posY2;
		this.posZ1 = posZ1;
		this.posZ2 = posZ2;

		this.dirX1 = dirX1;
		this.dirX2 = dirX2;
		this.dirY1 = dirY1;
		this.dirY2 = dirY2;
		this.dirZ1 = dirZ1;
		this.dirZ2 = dirZ2;

		this.normalX1 = normalX1;
		this.normalX2 = normalX2;
		this.normalY1 = normalY1;
		this.normalY2 = normalY2;
		this.normalZ1 = normalZ1;
		this.normalZ2 = normalZ2;

		pitchExtMuscle = (musculoGenePitchExt != null) ? new Muscle(creature,
				componentIndex+1, musculoGenePitchExt.getMinLimit(),
				musculoGenePitchExt.getMaxLimit(),
				musculoGenePitchExt.getMaxForce()) : null;
		pitchFlexMuscle = (musculoGenePitchFlex != null) ? new Muscle(creature,
				componentIndex+3, musculoGenePitchFlex.getMinLimit(),
				musculoGenePitchFlex.getMaxLimit(),
				musculoGenePitchFlex.getMaxForce()) : null;
		yawExtMuscle = (musculoGeneYawExt != null) ? new Muscle(creature,
				componentIndex+5, musculoGeneYawExt.getMinLimit(),
				musculoGeneYawExt.getMaxLimit(),
				musculoGeneYawExt.getMaxForce()) : null;
		yawFlexMuscle = (musculoGeneYawFlex != null) ? new Muscle(creature,
				componentIndex+7, musculoGeneYawFlex.getMinLimit(),
				musculoGeneYawFlex.getMaxLimit(),
				musculoGeneYawFlex.getMaxForce()) : null;
		rollExtMuscle = (musculoGeneRollExt != null) ? new Muscle(creature,
				componentIndex+9, musculoGeneRollExt.getMinLimit(),
				musculoGeneRollExt.getMaxLimit(),
				musculoGeneRollExt.getMaxForce()) : null;
		rollFlexMuscle = (musculoGeneRollFlex != null) ? new Muscle(creature,
				componentIndex+11, musculoGeneRollFlex.getMinLimit(),
				musculoGeneRollFlex.getMaxLimit(),
				musculoGeneRollFlex.getMaxForce()) : null;

	}
	
	private enum DegOfFreedom
	{
		PITCH, YAW, ROLL
	};

	
	public void exertForce(DegOfFreedom dof, double extForce, double flexForce)
	{
		switch (dof)
		{
		case PITCH:
			exertPitchForce(extForce, flexForce);
			break;
		case ROLL:
			exertRollForce(extForce, flexForce);
			break;
		case YAW:
			exertYawForce(extForce, flexForce);
			break;
		default:
			break;

		}

	}
	
	public abstract void exertPitchForce(double extForce, double flexForce);
	public abstract void exertYawForce(double extForce, double flexForce);
	public abstract void exertRollForce(double extForce, double flexForce);

	public abstract ArrayList<Component> getAllJointComponents();

	// current velocity for every axis
	protected float desiredPitchVelocity = 0;
	protected float desiredYawVelocity = 0;
	protected float desiredRollVelocity = 0;

	protected Muscle pitchExtMuscle;
	protected Muscle pitchFlexMuscle;
	protected Muscle yawExtMuscle;
	protected Muscle yawFlexMuscle;
	protected Muscle rollExtMuscle;
	protected Muscle rollFlexMuscle;

	/**
	 * position must be inside of the cube or on the surface values are between
	 * 0 and 1 and are scaled by the length, width and height
	 */
	protected double posX1;
	protected double posY1;
	protected double posZ1;

	protected double posX2;
	protected double posY2;
	protected double posZ2;

	/**
	 * Normal serves as initial direction of the joint
	 */
	protected double normalX1;
	protected double normalY1;
	protected double normalZ1;

	protected double normalX2;
	protected double normalY2;
	protected double normalZ2;

	/**
	 * Direction of the primary axis of rotation
	 */
	protected double dirX1;
	protected double dirY1;
	protected double dirZ1;

	protected double dirX2;
	protected double dirY2;
	protected double dirZ2;

	/**
	 * @return the posX1
	 */
	public double getPosX1()
	{
		return posX1;
	}

	/**
	 * @return the posY1
	 */
	public double getPosY1()
	{
		return posY1;
	}

	/**
	 * @return the posZ1
	 */
	public double getPosZ1()
	{
		return posZ1;
	}

	/**
	 * @return the posX2
	 */
	public double getPosX2()
	{
		return posX2;
	}

	/**
	 * @return the posY2
	 */
	public double getPosY2()
	{
		return posY2;
	}

	/**
	 * @return the posZ2
	 */
	public double getPosZ2()
	{
		return posZ2;
	}

	/**
	 * @return the normalX1
	 */
	public double getNormalX1()
	{
		return normalX1;
	}

	/**
	 * @return the normalY1
	 */
	public double getNormalY1()
	{
		return normalY1;
	}

	/**
	 * @return the normalZ1
	 */
	public double getNormalZ1()
	{
		return normalZ1;
	}

	/**
	 * @return the normalX2
	 */
	public double getNormalX2()
	{
		return normalX2;
	}

	/**
	 * @return the normalY2
	 */
	public double getNormalY2()
	{
		return normalY2;
	}

	/**
	 * @return the normalZ2
	 */
	public double getNormalZ2()
	{
		return normalZ2;
	}

	/**
	 * @return the dirX1
	 */
	public double getDirX1()
	{
		return dirX1;
	}

	/**
	 * @return the dirY1
	 */
	public double getDirY1()
	{
		return dirY1;
	}

	/**
	 * @return the dirZ1
	 */
	public double getDirZ1()
	{
		return dirZ1;
	}

	/**
	 * @return the dirX2
	 */
	public double getDirX2()
	{
		return dirX2;
	}

	/**
	 * @return the dirY2
	 */
	public double getDirY2()
	{
		return dirY2;
	}

	/**
	 * @return the dirZ2
	 */
	public double getDirZ2()
	{
		return dirZ2;
	}

	/**
	 * @return the pitchExtMuscle
	 */
	public Muscle getPitchExtMuscle()
	{
		return pitchExtMuscle;
	}

	/**
	 * @return the pitchFlexMuscle
	 */
	public Muscle getPitchFlexMuscle()
	{
		return pitchFlexMuscle;
	}

	/**
	 * @return the yawExtMuscle
	 */
	public Muscle getYawExtMuscle()
	{
		return yawExtMuscle;
	}

	/**
	 * @return the yawFlexMuscle
	 */
	public Muscle getYawFlexMuscle()
	{
		return yawFlexMuscle;
	}

	/**
	 * @return the rollExtMuscle
	 */
	public Muscle getRollExtMuscle()
	{
		return rollExtMuscle;
	}

	/**
	 * @return the rollFlexMuscle
	 */
	public Muscle getRollFlexMuscle()
	{
		return rollFlexMuscle;
	}
}
