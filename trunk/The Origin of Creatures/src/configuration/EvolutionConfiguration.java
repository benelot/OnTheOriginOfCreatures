package configuration;

public class EvolutionConfiguration
{

	// Evolution states
	public static enum EvolutionState
	{
		EVALUATION, GENERATION, PROCESSING
	};

	// Generation variables
	public static final double[] REAPER_SOW_OFFSPRING = { 0.5, 0.25, 0.125,
			0.075, 0.075 };
	public static int REAPER_GENE_MUTATION_QTY = 3;
	public static int REAPER_ATTRIBUTE_MUTATION_QTY = 5;
	public static final int REAPER_LINK_MUTATION_QTY = 6;

	public static final double REAPER_REAP_PERCENTAGE = 0.1;
	public static final double REAPER_CROSSOVER_PERCENTAGE = 0.5;
	public static final double REAPER_GENE_MUTATION_PERCENTAGE = 0.2;
	public static final double REAPER_ATTRIBUTE_MUTATION_PERCENTAGE = 0.2;
	public static final double REAPER_SOW_FRESH_PERCENTAGE = 1.0
			- REAPER_ATTRIBUTE_MUTATION_PERCENTAGE
			- REAPER_CROSSOVER_PERCENTAGE - REAPER_GENE_MUTATION_PERCENTAGE;
	
	public static final int POPULATION_SIZE = 100;

}
