package evolution.creatures.morphology;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Map.Entry;

import com.jme3.scene.Node;

import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.CreaturejME3;
import evolution.creatures.genetics.*;
import evolution.creatures.genetics.genetypes.*;
import evolution.creatures.morphology.joints.*;
import evolution.creatures.morphology.neuronalnets.Neuron;
import evolution.creatures.morphology.sensors.Sensor;
import evolution.creatures.morphology.sensors.exteroceptors.*;

/**
 * The class PhenotypejME3 is the jmonkey specific implementation of Phenotype
 * 
 * @author leviathan
 * 
 */
public class PhenotypejME3 extends Phenotype
{
	public PhenotypejME3(CreaturejME3 creature, Genome genotype)
	{
		super(creature);
		if (genotype != null)
		{
			buildPhenotypeFromGenotype(genotype);
		}
	}

	@Override
	public void buildPhenotypeFromGenotype(Genome genotype)
	{
		// separately store the body node that all other nodes are attached to
		body = ((LimbjME3) addComponent(genotype.getGenes().get(0)))
				.getLimbNode();
		// add components to the phenotype
		for (int geneNumber = 1; geneNumber < genotype.getGenes().size(); geneNumber++)
		{
			Gene<?> gene = genotype.getGenes().get(geneNumber);
			if (gene != null)
			{
				addComponent(gene);
			}
		}

		expand();

		ArrayList<LimbjME3> availableLimbs = new ArrayList<>();

		// transfer the links into a valid phenotype
		for (int geneNumber = 0; geneNumber < genotype.getGenes().size(); geneNumber++)
		{
			transferConnections(genotype, geneNumber);
		}
		
		
		//find limbs
		for(Component component: getComponents())
		if (component.getGeneType()
				.equals(GeneType.MORPHOGENE))
		{
			availableLimbs.add((LimbjME3) component);
		}

		// find connecting graph of active limbs
		Stack<LimbjME3> openLimbs = new Stack<>();
		openLimbs.add((LimbjME3) getComponents().get(0));
		activeLimbs = new ArrayList<>();

		while (openLimbs.size() != 0)
		{
			LimbjME3 limb = openLimbs.pop();
			expandLimb(limb, availableLimbs, openLimbs, activeLimbs);
		}
		for (LimbjME3 limbjME3 : activeLimbs)
		{
			body.attachChild(limbjME3.getLimbNode());
		}
		creature.setDeveloped(true);
	}

	ArrayList<LimbjME3> activeLimbs;

	@Override
	public void expand()
	{
		int unexpandedSize = getComponents().size();
		for (int i = 0; i < unexpandedSize; i++)
		{
			Component component = getComponents().get(i);
			switch (component.getGeneType())
			{
			case ARTICULOGENE:
				getComponents().addAll(
						((Joint) component).getAllJointComponents());
				break;
			/* The others do not need expansion */
			case EFFECTOGENE:
			case EXTEROCEPTOGENE:
			case MORPHOGENE:
			case MUSCULOGENE:
			case NEUROGENE:
			case PROPRIOCEPTOGENE:
				break;
			default:
				break;
			}

		}

	}

	public void expandLimb(LimbjME3 limb, ArrayList<LimbjME3> availableLimbs,
			Stack<LimbjME3> openLimbs, ArrayList<LimbjME3> activeLimbs)
	{
		if (activeLimbs.contains(limb))
		{
			return;
		}

		for (LimbjME3 alimb : availableLimbs)
		{
			if (alimb.getNeighborLimbs().contains(limb)
					|| limb.getNeighborLimbs().contains(alimb))
			{
				openLimbs.add(alimb);
			}
		}
		activeLimbs.add(limb);
	}

