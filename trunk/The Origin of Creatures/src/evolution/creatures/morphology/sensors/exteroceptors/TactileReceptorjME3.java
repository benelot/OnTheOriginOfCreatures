package evolution.creatures.morphology.sensors.exteroceptors;

import evolution.creatures.Creature;
import evolution.creatures.morphology.LimbjME3;

public class TactileReceptorjME3 extends TactileReceptor
{

	public TactileReceptorjME3(Creature creature,int componentIndex)
	{
		super(creature, componentIndex);
		isTouched = false;
	}
	
	public void addToLimb(LimbjME3 limb)
	{
		collisionControl = new CollisionControljME3(this,
				limb.getCollisionShape(), limb.getWeight());
		limb.getLimbNode().addControl(collisionControl);
	}
	
	CollisionControljME3 collisionControl;

	boolean isTouched;

	@Override
	public boolean sense()
	{
		if (isTouched)
		{
			isTouched = false;
			return true;
		}
		return false;
	}

	@Override
	public void update(double tpf)
	{
		// TODO Auto-generated method stub
		
	}

}
