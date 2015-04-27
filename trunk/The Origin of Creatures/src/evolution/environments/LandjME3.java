package evolution.environments;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.HeightfieldCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

import evolution.EvolutionjME3;

public class LandjME3 extends EnvironmentjME3
{

	private TerrainQuad terrain;
	Material mat_terrain;

	public LandjME3(EvolutionjME3 evolution)
	{
		super(evolution);
		/** 1. Create terrain material and load four textures into it. */
	    mat_terrain = new Material(evolution.getAssetManager(), 
	            "Common/MatDefs/Terrain/Terrain.j3md");
	 
//	    /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
//	    mat_terrain.setTexture("Alpha", evolution.getAssetManager().loadTexture(
//	            "Textures/Terrain/splat/alphamap.png"));
//	 
//	    /** 1.2) Add GRASS texture into the red layer (Tex1). */
//	    Texture grass = evolution.getAssetManager().loadTexture(
//	            "Textures/Terrain/splat/grass.jpg");
//	    grass.setWrap(WrapMode.Repeat);
//	    mat_terrain.setTexture("Tex1", grass);
//	    mat_terrain.setFloat("Tex1Scale", 64f);
	 
	    /** 1.3) Add DIRT texture into the green layer (Tex2) */
	    Texture dirt = evolution.getAssetManager().loadTexture(
	            "Textures/Terrain/splat/road.jpg");
	    dirt.setWrap(WrapMode.Repeat);
	    mat_terrain.setTexture("Tex2", dirt);
	    mat_terrain.setFloat("Tex2Scale", 32f);
	 
//	    /** 1.4) Add ROAD texture into the blue layer (Tex3) */
//	    Texture rock = evolution.getAssetManager().loadTexture(
//	            "Textures/Terrain/splat/road.jpg");
//	    rock.setWrap(WrapMode.Repeat);
//	    mat_terrain.setTexture("Tex3", rock);
//	    mat_terrain.setFloat("Tex3Scale", 128f);
	 
	    /** 2. Create the height map */
//	    AbstractHeightMap heightmap = null;
//	    Texture heightMapImage = evolution.getAssetManager().loadTexture(
//	            "Textures/Terrain/splat/mountains512.png");
//	    heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
//	    heightmap.load();
	    HillHeightMap heightmap = null;
	    HillHeightMap.NORMALIZE_RANGE = 100; // optional
	    try {
	        heightmap = new HillHeightMap(1025, 1, 50, 100, (byte) 3); // byte 3 is a random seed
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	 
	    /** 3. We have prepared material and heightmap. 
	     * Now we create the actual terrain:
	     * 3.1) Create a TerrainQuad and name it "my terrain".
	     * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
	     * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
	     * 3.4) As LOD step scale we supply Vector3f(1,1,1).
	     * 3.5) We supply the prepared heightmap itself.
	     */
	    terrain = new TerrainQuad("my terrain", 65, 1025, heightmap.getHeightMap());
	 
	    /** 4. We give the terrain its material, position & scale it, and attach it. */
	    terrain.setMaterial(mat_terrain);
	    terrain.setLocalTranslation(0, -10, 0);
	    terrain.setLocalScale(4f, 1f, 4f);
		evolution.getRootNode().attachChild(terrain);
	 
	    /** 5. The LOD (level of detail) depends on were the camera is: */
		TerrainLodControl control = new TerrainLodControl(terrain,
				evolution.getCamera());
		terrain.addControl(control);
	 
	    /** 6. Add physics: */ 
	    /* We set up collision detection for the scene by creating a static 
	    RigidBodyControl with mass zero.*/
	    CollisionShape terrain_c = new HeightfieldCollisionShape(terrain.getHeightMap());
	    terrain.addControl(new RigidBodyControl(terrain_c, 0));
//	    terrain.addControl(new RigidBodyControl(0));
	   

//		Node ground = PhysicsTestHelper
//				.createPhysicsTestNode(new PlaneCollisionShape(new Plane(
//						new Vector3f(0, 1, 0), 0)), 0);
//		ground.getControl(RigidBodyControl.class).setPhysicsLocation(
//				new Vector3f(0f, -3, 0f));
//		ground.getControl(RigidBodyControl.class).setApplyPhysicsLocal(true);
		evolution.getBulletAppState().getPhysicsSpace().add(terrain);
	}

}
