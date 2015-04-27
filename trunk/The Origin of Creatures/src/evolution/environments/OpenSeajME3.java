package evolution.environments;

import misc.samples.PhysicsTestHelper;

import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import evolution.EvolutionjME3;

public class OpenSeajME3 extends EnvironmentjME3
{

	public OpenSeajME3(EvolutionjME3 evolution)
	{
		super(evolution);
		// the floor mesh, does not move (mass=0)
		Node node3 = PhysicsTestHelper
				.createPhysicsTestNode(new PlaneCollisionShape(new Plane(
						new Vector3f(0, 1, 0), 0)), 0);
		node3.getControl(RigidBodyControl.class).setPhysicsLocation(
				new Vector3f(0f, -6, 0f));
		node3.getControl(RigidBodyControl.class).setApplyPhysicsLocal(true);
		evolution.getRootNode().attachChild(node3);
		evolution.getBulletAppState().getPhysicsSpace().add(node3);
	}

}
