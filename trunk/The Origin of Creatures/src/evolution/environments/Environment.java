package evolution.environments;

import evolution.Evolution;

public class Environment
{

	/*
	 * A viscosity effect is used for the simulations in underwater
	 * environments. For each exposed moving surface, a viscous force resists
	 * the normal component of its velocity, proportional to its sur- face area
	 * and normal velocity magnitude. This is a simple approximation that does
	 * not include the motion of the fluid itself, but is still sufficient for
	 * simulating realistic looking swimming and paddling dynamics. It is
	 * important that the physical simulation be reasonably accurate when
	 * optimizing for creatures that can move within it. Any bugs that allow
	 * energy leaks from non-conservation, or even round-off errors, will
	 * inevitably be discovered and exploited by the evolving creatures.
	 * Although this can be a lazy and often amusing approach for debugging a
	 * physical modeling system, it is not necessarily the most practical.
	 */

	protected Evolution evolution;

}
