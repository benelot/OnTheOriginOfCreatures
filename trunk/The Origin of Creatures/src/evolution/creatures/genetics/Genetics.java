package evolution.creatures.genetics;

import configuration.CreatureConfiguration;
import configuration.GeneticsConfiguration;
import configuration.GeneticsConfiguration.ExteroceptoType;
import configuration.GeneticsConfiguration.GeneType;
import configuration.NeuronalNetworkConfiguration.NeuroType;
import evolution.creatures.genetics.genetypes.ArticuloGene;
import evolution.creatures.genetics.genetypes.ExteroceptoGene;
import evolution.creatures.genetics.genetypes.MorphoGene;
import evolution.creatures.genetics.genetypes.MusculoGene;
import evolution.creatures.genetics.genetypes.NeuroGene;
import misc.utils.Randomness;

public class Genetics
{

	/*
	 * A directed graph is mutated by the following sequence of steps: 1. The
	 * internal parameters of each node are subjected to possible alterations. A
	 * mutation frequency for each parameter type determines the probability
	 * that a mutation will be applied to it at all. Boolean values are mutated
	 * by simply flipping their state. Scalar values are mutated by adding
	 * several random numbers to them for a Gaussian-like distribution so small
	 * adjustments are more likely than drastic ones. The scale of an adjustment
	 * is relative to the original value, so large quantities can be varied more
	 * easily and small ones can be carefully tuned. A scalar can also be
	 * negated. After a mutation occurs, values are clamped to their legal
	 * bounds. Some parameters that only have a limited number of legal values
	 * are mutated by simply picking a new value at random from the set of
	 * possibilities. 2. A new random node is added to the graph. A new node
	 * normally has no effect on the phenotype unless a connection also mutates
	 * a pointer to it. Therefore a new node is always initially added, but then
	 * garbage collected later (in step 5) if it does not become connected. This
	 * type of mutation allows the complexity of the graph to grow as an
	 * evolution proceeds. 3. The parameters of each connection are subjected to
	 * possible mutations, in the same way the node parameters were in step 1.
	 * With some frequency the connection pointer is moved to point to a
	 * different node which is chosen at random. 4. New random connections are
	 * added and existing ones are removed. In the case of the neural graphs
	 * these operations are not performed because the number of inputs for each
	 * element is fixed, but the morphological graphs can have a variable number
	 * of connections per node. Each existing node is subject to having a new
	 * random connection added to it, and each existing connection is subject to
	 * possible removal. 5. Unconnected elements are garbage collected.
	 * Connectedness is propagated outwards through the connections of the
	 * graph, starting from the root node of the morphology, or from the
	 * effector nodes of neural graphs. Although leaving the disconnected nodes
	 * for possible reconnection might be advantageous, and is probably
	 * biologically analogous, at least the unconnected newly added ones are
	 * removed to prevent unnecessary growth in graph size. Since mutations are
	 * performed on a per element basis, genotypes with only a few elements
	 * might not receive any mutations, where genotypes with many elements would
	 * receive enough mutations that they rarely resemble their parents. This is
	 * compensated for by temporarily scaling the mutation frequencies by an
	 * amount inversely proportional to the size of the current graph being
	 * mutated, such that on the average, at least one mutation occurs in the
	 * entire graph. Mutation of nested directed graphs, as are used here to
	 * represent creatures, is performed by first mutating the outer graph and
	 * then mutating the inner layer of graphs. The inner graphs are mutated
	 * last because legal values for some of their parameters (inter-node neural
	 * input sources) can depend on the topology of the outer graph.
	 * 
	 * 
	 * Sexual reproduction allows components from more than one parent to be
	 * combined into new offspring. This permits features to evolve
	 * independently and later be merged into a single individual. Two different
	 * methods for mating directed graphs are presented. The first is a
	 * crossover operation (see figure 5a). The nodes of two parents are each
	 * aligned in a row as they are stored, and the nodes of the first parent
	 * are copied to make the child, but one or more crossover points determine
	 * when the copying source should switch to the other parent. The
	 * connections of a node are copied with it and simply point to the same
	 * relative node locations as before. If the copied connections now point
	 * out of bounds because of varying node numbers they are randomly
	 * reassigned. A second mating method grafts two genotypes together by
	 * connecting a node of one parent to a node of another (see figure 5b). The
	 * first parent is copied, and one of its connections is chosen at random
	 * and adjusted to point to a random node in the second parent. Newly
	 * unconnected nodes of the first parent are removed and the newly connected
	 * node of the second parent and any of its descendants are appended to the
	 * new graph. A new directed graph can be produced by either of these two
	 * mating methods, or asexually by using only mutations. Offspring from
	 * matings are sometimes subjected to mutations afterwards, but with reduced
	 * mutation frequencies. In this work a reproduction method is chosen at
	 * random for each child to be produced by the surviving individuals using
	 * the ratios: 40% asexual, 30% cross- overs, and 30% grafting. A second
	 * parent is chosen from the survivors if necessary, and a new genotype is
	 * produced from the parent or parents. After a new generation of genotypes
	 * is created, a phenotype creature is generated from each, and again their
	 * fitness levels are evaluated. As this cycle of variation and selection
	 * continues, the population is directed towards creatures with higher and
	 * higher fitness.
	 */

