package tests;

import misc.samples.PhysicsTestHelper;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import evolution.creatures.Creature;
import evolution.creatures.CreaturejME3;
import evolution.creatures.morphology.PhenotypejME3;

/**
 * This test drops a creature mesh into the scene just to see if the generation
 * of phenotypes works.
 * 
 * @author leviathan
 * 
 */
public class TestCreaturejME3 extends SimpleApplication
{

	private BulletAppState bulletAppState;

	public static void main(String[] args)
	{
		TestCreaturejME3 app = new TestCreaturejME3();
		app.start();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void simpleInitApp()
	{
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		bulletAppState.getPhysicsSpace().enableDebug(assetManager);

		CreaturejME3 creaturejME3 = new CreaturejME3();
		creaturejME3.print();

		addToWorld(creaturejME3);

		// the floor mesh, does not move (mass=0)
		Node floor = PhysicsTestHelper
				.createPhysicsTestNode(new PlaneCollisionShape(new Plane(
						new Vector3f(0, 1, 0), 0)), 0);
		floor.getControl(RigidBodyControl.class).setPhysicsLocation(
				new Vector3f(0f, -6, 0f));
		rootNode.attachChild(floor);
		getPhysicsSpace().add(floor);

		// removeFromWorld(creaturejME3);
	}

	private PhysicsSpace getPhysicsSpace()
	{
		return bulletAppState.getPhysicsSpace();
	}

	public void addToWorld(Creature creature)
	{
		// TODO:Check if correct
		getRootNode().attachChild(
				((PhenotypejME3) creature.getPhenotype()).getBody());
		getPhysicsSpace().addAll(
				((PhenotypejME3) creature.getPhenotype()).getBody());
	}

	public void removeFromWorld(Creature creature)
	{
		getRootNode().detachChild(
				((PhenotypejME3) creature.getPhenotype()).getBody());
		getPhysicsSpace().removeAll(
				(((PhenotypejME3) creature.getPhenotype()).getBody()));
	}
}
