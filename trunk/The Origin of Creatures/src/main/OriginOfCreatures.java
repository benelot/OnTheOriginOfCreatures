package main;

import misc.logging.CreatureLog;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.CameraNode;
import com.jme3.system.AppSettings;

import configuration.ApplicationConfiguration;
import configuration.CommandConfiguration;
import configuration.EvolutionConfiguration;
import configuration.FitnessConfiguration.JuryType;
import evolution.EvolutionjME3;
import evolution.creatures.CreaturejME3;
import evolution.creatures.genetics.Population;
import evolution.environments.LandjME3;

/**
 * main file to initialize the jmonkey engine
 */
public class OriginOfCreatures extends SimpleApplication implements
		ActionListener
{

	private BulletAppState bulletAppState;
	private EvolutionjME3 evolution;

	public static void main(String[] args)
	{
		CreatureLog.info("==================================================\r\n");
		CreatureLog.info("=============Origin of Creatures==================\r\n");
		CreatureLog.info("==================================================\r\n");
		CreatureLog.info("Application started...\r\n");
		String titlestring = new String();
		titlestring += "Generation\t";
		for(int i = 0; i < EvolutionConfiguration.POPULATION_SIZE;i++)
		{
			titlestring+= i + "\t";
		}
		titlestring += "AVG Fitness\t";
		titlestring += "Population size\t";
		titlestring += "AVG genes:\t";
		titlestring += "MIN genes:\t";
		titlestring += "MAX genes:\r\n";
		CreatureLog.info(titlestring);

		AppSettings settings = new AppSettings(true);

		// add some settings
		settings.setResolution(640, 480);

		// only important on my netbook as it does not have opengl 2 and glsl
		settings.setRenderer(AppSettings.LWJGL_OPENGL1);
		settings.setFrameRate(30);
		OriginOfCreatures app = new OriginOfCreatures();
		app.setSettings(settings);

		app.start(); // start the game
	}

	@SuppressWarnings("deprecation")
	@Override
	public void simpleInitApp()
	{
		speed = ApplicationConfiguration.DEFAULT_EVALUATION_SPEED;
		proceedHit = false;
		setPauseOnLostFocus(false);

		flyCam.setEnabled(false);
		CameraNode camNode = new CameraNode("Camera Node", cam);
		ChaseCamera chaseCam = new ChaseCamera(cam, camNode, inputManager);
		chaseCam.setSmoothMotion(true);
		chaseCam.setChasingSensitivity(5f); // The lower the chasing
											// sensitivity, the slower the
											// camera will follow the target
											// when it moves.
		chaseCam.setTrailingSensitivity(5f); // The lower the trailing
												// sensitivity, the slower the
												// camera will begin to go after
												// the target when it moves.
												// Default is 0.5;
		chaseCam.setRotationSensitivity(0f); // The lower the
													// sensitivity, the
		// slower the camera will rotate
		// around the target when the
		// mosue is dragged. Default is
		// 5.
		chaseCam.setTrailingRotationInertia(0.1f); // This prevents the camera
													// to stop too abruptly when
													// the target stops rotating
													// before the camera has
													// reached the target's
													// trailing position.
													// Default is 0.1f.
		chaseCam.setDefaultDistance(100); // The default distance to the target
											// at the start of the application.
		chaseCam.setMaxDistance(100); // The maximum zoom distance. Default is
										// 40f.
		chaseCam.setMinDistance(80);
		// create the camera Node

		// This mode means that camera copies the movements of the target:/
		// camNode.setControlDir(ControlDirection.SpatialToCamera);

		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		bulletAppState.getPhysicsSpace().enableDebug(assetManager);
		// bulletAppState.getPhysicsSpace().setAccuracy(ApplicationConfiguration.PHYSICS_ACCURACY);
		inputManager.addMapping(CommandConfiguration.COMMAND_PROCEED,
				new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addListener(this, CommandConfiguration.COMMAND_PROCEED);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED1,
				new KeyTrigger(KeyInput.KEY_1));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED1);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED2,
				new KeyTrigger(KeyInput.KEY_2));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED2);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED3,
				new KeyTrigger(KeyInput.KEY_3));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED3);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED4,
				new KeyTrigger(KeyInput.KEY_4));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED4);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED5,
				new KeyTrigger(KeyInput.KEY_5));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED5);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED6,
				new KeyTrigger(KeyInput.KEY_6));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED6);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED7,
				new KeyTrigger(KeyInput.KEY_7));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED7);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED8,
				new KeyTrigger(KeyInput.KEY_8));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED8);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED9,
				new KeyTrigger(KeyInput.KEY_9));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED9);

		inputManager.addMapping(CommandConfiguration.COMMAND_SPEED0,
				new KeyTrigger(KeyInput.KEY_0));
		inputManager.addListener(this, CommandConfiguration.COMMAND_SPEED0);

		evolution = new EvolutionjME3(bulletAppState, assetManager, rootNode);
		evolution.setCamera(cam);
		evolution.setCamNode(camNode);

		Population population = new Population(evolution, EvolutionConfiguration.POPULATION_SIZE,
				CreaturejME3.class, new LandjME3(evolution));
		population.addFitnessFunction(JuryType.VELOCITY, 1);

		evolution.addPopulation(population);

		evolution.start();
	}

	@Override
	public void simpleUpdate(float tpf)
	{
		evolution.update(tpf);
	}

	@Override
	public void onAction(String actionString, boolean bln, float tpf)
	{
		switch (actionString)
		{
		case CommandConfiguration.COMMAND_PROCEED:
			if (bln && !proceedHit)
			{
				evolution.proceed();
				proceedHit = true;
			} else
			{
				proceedHit = false;
			}
			break;
		case CommandConfiguration.COMMAND_SPEED0:
			speed = 0.000001f;
			break;
		case CommandConfiguration.COMMAND_SPEED1:
			speed = 1;
			break;
		case CommandConfiguration.COMMAND_SPEED2:
			speed = 2;
			break;
		case CommandConfiguration.COMMAND_SPEED3:
			speed = 3;
			break;
		case CommandConfiguration.COMMAND_SPEED4:
			speed = 4;
			break;
		case CommandConfiguration.COMMAND_SPEED5:
			speed = 5;
			break;
		case CommandConfiguration.COMMAND_SPEED6:
			speed = 6;
			break;
		case CommandConfiguration.COMMAND_SPEED7:
			speed = 7;
			break;
		case CommandConfiguration.COMMAND_SPEED8:
			speed = 8;
			break;
		case CommandConfiguration.COMMAND_SPEED9:
			speed = 9;
			break;
		}
	}

	boolean proceedHit;
}