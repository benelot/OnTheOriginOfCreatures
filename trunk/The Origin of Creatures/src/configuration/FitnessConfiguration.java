package configuration;

public class FitnessConfiguration
{
	public static enum JuryType
	{
		MOVING, JUMPING, FOLLOWING, VELOCITY
	}

	//If the simulation is speeded up in ApplicationConfiguration, the time will be divided by the evaluation speed.
	public static final long FITNESS_EVALUATION_TIME = 10;

}
