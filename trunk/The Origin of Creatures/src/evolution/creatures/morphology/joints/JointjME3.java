package evolution.creatures.morphology.joints;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.SixDofJoint;
import com.jme3.bullet.joints.motors.RotationalLimitMotor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import configuration.PhysicsConfiguration;
import evolution.creatures.Creature;
import evolution.creatures.genetics.genetypes.MusculoGene;

public abstract class JointjME3 extends Joint
{

	JointjME3(Creature creature,int componentIndex, double posX1, double posY1, double posZ1,
			double normalX1, double normalY1, double normalZ1, double dirX1,
			double dirY1, double dirZ1, double posX2, double posY2,
			double posZ2, double normalX2, double normalY2, double normalZ2,
			double dirX2, double dirY2, double dirZ2,
			MusculoGene musculoGenePitchExt, MusculoGene musculoGenePitchFlex,
			MusculoGene musculoGeneYawExt, MusculoGene musculoGeneYawFlex,
			MusculoGene musculoGeneRollExt, MusculoGene musculoGeneRollFlex)
	{
		super(creature,componentIndex, posX1, posY1, posZ1, normalX1, normalY1, normalZ1,
				dirX1, dirY1, dirZ1, posX2, posY2, posZ2, normalX2, normalY2,
				normalZ2, dirX2, dirY2, dirZ2, musculoGenePitchExt,
				musculoGenePitchFlex, musculoGeneYawExt, musculoGeneYawFlex,
				musculoGeneRollExt, musculoGeneRollFlex);

		// physics simulation puts objects to sleep when moving slower than
		// sleeping threshold. We do not want that.
		// weightNodeControl.setAngularSleepingThreshold(0);

		// weightNodeControl.setGravity(new Vector3f((float)
		// PhysicsConfiguration.PHYSICS_GRAVITY_X, (float)
		// PhysicsConfiguration.PHYSICS_GRAVITY_Y,
		// (float) PhysicsConfiguration.PHYSICS_GRAVITY_Z));
	}

	public void joinLimbs(Node A, Node B)
	{
		// TODO: improve this method
		Vector3f pivotA = A.worldToLocal(new Vector3f(((float) getPosX1()),
				((float) getPosY1()), ((float) getPosZ1())), new Vector3f());
		Vector3f pivotB = B.worldToLocal(new Vector3f(((float) getPosX1()),
				((float) getPosY1()), ((float) getPosZ1())), new Vector3f());
		// Vector3f pivotA = new Vector3f(((float) getPosX1()), ((float)
		// getPosY1()), ((float) getPosZ1()));
		// Vector3f pivotB = new Vector3f(((float) getPosX2()), ((float)
		// getPosY2()), ((float) getPosZ2()));

		// Vector3f normalA = new Vector3f(((float) getNormalX1()),
		// ((float) getNormalY1()), ((float) getNormalZ1()));
		// Vector3f normalB = new Vector3f(((float) getNormalX2()),
		// ((float) getNormalY2()), ((float) getNormalZ2()));
		// Vector3f dirA = new Vector3f(((float) getDirX1()),
		// ((float) getDirY1()), ((float) getDirZ1()));
		// Vector3f dirB = new Vector3f(((float) getDirX2()),
		// ((float) getDirY2()), ((float) getDirZ2()));
		//
		// Matrix3f rotA = new Matrix3f();
		// Matrix3f rotB = new Matrix3f();

		// Joint with six degrees of freedom, we will only use 3 of them
		// TODO:Try to use SixDofSpringJoint
		joint = new SixDofJoint(A.getControl(RigidBodyControl.class),
				B.getControl(RigidBodyControl.class), pivotA, pivotB, true);

		// fetch the reference for the rotational motors and enable them
		pitchMotor = joint
				.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_PITCH);
		yawMotor = joint
				.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_ROLL);
		rollMotor = joint
				.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_YAW);
		pitchMotor.setEnableMotor(true);
		yawMotor.setEnableMotor(true);
		rollMotor.setEnableMotor(true);

		// Default maxMotorForce is too weak to lift the weight node
		pitchMotor.setMaxMotorForce((float) getPitchExtMuscle().getMaxForce());
		yawMotor.setMaxMotorForce((float) getYawExtMuscle().getMaxForce());
		rollMotor.setMaxMotorForce((float) getRollExtMuscle().getMaxForce());
	}

	protected SixDofJoint joint;
	protected RotationalLimitMotor pitchMotor;
	protected RotationalLimitMotor yawMotor;
	protected RotationalLimitMotor rollMotor;

}
