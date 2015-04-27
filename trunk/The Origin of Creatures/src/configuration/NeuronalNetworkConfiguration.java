package configuration;

public class NeuronalNetworkConfiguration
{
	public static boolean USE_SYNAPSE_MUTATION = true;

	public enum NeuroType
	{
		SUM, PRODUCT, /* DIVISION, */SUM_THRESHOLD, GREATER_THAN, SIGN, MIN, MAX, ABS, /*
																					 * INTERPOLATE
																					 * ,
																					 */SIN, COS, /*
																								 * ATAN
																								 * ,
																								 */LOG, EXP, SIGMOID, /*
																													 * INTEGRATE
																													 * ,
																													 * DIFFERENTIATE
																													 * ,
																													 */SMOOTH, WAVE, SAW
	};
	
	public static double LEARNINGRATE = 0.1;
}
