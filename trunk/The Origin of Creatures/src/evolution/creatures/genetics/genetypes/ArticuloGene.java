package evolution.creatures.genetics.genetypes;

import java.util.ArrayList;
import misc.utils.Randomness;
import configuration.GeneticsConfiguration.GeneType;
import evolution.creatures.genetics.Gene;

public class ArticuloGene extends Gene<ArticuloGene>
{

	public ArticuloGene()
	{
		super(GeneType.ARTICULOGENE);

		musculoGenePitchExt = new MusculoGene();
		musculoGenePitchFlex = new MusculoGene();

		musculoGeneRollExt = new MusculoGene();
		musculoGeneRollFlex = new MusculoGene();

		musculoGeneYawExt = new MusculoGene();
		musculoGeneYawFlex = new MusculoGene();

		// create random connection points for the joint
		posX1 = Randomness.nextDouble(0, 1);
		posX2 = Randomness.nextDouble(0, 1);
		posY1 = Randomness.nextDouble(0, 1);
		posY2 = Randomness.nextDouble(0, 1);
		posZ1 = Randomness.nextDouble(0, 1);
		posZ2 = Randomness.nextDouble(0, 1);

		// create random pitch axis directions
		dirX1 = Randomness.nextDouble(0, 1);
		dirX2 = Randomness.nextDouble(0, 1);
		dirY1 = Randomness.nextDouble(0, 1);
		dirY2 = Randomness.nextDouble(0, 1);
		dirZ1 = Randomness.nextDouble(0, 1);
		dirZ2 = Randomness.nextDouble(0, 1);

		// create random joint normals
		normalX1 = Randomness.nextDouble(0, 1);
		normalX2 = Randomness.nextDouble(0, 1);
		normalY1 = Randomness.nextDouble(0, 1);
		normalY2 = Randomness.nextDouble(0, 1);
		normalZ1 = Randomness.nextDouble(0, 1);
		normalZ2 = Randomness.nextDouble(0, 1);
	}

	public double getMaxForce()
	{
		return maxForce;
	}

	public void setMaxForce(double maxForce)
	{
		this.maxForce = maxForce;
	}

	public void setPosX1(double posX1)
	{
		this.posX1 = posX1;
	}

	public void setPosY1(double posY1)
	{
		this.posY1 = posY1;
	}

	public void setPosZ1(double posZ1)
	{
		this.posZ1 = posZ1;
	}

	public void setPosX2(double posX2)
	{
		this.posX2 = posX2;
	}

	public void setPosY2(double posY2)
	{
		this.posY2 = posY2;
	}

	public void setPosZ2(double posZ2)
	{
		this.posZ2 = posZ2;
	}

	public void setNormalX1(double normalX1)
	{
		this.normalX1 = normalX1;
	}

	public void setNormalY1(double normalY1)
	{
		this.normalY1 = normalY1;
	}

	public void setNormalZ1(double normalZ1)
	{
		this.normalZ1 = normalZ1;
	}

	public void setNormalX2(double normalX2)
	{
		this.normalX2 = normalX2;
	}

	public void setNormalY2(double normalY2)
	{
		this.normalY2 = normalY2;
	}

	public void setNormalZ2(double normalZ2)
	{
		this.normalZ2 = normalZ2;
	}

	public void setDirX1(double dirX1)
	{
		this.dirX1 = dirX1;
	}

	public void setDirY1(double dirY1)
	{
		this.dirY1 = dirY1;
	}

	public void setDirZ1(double dirZ1)
	{
		this.dirZ1 = dirZ1;
	}

	public void setDirX2(double dirX2)
	{
		this.dirX2 = dirX2;
	}

	public void setDirY2(double dirY2)
	{
		this.dirY2 = dirY2;
	}

	public void setDirZ2(double dirZ2)
	{
		this.dirZ2 = dirZ2;
	}

	private double maxForce;

	private MusculoGene musculoGenePitchExt;
	private MusculoGene musculoGenePitchFlex;
	private MusculoGene musculoGeneYawExt;
	private MusculoGene musculoGeneYawFlex;
	private MusculoGene musculoGeneRollExt;
	private MusculoGene musculoGeneRollFlex;

	/**
	 * @return the musculoGenePitchExt
	 */
	public MusculoGene getMusculoGenePitchExt()
	{
		return musculoGenePitchExt;
	}

	/**
	 * @return the musculoGenePitchFlex
	 */
	public MusculoGene getMusculoGenePitchFlex()
	{
		return musculoGenePitchFlex;
	}

	/**
	 * @return the musculoGeneYawExt
	 */
	public MusculoGene getMusculoGeneYawExt()
	{
		return musculoGeneYawExt;
	}

	/**
	 * @return the musculoGeneYawFlex
	 */
	public MusculoGene getMusculoGeneYawFlex()
	{
		return musculoGeneYawFlex;
	}

	/**
	 * @return the musculoGeneRollExt
	 */
	public MusculoGene getMusculoGeneRollExt()
	{
		return musculoGeneRollExt;
	}

	/**
	 * @return the musculoGeneRollFlex
	 */
	public MusculoGene getMusculoGeneRollFlex()
	{
		return musculoGeneRollFlex;
	}

	/**
	 * position must be inside of the cube or on the surface values are between
	 * 0 and 1 and are scaled by the length, width and height
	 */
	private double posX1;
	private double posY1;
	private double posZ1;