	public static Genome performCrossOver(Genome mother, Genome father)
	{
		mother.compact();
		father.compact();
		Genome son = new Genome();
		son.setMorphoRoot(mother.getMorphoRoot().clone());
		son.getGenes().add(son.getMorphoRoot());
		int motherStart = Randomness
				.nextPosInt(0, mother.getGenes().size() - 1);
		int motherEnd = motherStart
				+ Randomness.nextPosInt(0, mother.getGenes().size() - 1
						- motherStart);
		int fatherStart = Randomness
				.nextPosInt(0, father.getGenes().size() - 1);
		int fatherEnd = fatherStart
				+ Randomness.nextPosInt(0, father.getGenes().size() - 1
						- fatherStart);

		son.append(mother, motherStart, motherEnd);
		son.append(father, fatherStart, fatherEnd);
		for (int i = 0; i < GeneticsConfiguration.GENOME_INITIAL_MAX_LINKS; i++)
		{
			if (Math.random() < GeneticsConfiguration.GENOME_INITIAL_CONNECTIONS_PROBABILITY)
			{
				son.linkRandomGeneTo(0);
			}
		}

		// son.rewire();
		return son;

	}

	public static Gene<?> createRandomGene()
	{
		// get a randomly chosen gene type out of the possible options
		GeneType geneType = GeneticsConfiguration.CreatableGenes[Randomness
				.nextPosInt(0, GeneticsConfiguration.CreatableGenes.length - 1)];

		Gene<?> gene = null;
		switch (geneType)
		{
		case EXTEROCEPTOGENE:
			gene = new ExteroceptoGene(
					ExteroceptoType.values()[Randomness.nextPosInt(0,
							ExteroceptoType.values().length - 1)]);
			break;
		case MORPHOGENE:
			gene = new MorphoGene();
			break;
		case NEUROGENE:
			gene = new NeuroGene(NeuroType.values()[Randomness.nextPosInt(0,
					NeuroType.values().length - 1)]);
			break;
		case ARTICULOGENE:
			gene = new ArticuloGene();
		default:
			/* Concerns EFFECTOGENE,MUSCULOGENE,PROPRIOCEPTOGENE */
			break;
		}

		return gene;
	}

	public void removeRandomGene(Genome genome)
	{
		genome.compact();
		int mutationIndex = Randomness.nextPosInt(0,
				genome.getGenes().size() - 1);
		genome.getGenes().remove(mutationIndex);
	}

	public static void performGeneMutation(Genome genome)
	{
		genome.compact();
		// the first gene can not be mutated, as it needs to be the morphoroot
		int mutationIndex = Randomness.nextPosInt(1,
				genome.getGenes().size() - 1);
		
		//under no circumstances you should modify the morphoroot
		if(mutationIndex == 0)
		{
			return;
		}
		Gene<?> gene = genome.getGenes().get(mutationIndex);
		genome.getGenes().set(mutationIndex, createRandomGene());
		genome.getGenes().get(mutationIndex)
				.setLinkedGeneWeights(gene.cloneLinkedGeneWeights());
	}

