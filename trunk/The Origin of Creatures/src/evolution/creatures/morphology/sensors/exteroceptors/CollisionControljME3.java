package evolution.creatures.morphology.sensors.exteroceptors;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;

public class CollisionControljME3 extends RigidBodyControl implements
		PhysicsCollisionListener
{

	public CollisionControljME3(TactileReceptorjME3 tactileReceptor,CollisionShape shape, double mass)
	{
		super(shape, (float) mass);
		receptor = tactileReceptor;
	}
	
	TactileReceptorjME3 receptor;

	@Override
	public void collision(PhysicsCollisionEvent event)
	{

		if (event.getObjectA() == this || event.getObjectB() == this)
		{
			receptor.isTouched = true;
		}
	}
}

