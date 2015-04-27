package evolution;

import misc.logging.CreatureLog;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;

import configuration.EvolutionConfiguration.EvolutionState;
import evolution.creatures.Creature;
import evolution.creatures.CreaturejME3;
import evolution.creatures.morphology.LimbjME3;
import evolution.creatures.morphology.PhenotypejME3;
import evolution.environments.Environment;
import evolution.environments.EnvironmentjME3;

/**
 * The EvolutionjME3 class is the specific implementation of the evolution
 * class.
 * 
 * @author leviathan
 * 
 */
public class EvolutionjME3 extends Evolution
{

	/**
	 * The constructor of the evolution.
	 * 
	 * @param bulletAppState
	 *            the state of the bullet physics engine.
	 * @param assetManager
	 *            the assets manager of the jmonkey engine.
	 * @param rootNode
	 *            the root node of the 3D world of the jmonkey engine.
	 */
	public EvolutionjME3(BulletAppState bulletAppState,
			AssetManager assetManager, Node rootNode)
	{
		super(); 

		// keep a reference to the bullet engine state, the asset manager and
		// the root node
		this.bulletAppState = bulletAppState;
		this.assetManager = assetManager;
		this.rootNode = rootNode;
		this.creatureInWorld = false;
		this.currentPopulation = null;
		this.currentCreature = null;
		this.populationIterator = null;
		this.creatureIterator = null;
	}

	boolean creatureInWorld;

	/**
	 * @return the bulletAppState
	 */
	public BulletAppState getBulletAppState()
	{
		return bulletAppState;
	}

	public AssetManager getAssetManager()
	{
		return assetManager;
	}

	public Node getRootNode()
	{
		return rootNode;
	}

	@Override
	public void start()
	{
		reaper.initPopulations(populations);
		initialize();
		proceed();

	}

	public void initialize()
	{
		if (populationIterator == null)
		{
			populationIterator = populations.iterator();
			if (populationIterator.hasNext())
			{
				currentPopulation = populationIterator.next();
			} else
			{
				CreatureLog.warn("No populations");
				System.exit(1);
			}
		}

		if (creatureIterator == null)
		{
			creatureIterator = currentPopulation.getCreatures().iterator();
			if (creatureIterator.hasNext())
			{
				currentCreature = creatureIterator.next();
			} else
			{
				CreatureLog.warn("A population without creatures.");
				System.exit(1);
			}
		}
	}

	public void evolve()
	{
		evolutionState = EvolutionState.GENERATION;
		reaper.evolvePopulations(populations);
		populationIterator = null;
		creatureIterator = null;
		initialize();
	}

	@Override
	public void update(float tpf)
	{
		switch (evolutionState)
		{
		case EVALUATION:
			((CreaturejME3)currentCreature).update(tpf);
			evaluationTimer.update(tpf);
			if (evaluationTimer.isEndReached())
			{
				try
				{
					proceed();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			break;
		case GENERATION:
		case PROCESSING:
			break;
		default:
			break;

		}
	}

	@Override
	public void addEnvironment(Environment environment)
	{
		EnvironmentjME3 environmentjME3 = (EnvironmentjME3) environment;
		getRootNode().attachChild(environmentjME3.getNode());
		getBulletAppState().getPhysicsSpace().add(environmentjME3.getNode());
	}

	@Override
	public void removeEnvironment(Environment environment)
	{
		EnvironmentjME3 environmentjME3 = (EnvironmentjME3) environment;
		getRootNode().detachChild(environmentjME3.getNode());
		getBulletAppState().getPhysicsSpace().remove(environmentjME3.getNode());
	}

	@Override
	public void addToWorld(Creature creature)
	{
		System.out.println("Attaching creature to world...");
		getRootNode().attachChild(
				((PhenotypejME3) creature.getPhenotype()).getBody());
		getBulletAppState().getPhysicsSpace().addAll(
				((PhenotypejME3) creature.getPhenotype()).getBody());
		creatureInWorld = true;
	}

	@Override
	public void removeFromWorld(Creature creature)
	{
		System.out.println("Detaching creature from world...");
		getRootNode().detachChild(
				((PhenotypejME3) creature.getPhenotype()).getBody());
		getBulletAppState().getPhysicsSpace().removeAll(
				(((PhenotypejME3) creature.getPhenotype()).getBody()));
		creatureInWorld = false;
	}

	/**
	 * the state of the bullet physics engine.
	 */
	private BulletAppState bulletAppState;

	/**
	 * the assets manager of the jmonkey engine.
	 */
	private AssetManager assetManager;

	/**
	 * the root node of the 3D world of the jmonkey engine.
	 */
	private Node rootNode;

	public void beginEvaluation(Creature creature)
	{
		// add creature to world
		addToWorld(creature);

		// Evaluation
		evaluationTimer.reset();
		evolutionState = EvolutionState.EVALUATION;
	}

	@Override
	public void proceed()
	{
		System.out.println("Proceeding...");

		evolutionState = EvolutionState.PROCESSING;

		// if there is still a creature in the world, remove it
		if (currentCreature != null && creatureInWorld)
		{
			removeFromWorld(currentCreature);
			currentCreature.evaluateFinalFitness();
			currentCreature = null;
		}

		if (!creatureIterator.hasNext() && !populationIterator.hasNext())
		{
			evolve();
		}

		if (!creatureIterator.hasNext() && populationIterator.hasNext())
		{
			currentPopulation = populationIterator.next();
			creatureIterator = currentPopulation.getCreatures().iterator();
		}

		if (creatureIterator.hasNext())
		{
			currentCreature = creatureIterator.next();
		}

		if (currentCreature != null)
		{
			focusFlyCam(currentCreature);
			beginEvaluation(currentCreature);
		} else
		{
			CreatureLog.warn("Unfortunately there are no creatures to test.");

		}
	}
	
	private void focusFlyCam(Creature currentCreature)
	{
		//Attach the camNode to the target:
		((LimbjME3)currentCreature.getPhenotype().getComponents().toArray()[0]).getLimbNode().attachChild(camNode);
		//Move camNode, e.g. behind and above the target:
		//camNode.setLocalTranslation(new Vector3f(0, 25, -25));
		//Rotate the camNode to look at the target:
		//camNode.lookAt(((LimbjME3)currentCreature.getPhenotype().getComponents().toArray()[0]).getLimbNode().getLocalTranslation(), Vector3f.UNIT_Y);
	}
	

	public void setCamNode(CameraNode camNode)
	{
		this.camNode = camNode;
		
	}
	
	CameraNode camNode;

	public void setCamera(Camera camera)
	{
		this.cam = camera;
		
	}
	
	public Camera getCamera()
	{
		return cam;
	}
	
	Camera cam;
}
