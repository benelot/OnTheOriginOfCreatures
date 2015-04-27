package configuration;

public class GeneticsConfiguration
{
	// public static final int GENOME_MAX_RECURSION = 5;
	public static final double GENOME_CONNECTIONS_PROBABILITY = 0.1;
	public static final double GENOME_INITIAL_CONNECTIONS_PROBABILITY = 1;
	public static final int GENOME_INITIAL_MAX_COMPONENTS = 40;
	public static final int GENOME_INITIAL_MAX_LINKS = 20;
	public static final double GENOME_INITIAL_LINK_WEIGHT_RANGE = 10;
	public static final double GENOME_MUSCULOGENE_MAX_FORCE = 10;
	public static final int POPULATION_DEFAULT_SIZE = 100;
	public static double GENOME_EXTENSION_PROBABILITY = 0.95;

	public static enum GeneType
	{
		ARTICULOGENE, EXTEROCEPTOGENE, PROPRIOCEPTOGENE, MORPHOGENE, NEUROGENE, MUSCULOGENE, EFFECTOGENE
	};
	
	public enum ExteroceptoType
	{
		VISION, TACTION;
	}

	public enum SensorType
	{
		VISION, TACTION,PROPRIOCEPTION, MOTORCEPTION;
	}

	public static final GeneType[] CreatableGenes = { GeneType.ARTICULOGENE,/*
																			 * GeneType
																			 * .
																			 * EFFECTOGENE
																			 * ,
																			 */
			GeneType.EXTEROCEPTOGENE, GeneType.MORPHOGENE, GeneType.NEUROGENE };

	public static final GeneType[] DonatorGenes = { GeneType.EXTEROCEPTOGENE,
			GeneType.NEUROGENE, GeneType.PROPRIOCEPTOGENE };
	public static final GeneType[] AcceptorGenes = { GeneType.EFFECTOGENE,
			GeneType.MUSCULOGENE, GeneType.NEUROGENE };
}
