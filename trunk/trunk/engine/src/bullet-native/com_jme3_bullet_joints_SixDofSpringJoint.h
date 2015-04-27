/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_jme3_bullet_joints_SixDofSpringJoint */

#ifndef _Included_com_jme3_bullet_joints_SixDofSpringJoint
#define _Included_com_jme3_bullet_joints_SixDofSpringJoint
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_jme3_bullet_joints_SixDofSpringJoint
 * Method:    enableSpring
 * Signature: (JIZ)V
 */
JNIEXPORT void JNICALL Java_com_jme3_bullet_joints_SixDofSpringJoint_enableSpring
  (JNIEnv *, jobject, jlong, jint, jboolean);

/*
 * Class:     com_jme3_bullet_joints_SixDofSpringJoint
 * Method:    setStiffness
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_jme3_bullet_joints_SixDofSpringJoint_setStiffness
  (JNIEnv *, jobject, jlong, jint, jfloat);

/*
 * Class:     com_jme3_bullet_joints_SixDofSpringJoint
 * Method:    setDamping
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_jme3_bullet_joints_SixDofSpringJoint_setDamping
  (JNIEnv *, jobject, jlong, jint, jfloat);

/*
 * Class:     com_jme3_bullet_joints_SixDofSpringJoint
 * Method:    setEquilibriumPoint
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_jme3_bullet_joints_SixDofSpringJoint_setEquilibriumPoint__J
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_jme3_bullet_joints_SixDofSpringJoint
 * Method:    setEquilibriumPoint
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_jme3_bullet_joints_SixDofSpringJoint_setEquilibriumPoint__JI
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     com_jme3_bullet_joints_SixDofSpringJoint
 * Method:    createJoint
 * Signature: (JJLcom/jme3/math/Vector3f;Lcom/jme3/math/Matrix3f;Lcom/jme3/math/Vector3f;Lcom/jme3/math/Matrix3f;Z)J
 */
JNIEXPORT jlong JNICALL Java_com_jme3_bullet_joints_SixDofSpringJoint_createJoint
  (JNIEnv *, jobject, jlong, jlong, jobject, jobject, jobject, jobject, jboolean);

#ifdef __cplusplus
}
#endif
#endif