	@Override
	public Component addComponent(Gene<?> gene)
	{
		Component component = null;
		int componentIndex = getComponents().size();
		switch (gene.getGeneType())
		{
		case EXTEROCEPTOGENE:
			switch (((ExteroceptoGene) gene).getType())
			{
			case TACTION:
				component = new TactileReceptorjME3(creature, componentIndex);
				getComponents().add(component);
				break;
			case VISION:
				component = new PhotoReceptorjME3(creature, componentIndex);
				getComponents().add(component);
				break;
			}
			break;
		case MORPHOGENE:
			// create a new limb
			component = new LimbjME3(creature, componentIndex,
					((MorphoGene) gene).getLength(),
					((MorphoGene) gene).getWidth(),
					((MorphoGene) gene).getHeight());
			getComponents().add(component);
			break;
		case NEUROGENE:
			component = new Neuron(creature, componentIndex, (NeuroGene) gene);
			getComponents().add(component);
			break;
		case ARTICULOGENE:
			ArticuloGene articuloGene = ((ArticuloGene) gene);
			component = new ConfigurableDofJointjME3(creature, articuloGene,
					componentIndex);
			getComponents().add(component);
			break;
		default:
			/* Concerns for EFFECTOGENE,MUSCULOGENE,PROPRIOCEPTOGENE */
			break;
		}

		return component;
	}

	@Override
	public void transferConnections(Genome genotype, int index)
	{
		Gene<?> gene = genotype.getGenes().get(index);

		if (gene != null)
		{

			switch (gene.getGeneType())
			{
			case ARTICULOGENE:
				// find the first two limbs and connect them via the specified
				// joint
				Hashtable<Integer, Double> connections = new Hashtable<>();
				for (Entry<Integer, Double> entry : gene.getLinkedGeneWeights()
						.entrySet())
				{
					int linkIndex = entry.getKey();

					Gene<?> gene2 = genotype.getGenes().get(linkIndex);
					if (gene2.getGeneType().equals(GeneType.MORPHOGENE))
					{
						connections.put(linkIndex, (Double) gene
								.getLinkedGeneWeights().get(linkIndex));
					}
					if (connections.keySet().size() == 2)
					{
						// connect the limbs with the joint
						JointjME3 joint = ((JointjME3) getComponents().get(
								index));
						LimbjME3 limb1 = (LimbjME3) getComponents().get(
								(int) connections.keySet().toArray()[0]);
						LimbjME3 limb2 = (LimbjME3) getComponents().get(
								(int) connections.keySet().toArray()[1]);
						joint.joinLimbs(limb1.getLimbNode(),
								limb2.getLimbNode());

						limb1.getNeighborLimbs().add(limb2);
						limb2.getNeighborLimbs().add(limb1);
						break;
					}

				}
				break;
			case EFFECTOGENE:
			case MUSCULOGENE:
				// it is not possible to have a link from effectogene or
				// musculogene
				// to something else
				break;
			case EXTEROCEPTOGENE:
			case NEUROGENE:
			case PROPRIOCEPTOGENE:
				for (Entry<Integer, Double> entry : gene.getLinkedGeneWeights()
						.entrySet())
				{
					int linkIndex = entry.getKey();
					double weight = entry.getValue();
					Gene<?> gene2 = genotype.getGenes().get(linkIndex);
					if (isValidLink(gene.getGeneType(), gene2.getGeneType()))
					{
						getComponents().get(index).getAxon()
								.put(linkIndex, weight);
						getComponents().get(linkIndex).getDendrite()
								.put(index, 0d);
					}
				}
				break;
			case MORPHOGENE:
				for (Entry<Integer, Double> entry : gene.getLinkedGeneWeights()
						.entrySet())
				{
					int linkIndex = entry.getKey();
					Gene<?> gene2 = genotype.getGenes().get(linkIndex);
					if (gene2.getGeneType().equals(GeneType.EXTEROCEPTOGENE))
					{
						((LimbjME3) getComponents().get(index)).getSensors()
								.add((Sensor) getComponents().get(linkIndex));
					}
				}
				break;
			}
		}
	}

	private boolean isValidLink(GeneType geneType, GeneType geneType2)
	{

		boolean axonIsOfDonatorGene = (geneType
				.equals(GeneType.EXTEROCEPTOGENE)
				|| geneType.equals(GeneType.NEUROGENE) || geneType
				.equals(GeneType.PROPRIOCEPTOGENE));
		boolean dendriteIsOfAcceptorGene = (geneType2
				.equals(GeneType.NEUROGENE)
				|| geneType2.equals(GeneType.MUSCULOGENE) || geneType2
				.equals(GeneType.EFFECTOGENE));

		return (axonIsOfDonatorGene && dendriteIsOfAcceptorGene);
	}

	public Node getBody()
	{
		return body;
	}

	Node body;
}
