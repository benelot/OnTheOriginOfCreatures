package evolution.environments;

import evolution.Evolution;

public class OpenSea extends Environment
{

	public OpenSea(Evolution evolution)
	{

	}

	// loat scatter = 1;
	// // More scattering when we look towards the light.
	// scatter *= saturate(-dot(viewDirection, DirectionalLightDirection) * 0.5
	// + 0.5);
	// // More scattering when we look straight onto the wave surface.
	// scatter *= saturate(-dot(viewDirection, normal));
	// // Less scattering when camera is above water and looks down.
	// scatter *= max(0, 1-abs(viewDirection.y));
	// // More scattering when wave is high above surface level.
	// scatter *= max(0, input.PositionWorld.y - SurfaceLevel);
}
