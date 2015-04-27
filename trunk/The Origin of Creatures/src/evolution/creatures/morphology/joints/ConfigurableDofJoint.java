package evolution.creatures.morphology.joints;

/**
 * Spheroidal Joint, multiaxial movement allows for rotational movement in
 * several planes (eg. hip joint)
 */

public interface ConfigurableDofJoint
{

	public abstract void exertPitchForce(double extForce, double flexForce);

	public abstract void exertYawForce(double extForce, double flexForce);

	public abstract void exertRollForce(double extForce, double flexForce);

}