	private double posX2;
	private double posY2;
	private double posZ2;

	/**
	 * Normal serves as initial direction of the joint
	 */
	private double normalX1;
	private double normalY1;
	private double normalZ1;

	private double normalX2;
	private double normalY2;
	private double normalZ2;

	/**
	 * Direction of the primary axis of rotation
	 */
	private double dirX1;
	private double dirY1;
	private double dirZ1;

	private double dirX2;
	private double dirY2;
	private double dirZ2;

	/**
	 * @return the posX1
	 */
	public double getPosX1()
	{
		return posX1;
	}

	/**
	 * @return the posY1
	 */
	public double getPosY1()
	{
		return posY1;
	}

	/**
	 * @return the posZ1
	 */
	public double getPosZ1()
	{
		return posZ1;
	}

	/**
	 * @return the posX2
	 */
	public double getPosX2()
	{
		return posX2;
	}

	/**
	 * @return the posY2
	 */
	public double getPosY2()
	{
		return posY2;
	}

	/**
	 * @return the posZ2
	 */
	public double getPosZ2()
	{
		return posZ2;
	}

	/**
	 * @return the normalX1
	 */
	public double getNormalX1()
	{
		return normalX1;
	}

	/**
	 * @return the normalY1
	 */
	public double getNormalY1()
	{
		return normalY1;
	}

	/**
	 * @return the normalZ1
	 */
	public double getNormalZ1()
	{
		return normalZ1;
	}

	/**
	 * @return the normalX2
	 */
	public double getNormalX2()
	{
		return normalX2;
	}

	/**
	 * @return the normalY2
	 */
	public double getNormalY2()
	{
		return normalY2;
	}

	/**
	 * @return the normalZ2
	 */
	public double getNormalZ2()
	{
		return normalZ2;
	}

	/**
	 * @return the dirX1
	 */
	public double getDirX1()
	{
		return dirX1;
	}

	/**
	 * @return the dirY1
	 */
	public double getDirY1()
	{
		return dirY1;
	}

	/**
	 * @return the dirZ1
	 */
	public double getDirZ1()
	{
		return dirZ1;
	}

	/**
	 * @return the dirX2
	 */
	public double getDirX2()
	{
		return dirX2;
	}

	/**
	 * @return the dirY2
	 */
	public double getDirY2()
	{
		return dirY2;
	}

	/**
	 * @return the dirZ2
	 */
	public double getDirZ2()
	{
		return dirZ2;
	}

	public ArrayList<Gene<?>> getAllMuscleComponents()
	{
		ArrayList<Gene<?>> genes = new ArrayList<>();
		genes.add(getMusculoGenePitchExt());
		genes.add(getMusculoGenePitchExt().getMotorceptoGene());
		// genes.add(getMusculoGenePitchExt().getMotortensoGene());
		genes.add(getMusculoGenePitchFlex());
		genes.add(getMusculoGenePitchFlex().getMotorceptoGene());
		// genes.add(getMusculoGenePitchFlex().getMotortensoGene());
		genes.add(getMusculoGeneYawExt());
		genes.add(getMusculoGeneYawExt().getMotorceptoGene());
		// genes.add(getMusculoGeneYawExt().getMotortensoGene());
		genes.add(getMusculoGeneYawFlex());
		genes.add(getMusculoGeneYawFlex().getMotorceptoGene());
		// genes.add(getMusculoGeneYawFlex().getMotortensoGene());
		genes.add(getMusculoGeneRollExt());
		genes.add(getMusculoGeneRollExt().getMotorceptoGene());
		// genes.add(getMusculoGeneRollExt().getMotortensoGene());
		genes.add(getMusculoGeneRollFlex());
		genes.add(getMusculoGeneRollFlex().getMotorceptoGene());
		// genes.add(getMusculoGeneRollFlex().getMotortensoGene());
		return genes;
	}

	@Override
	public ArticuloGene clone()
	{
		ArticuloGene newGene = new ArticuloGene();
		newGene.posX1 = posX1;
		newGene.posX2 = posX2;
		newGene.posY1 = posY1;
		newGene.posY2 = posY2;
		newGene.posZ1 = posZ1;
		newGene.posZ2 = posZ2;

		newGene.dirX1 = dirX1;
		newGene.dirX2 = dirX2;
		newGene.dirY1 = dirY1;
		newGene.dirY2 = dirY2;
		newGene.dirZ1 = dirZ1;
		newGene.dirZ2 = dirZ2;

		newGene.normalX1 = normalX1;
		newGene.normalX2 = normalX2;
		newGene.normalY1 = normalY1;
		newGene.normalY2 = normalY2;
		newGene.normalZ1 = normalZ1;
		newGene.normalZ2 = normalZ2;

		newGene.maxForce = maxForce;
		newGene.musculoGenePitchExt = musculoGenePitchExt.clone();
		newGene.musculoGenePitchFlex = musculoGenePitchFlex.clone();
		newGene.musculoGeneRollExt = musculoGeneRollExt.clone();
		newGene.musculoGeneRollFlex = musculoGeneRollFlex.clone();
		newGene.musculoGeneYawExt = musculoGeneYawExt.clone();
		newGene.musculoGeneYawFlex = musculoGeneYawFlex.clone();
		newGene.setLinkedGeneWeights(cloneLinkedGeneWeights());
		return newGene;
	}
}