	public static void performAttributeMutation(Genome genome)
	{
		genome.compact();

		int mutationIndex = Randomness.nextPosInt(0,
				genome.getGenes().size() - 1);
		int mutationAttribute = 0;
		Gene<?> gene = genome.getGenes().get(mutationIndex);
		switch (gene.getGeneType())
		{
		case ARTICULOGENE:
			ArticuloGene aGene = (ArticuloGene) gene;

			mutationAttribute = Randomness.nextPosInt(1, 18);

			switch (mutationAttribute)
			{
			// create random connection points for the joint
			case 1:
				aGene.setPosX1(Randomness.nextDouble(0, 1));
				break;
			case 2:
				aGene.setPosX2(Randomness.nextDouble(0, 1));
				break;
			case 3:
				aGene.setPosY1(Randomness.nextDouble(0, 1));
				break;
			case 4:
				aGene.setPosY2(Randomness.nextDouble(0, 1));
				break;
			case 5:
				aGene.setPosZ1(Randomness.nextDouble(0, 1));
				break;
			case 6:
				aGene.setPosZ2(Randomness.nextDouble(0, 1));
				break;
			// create random pitch axis directions
			case 7:
				aGene.setDirX1(Randomness.nextDouble(0, 1));
				break;
			case 8:
				aGene.setDirX2(Randomness.nextDouble(0, 1));
				break;
			case 9:
				aGene.setDirY1(Randomness.nextDouble(0, 1));
				break;
			case 10:
				aGene.setDirY2(Randomness.nextDouble(0, 1));
				break;
			case 11:
				aGene.setDirZ1(Randomness.nextDouble(0, 1));
				break;
			case 12:
				aGene.setDirZ2(Randomness.nextDouble(0, 1));
				break;
			// create random joint normals
			case 13:
				aGene.setNormalX1(Randomness.nextDouble(0, 1));
				break;
			case 14:
				aGene.setNormalX2(Randomness.nextDouble(0, 1));
				break;
			case 15:
				aGene.setNormalY1(Randomness.nextDouble(0, 1));
				break;
			case 16:
				aGene.setNormalY2(Randomness.nextDouble(0, 1));
				break;
			case 17:
				aGene.setNormalZ1(Randomness.nextDouble(0, 1));
				break;
			case 18:
				aGene.setNormalZ2(Randomness.nextDouble(0, 1));
				break;
			}

			break;
		case EFFECTOGENE:
			// EffectoGene eGene = (EffectoGene) gene;
			break;
		case EXTEROCEPTOGENE:
			// ExteroceptoGene exGene = (ExteroceptoGene) gene;
			break;
		case MORPHOGENE:
			MorphoGene mGene = (MorphoGene) gene;

			mutationAttribute = Randomness.nextPosInt(1, 3);

			switch (mutationAttribute)
			{
			case 1:
				mGene.setWidth(Randomness.nextDouble(0,
						CreatureConfiguration.CREATURE_BODYPART_MAXSIZE));
				break;
			case 2:
				mGene.setHeight(Randomness.nextDouble(0,
						CreatureConfiguration.CREATURE_BODYPART_MAXSIZE));
				break;
			case 3:
				mGene.setLength(Randomness.nextDouble(0,
						CreatureConfiguration.CREATURE_BODYPART_MAXSIZE));
				break;
			}

			break;
		case MUSCULOGENE:
			MusculoGene muGene = (MusculoGene) gene;

			mutationAttribute = Randomness.nextPosInt(1, 3);

			switch (mutationAttribute)
			{
			case 1:
				muGene.setMaxForce(Randomness.nextDouble(0,
						GeneticsConfiguration.GENOME_MUSCULOGENE_MAX_FORCE));
				break;
			case 2:
				muGene.setMinLimit(Randomness.nextDouble(0, Double.MAX_VALUE));
				break;
			case 3:
				muGene.setMaxLimit(Randomness.nextDouble(0, Double.MAX_VALUE));
				break;
			}

			break;
		case NEUROGENE:
			// NeuroGene nGene = (NeuroGene) gene;
			break;
		case PROPRIOCEPTOGENE:
			// ProprioceptoGene pGene = (ProprioceptoGene) gene;
			break;
		default:
			break;

		}

	}

	public static void performLinkMutation(Genome genome)
	{
		int mutationIndex = Randomness.nextPosInt(0,
				genome.getGenes().size() - 1);

		if (genome.getGenes().get(mutationIndex).getLinkedGeneWeights()
				.keySet().size() != 0)
		{
			int linkIndex = Randomness.nextPosInt(0,
					genome.getGenes().get(mutationIndex).getLinkedGeneWeights()
							.keySet().size() - 1);
			int i = (int) genome.getGenes().get(mutationIndex)
					.getLinkedGeneWeights().keySet().toArray()[linkIndex];
			double weight = genome.getGenes().get(mutationIndex)
					.getLinkedGeneWeights().remove(i);
			int newLinkIndex = Randomness.nextPosInt(0, genome.getGenes()
					.size() - 1);
			genome.getGenes().get(mutationIndex).getLinkedGeneWeights()
					.put(newLinkIndex, weight);
		}

	}
}
