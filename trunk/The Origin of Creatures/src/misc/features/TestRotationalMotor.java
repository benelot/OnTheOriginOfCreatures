package misc.features;

import java.util.logging.Logger;

import misc.samples.PhysicsTestHelper;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.SixDofJoint;
import com.jme3.bullet.joints.motors.RotationalLimitMotor;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;

/**
 * Joint vs. spatial rotational behaviour.
 * <p>
 * I/K: desired pitch velocity<br>
 * U/O: desired yaw velocity<br>
 * J/L: desired roll velocity<br>
 * R: reset desired orientation<br>
 * SPACE: enable/disable motor control
 * <p>
 * Pink arrow: spatial<br>
 * Green arrow: joint<br>
 * Blue box: weight node
 * <p>
 * 
 * @author cmeyer
 * 
 */
public class TestRotationalMotor extends SimpleApplication implements
		ActionListener
{
	private static final Logger logger = Logger
			.getLogger(TestRotationalMotor.class.getName());

	protected BulletAppState bulletAppState;
	protected Geometry arrow;

	// current velocity for every axis
	protected float desiredPitchVelocity = 0;
	protected float desiredYawVelocity = 0;
	protected float desiredRollVelocity = 0;

	protected boolean motorEnabled = true;

	protected SixDofJoint joint;
	protected RotationalLimitMotor pitchMotor;
	protected RotationalLimitMotor yawMotor;
	protected RotationalLimitMotor rollMotor;

	/**
	 * Initialization
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void simpleInitApp()
	{
		// create the bullet state for physics simulation
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		bulletAppState.getPhysicsSpace().enableDebug(assetManager);

		setupKeys();
		setupJoint();
		setupArrows();
	}

	/**
	 * Key bindings
	 */
	private void setupKeys()
	{
		inputManager.addMapping("PitchPlus", new KeyTrigger(KeyInput.KEY_I));
		inputManager.addMapping("PitchMinus", new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping("YawPlus", new KeyTrigger(KeyInput.KEY_O));
		inputManager.addMapping("YawMinus", new KeyTrigger(KeyInput.KEY_U));
		inputManager.addMapping("RollPlus", new KeyTrigger(KeyInput.KEY_L));
		inputManager.addMapping("RollMinus", new KeyTrigger(KeyInput.KEY_J));
		inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping("toggleEnableMotors", new KeyTrigger(
				KeyInput.KEY_SPACE));
		inputManager.addListener(this, "RollMinus", "RollPlus", "PitchMinus",
				"PitchPlus", "YawMinus", "YawPlus", "Reset",
				"toggleEnableMotors");
	}

	/**
	 * Create the joint and weight node.
	 */
	private void setupJoint()
	{
		// Static nodes have zero mass
		Node holderNode = PhysicsTestHelper.createPhysicsTestNode(
				new BoxCollisionShape(new Vector3f(.1f, .1f, .1f)), 0);
		holderNode.getControl(RigidBodyControl.class).setPhysicsLocation(
				Vector3f.ZERO);
		rootNode.attachChild(holderNode);
		bulletAppState.getPhysicsSpace().add(holderNode);

		// dynamic node with mass of 1
		Node weightNode = PhysicsTestHelper.createPhysicsTestNode(
				new BoxCollisionShape(new Vector3f(.3f, .3f, .3f)), 1);
		RigidBodyControl weightNodeControl = weightNode
				.getControl(RigidBodyControl.class);
		weightNodeControl.setPhysicsLocation(new Vector3f(0f, 1, 0f));
		rootNode.attachChild(weightNode);
		bulletAppState.getPhysicsSpace().add(weightNode);

		// Joint with six degrees of freedom, we will only use 3 of them
		joint = new SixDofJoint(holderNode.getControl(RigidBodyControl.class),
				weightNode.getControl(RigidBodyControl.class), Vector3f.ZERO,
				new Vector3f(0f, -1, 0f), true);
		bulletAppState.getPhysicsSpace().add(joint);

		// fetch the reference for the rotational motors and enable them
		pitchMotor = joint.getRotationalLimitMotor(0);
		yawMotor = joint.getRotationalLimitMotor(1);
		rollMotor = joint.getRotationalLimitMotor(2);
		setEnableMotors(motorEnabled);

		// Default maxMotorForce is too weak to lift the weight node
		pitchMotor.setMaxMotorForce(1);
		yawMotor.setMaxMotorForce(1);
		rollMotor.setMaxMotorForce(1);

		// physics simulation puts objects to sleep when moving slower than
		// sleeping threshold. We do not want that.
		weightNodeControl.setAngularSleepingThreshold(0);
		weightNodeControl.setGravity(new Vector3f(0, -9.81f, 0));
	}

	private void setupArrows()
	{
		Arrow desiredArrowMesh = new Arrow(Vector3f.UNIT_Y);
		arrow = new Geometry("arrow", desiredArrowMesh);

		Material pinkMat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		pinkMat.setColor("Color", ColorRGBA.Pink);
		arrow.setMaterial(pinkMat);

		rootNode.attachChild(arrow);
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf)
	{
		if (name.equals("PitchPlus") && isPressed)
		{
			desiredPitchVelocity += 1;
		}
		if (name.equals("PitchMinus") && isPressed)
		{
			desiredPitchVelocity -= 1;
		}
		if (name.equals("YawPlus") && isPressed)
		{
			desiredYawVelocity += 1;
		}
		if (name.equals("YawMinus") && isPressed)
		{
			desiredYawVelocity -= 1;
		}
		if (name.equals("RollPlus") && isPressed)
		{
			desiredRollVelocity += 1;
		}
		if (name.equals("RollMinus") && isPressed)
		{
			desiredRollVelocity -= 1;
		}
		if (name.equals("Reset") && isPressed)
		{
			desiredPitchVelocity = 0;
			desiredYawVelocity = 0;
			desiredRollVelocity = 0;
			arrow.setLocalRotation(new Quaternion());

			joint.getBodyB().clearForces();
			joint.getBodyB().setPhysicsLocation(Vector3f.UNIT_Y);
			joint.getBodyB().setPhysicsRotation(new Quaternion());
		}
		if (name.equals("toggleEnableMotors") && isPressed)
		{
			setEnableMotors(!motorEnabled);
			logger.info("motorEnabled = " + motorEnabled);
		}
	}

	/**
	 * Enable/disable all 3 motors.
	 * 
	 * @param enableMotors
	 */
	private void setEnableMotors(boolean enableMotors)
	{
		motorEnabled = enableMotors;

		pitchMotor.setEnableMotor(enableMotors);
		yawMotor.setEnableMotor(enableMotors);
		rollMotor.setEnableMotor(enableMotors);
	}

	@Override
	public void simpleUpdate(float tpf)
	{
		// calculate rotation step for this frame from velocities
		Quaternion rotationStep = new Quaternion();
		rotationStep.multLocal(new Quaternion().fromAngleNormalAxis(
				-desiredYawVelocity * tpf, Vector3f.UNIT_Y));
		rotationStep.multLocal(new Quaternion().fromAngleNormalAxis(
				-desiredRollVelocity * tpf, Vector3f.UNIT_Z));
		rotationStep.multLocal(new Quaternion().fromAngleNormalAxis(
				-desiredPitchVelocity * tpf, Vector3f.UNIT_X));
		arrow.rotate(rotationStep);

		// set motors to desired velocity
		yawMotor.setTargetVelocity(desiredYawVelocity);
		rollMotor.setTargetVelocity(desiredRollVelocity);
		pitchMotor.setDamping(desiredRollVelocity);
		pitchMotor.setTargetVelocity(desiredPitchVelocity);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		TestRotationalMotor app = new TestRotationalMotor();
		app.start();
	}

}