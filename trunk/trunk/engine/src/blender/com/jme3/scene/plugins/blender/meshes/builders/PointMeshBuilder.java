package com.jme3.scene.plugins.blender.meshes.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Mesh.Mode;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Format;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.VertexBuffer.Usage;
import com.jme3.scene.plugins.blender.file.BlenderFileException;
import com.jme3.scene.plugins.blender.file.Pointer;
import com.jme3.scene.plugins.blender.file.Structure;
import com.jme3.util.BufferUtils;

/**
 * A builder that creates a points mesh. The result is made of points that do not belong to any edge and face.
 * 
 * @author Marcin Roguski (Kaelthas)
 */
/* package */class PointMeshBuilder {
    private static final Logger LOGGER   = Logger.getLogger(PointMeshBuilder.class.getName());

    /** An array of reference vertices. */
    private Vector3f[][]        verticesAndNormals;
    /** The vertices of the mesh. */
    private List<Vector3f>      vertices = new ArrayList<Vector3f>();
    /** The normals of the mesh. */
    private List<Vector3f>      normals              = new ArrayList<Vector3f>();
    
    /**
     * This map's key is the vertex index from 'vertices 'table and the value are indices from 'vertexList'
     * positions (it simply tells which vertex is referenced where in the result list).
     */
    private Map<Integer, List<Integer>> globalVertexReferenceMap;
    
    /**
     * Constructor. Stores the given array (not copying it).
     * The second argument describes if the model uses generated textures. If yes then no vertex amount optimisation is applied.
     * The amount of vertices is always faceCount * 3.
     * @param verticesAndNormals
     *            the reference vertices and normals array
     */
    public PointMeshBuilder(Vector3f[][] verticesAndNormals) {
        this.verticesAndNormals = verticesAndNormals;
        globalVertexReferenceMap = new HashMap<Integer, List<Integer>>(verticesAndNormals.length);
    }

    /**
     * The method reads the mesh. Since blender does not store the information in the vertex itself whether it belongs
     * anywhere or not, we need to check all vertices and use here only those that are not used.
     * @param meshStructure
     *            the mesh structure
     * @throws BlenderFileException
     *             an exception thrown when reading from the blend file fails
     */
    public void readMesh(Structure meshStructure) throws BlenderFileException {
        LOGGER.fine("Reading points mesh.");
        Pointer pMEdge = (Pointer) meshStructure.getFieldValue("medge");

        if (pMEdge.isNotNull()) {
            int count = ((Number) meshStructure.getFieldValue("totvert")).intValue();
            Set<Vector3f> usedVertices = new HashSet<Vector3f>(count);
            List<Structure> edges = pMEdge.fetchData();

            for (Structure edge : edges) {
                int v1 = ((Number) edge.getFieldValue("v1")).intValue();
                int v2 = ((Number) edge.getFieldValue("v2")).intValue();
                usedVertices.add(verticesAndNormals[v1][0]);
                usedVertices.add(verticesAndNormals[v2][0]);
            }

            if (usedVertices.size() < count) {
                vertices = new ArrayList<Vector3f>(count - usedVertices.size());
                int vertexIndex = 0, blenderVertexIndex = 0;
                for (Vector3f[] vertAndNormal : verticesAndNormals) {
                    if (!usedVertices.contains(vertAndNormal[0])) {
                        vertices.add(vertAndNormal[0]);
                        normals.add(vertAndNormal[1]);
                        this.appendVertexReference(blenderVertexIndex, vertexIndex++, globalVertexReferenceMap);
                    }
                    ++blenderVertexIndex;
                }
            }
        }
    }

    /**
     * Builds the meshes.
     * @return a map between material index and the mesh
     */
    public Map<Integer, Mesh> buildMeshes() {
        LOGGER.fine("Building point mesh.");
        Map<Integer, Mesh> result = new HashMap<Integer, Mesh>(1);

        if (vertices.size() > 0) {
            Mesh mesh = new Mesh();
            mesh.setMode(Mode.Points);
            mesh.setPointSize(3);

            // the point mesh does not need index buffer, but some modifiers applied by importer need it
            // the 'alone point' situation should be quite rare so not too many resources are wasted here
            LOGGER.fine("Creating indices buffer.");
            if (vertices.size() <= Short.MAX_VALUE) {
                short[] indices = new short[vertices.size()];
                for (int i = 0; i < vertices.size(); ++i) {
                    indices[i] = (short) i;
                }
                mesh.setBuffer(Type.Index, 1, indices);
            } else {
                int[] indices = new int[vertices.size()];
                for (int i = 0; i < vertices.size(); ++i) {
                    indices[i] = i;
                }
                mesh.setBuffer(Type.Index, 1, indices);
            }

            LOGGER.fine("Creating vertices buffer.");
            VertexBuffer verticesBuffer = new VertexBuffer(Type.Position);
            verticesBuffer.setupData(Usage.Static, 3, Format.Float, BufferUtils.createFloatBuffer(vertices.toArray(new Vector3f[vertices.size()])));
            mesh.setBuffer(verticesBuffer);
            
            LOGGER.fine("Creating normals buffer (in case of points it is required if skeleton is applied).");
            VertexBuffer normalsBuffer = new VertexBuffer(Type.Normal);
            normalsBuffer.setupData(Usage.Static, 3, Format.Float, BufferUtils.createFloatBuffer(normals.toArray(new Vector3f[normals.size()])));
            mesh.setBuffer(normalsBuffer);

            result.put(-1, mesh);
        }
        return result;
    }

    /**
     * @return <b>true</b> if the mesh has no vertices and <b>false</b> otherwise
     */
    public boolean isEmpty() {
        return vertices == null;
    }
    
    public Map<Integer, List<Integer>> getGlobalVertexReferenceMap() {
        return globalVertexReferenceMap;
    }
    
    /**
     * This method fills the vertex reference map. The vertices are loaded once and referenced many times in the model. This map is created
     * to tell where the basic vertices are referenced in the result vertex lists. The key of the map is the basic vertex index, and its key
     * - the reference indices list.
     * 
     * @param basicVertexIndex
     *            the index of the vertex from its basic table
     * @param resultIndex
     *            the index of the vertex in its result vertex list
     * @param vertexReferenceMap
     *            the reference map
     */
    private void appendVertexReference(int basicVertexIndex, int resultIndex, Map<Integer, List<Integer>> vertexReferenceMap) {
        List<Integer> referenceList = vertexReferenceMap.get(Integer.valueOf(basicVertexIndex));
        if (referenceList == null) {
            referenceList = new ArrayList<Integer>();
            vertexReferenceMap.put(Integer.valueOf(basicVertexIndex), referenceList);
        }
        referenceList.add(Integer.valueOf(resultIndex));
    }
}
