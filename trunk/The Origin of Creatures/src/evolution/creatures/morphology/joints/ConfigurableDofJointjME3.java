package evolution.creatures.morphology.joints;

import java.util.ArrayList;

import com.jme3.math.Quaternion;

import configuration.PhysicsConfiguration;
import evolution.creatures.Creature;
import evolution.creatures.genetics.genetypes.ArticuloGene;
import evolution.creatures.morphology.Component;

public class ConfigurableDofJointjME3 extends JointjME3 implements
		ConfigurableDofJoint
{

	public ConfigurableDofJointjME3(Creature creature, ArticuloGene gene,
			int componentIndex)
	{
		super(creature, componentIndex, gene.getPosX1(), gene.getPosY1(), gene
				.getPosZ1(), gene.getNormalX1(), gene.getNormalY1(), gene
				.getNormalZ1(), gene.getDirX1(), gene.getDirY1(), gene
				.getDirZ1(), gene.getPosX2(), gene.getPosY2(), gene.getPosZ2(),
				gene.getNormalX2(), gene.getNormalY2(), gene.getNormalZ2(),
				gene.getDirX2(), gene.getDirY2(), gene.getDirZ2(), gene
						.getMusculoGenePitchExt(), gene
						.getMusculoGenePitchFlex(),
				gene.getMusculoGeneYawExt(), gene.getMusculoGeneYawFlex(), gene
						.getMusculoGeneRollExt(), gene.getMusculoGeneRollFlex());
		// joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_PITCH).setTargetVelocity((float)
		// PhysicsConfiguration.PHYSICS_MUSCLE_MAX_VELOCITY);
		// joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_ROLL).setTargetVelocity((float)
		// PhysicsConfiguration.PHYSICS_MUSCLE_MAX_VELOCITY);
		// joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_YAW).setTargetVelocity((float)
		// PhysicsConfiguration.PHYSICS_MUSCLE_MAX_VELOCITY);
	}

	@Override
	public void exertPitchForce(double extForce, double flexForce)
	{
		// get extension force and velocity
		extForce = pitchExtMuscle.getForce();
		double extVelocity = pitchExtMuscle.getVelocity();
		//System.out.println("Extension force " + extForce);
		//System.out.println("Extension velocity " + extVelocity);

		// get flexion force and velocity
		flexForce = pitchFlexMuscle.getForce();
		double flexVelocity = pitchFlexMuscle.getVelocity();
		//System.out.println("Flexion force" + flexForce);
		//System.out.println("Flexion velocity " + flexVelocity);

		joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_PITCH)
				.setMaxMotorForce((float) Math.abs(extForce - flexForce));

		// joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_PITCH).setDamping((float)Math.min(extForce,
		// flexForce));

		joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_PITCH)
				.setTargetVelocity((float) (extVelocity - flexVelocity));
	}

	@Override
	public void exertYawForce(double extForce, double flexForce)
	{
		// get extension force and velocity
		extForce = yawExtMuscle.getForce();
		double extVelocity = yawExtMuscle.getVelocity();

		// get flexion force and velocity
		flexForce = yawFlexMuscle.getForce();
		double flexVelocity = yawFlexMuscle.getVelocity();

		joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_YAW)
				.setMaxMotorForce((float) Math.abs(extForce - flexForce));

		// joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_PITCH).setDamping((float)Math.min(extForce,
		// flexForce));

		joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_YAW)
				.setTargetVelocity((float) (extVelocity - flexVelocity));
	}

	@Override
	public void exertRollForce(double extForce, double flexForce)
	{
		// get extension force and velocity
		extForce = rollExtMuscle.getForce();
		double extVelocity = rollExtMuscle.getVelocity();

		// get flexion force and velocity
		flexForce = rollFlexMuscle.getForce();
		double flexVelocity = rollFlexMuscle.getVelocity();

		joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_ROLL)
				.setMaxMotorForce((float) Math.abs(extForce - flexForce));

		// joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_PITCH).setDamping((float)Math.min(extForce,
		// flexForce));

		joint.getRotationalLimitMotor(PhysicsConfiguration.PHYSICS_JOINT_ROLL)
				.setTargetVelocity((float) (extVelocity - flexVelocity));
	}

	@Override
	public ArrayList<Component> getAllJointComponents()
	{
		ArrayList<Component> jointComponents = new ArrayList<Component>();
		// additional motor tensor to measure the muscle tension could be useful

		jointComponents.add(pitchExtMuscle);
		jointComponents.add(pitchExtMuscle.getMotorceptor());

		jointComponents.add(pitchFlexMuscle);
		jointComponents.add(pitchFlexMuscle.getMotorceptor());

		jointComponents.add(rollExtMuscle);
		jointComponents.add(rollExtMuscle.getMotorceptor());

		jointComponents.add(rollFlexMuscle);
		jointComponents.add(rollFlexMuscle.getMotorceptor());

		jointComponents.add(yawExtMuscle);
		jointComponents.add(yawExtMuscle.getMotorceptor());

		jointComponents.add(yawFlexMuscle);
		jointComponents.add(yawFlexMuscle.getMotorceptor());
		return jointComponents;
	}

	@Override
	public void update(double tpf)
	{
		if (joint == null)
		{
			return;
		}
		// move the joint according to the muscle position
		exertPitchForce(pitchExtMuscle.getForce(), pitchFlexMuscle.getForce());
		exertRollForce(rollExtMuscle.getForce(), rollFlexMuscle.getForce());
		exertYawForce(yawExtMuscle.getForce(), yawFlexMuscle.getForce());
		
		Quaternion currentRotationB = joint.getBodyB().getPhysicsRotation();
		float[] currentAnglesB = currentRotationB.toAngles(null);
		Quaternion currentRotationA = joint.getBodyA().getPhysicsRotation();
		float[] currentAnglesA = currentRotationA.toAngles(null);
		pitchExtMuscle.getMotorceptor().setPosition(currentAnglesB[0]-currentAnglesA[0]);
		pitchFlexMuscle.getMotorceptor().setPosition(currentAnglesB[0]-currentAnglesA[0]);
		yawExtMuscle.getMotorceptor().setPosition(currentAnglesB[1]-currentAnglesA[1]);
		yawFlexMuscle.getMotorceptor().setPosition(currentAnglesB[1]-currentAnglesA[1]);
		rollExtMuscle.getMotorceptor().setPosition(currentAnglesB[2]-currentAnglesA[2]);
		rollFlexMuscle.getMotorceptor().setPosition(currentAnglesB[2]-currentAnglesA[2]);

	}
}
