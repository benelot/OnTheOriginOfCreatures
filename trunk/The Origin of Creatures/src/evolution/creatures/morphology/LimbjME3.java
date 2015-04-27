package evolution.creatures.morphology;

import java.util.ArrayList;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import configuration.PhysicsConfiguration;
import evolution.creatures.Creature;
import evolution.creatures.morphology.sensors.Sensor;
import evolution.creatures.morphology.sensors.exteroceptors.PhotoReceptorjME3;
import evolution.creatures.morphology.sensors.exteroceptors.TactileReceptorjME3;

public class LimbjME3 extends Limb
{

	public LimbjME3(Creature creature,int componentIndex, double length, double width,
			double height)
	{
		super(creature, componentIndex, length, width, height);

		limbNode = createLimbNode(length, width, height);
		neighborLimbs = new ArrayList<>();
	}

	private Node createLimbNode(double length, double width, double height)
	{

//		Box box = new Box((float) length, (float) height, (float) width);
//		Geometry boxGeometry = new Geometry("Limb", box);
//		boxGeometry.scale((float) length, (float) height, (float) width);
//		
//		Material material = new Material(((CreaturejME3)creature).getEvolution().getAssetManager(),
//				"Common/MatDefs/Misc/Unshaded.j3md");
//		material.setTexture("ColorMap",
//				((CreaturejME3)creature).getEvolution().getAssetManager().loadTexture("Interface/Logo/Monkey.jpg"));
//		boxGeometry.setMaterial(material);
//		// RigidBodyControl automatically uses box collision shapes when
//		// attached to single geometry with box mesh
//		weight = PhysicsConfiguration.PHYSICS_CREATURE_MATERIAL_MASS * length
//				* width * height;
//
//		
//		limbNode = new Node("Limb");
//		limbNode.addControl(new RigidBodyControl((float) weight));
//		limbNode.attachChild(boxGeometry);
//		return limbNode;

		shape = new BoxCollisionShape(new Vector3f(
				(float) length, (float) width, (float) height));
		weight = PhysicsConfiguration.PHYSICS_CREATURE_MATERIAL_MASS
				* length * width * height;

		Node node = new Node("Limb");
		RigidBodyControl rigidBodyControl = new RigidBodyControl(shape,
				(float) (weight));
		node.addControl(rigidBodyControl);
		return node;
	}
	
	BoxCollisionShape shape;
	double weight;
	
	public BoxCollisionShape getCollisionShape()
	{
		return shape;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void addSensor(Sensor sensor)
	{
		switch (sensor.getSensorType())
		{
		case PROPRIOCEPTION:
			break;
		case TACTION:
			((TactileReceptorjME3) sensor).addToLimb(this);
			break;
		case VISION:
			((PhotoReceptorjME3) sensor).addToLimb(this);
			break;
		default:
			break;
		
		}
	}

	private Node limbNode;

	public Node getLimbNode()
	{
		return limbNode;
	}

	public ArrayList<LimbjME3> getNeighborLimbs()
	{
		return neighborLimbs;
	}

	private ArrayList<LimbjME3> neighborLimbs;

	@Override
	public void update(double tpf)
	{
		// The limb itself does not have any actions

	}
}
