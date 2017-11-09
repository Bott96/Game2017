package mondo3D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ProjectileEquation {

 public float gravity;
 public Vector3 startVelocity;
 public Vector3 startPoint;
 public Vector3 Dir;

 public ProjectileEquation(Vector3 startPoint, Vector3 Dir) {
  gravity = 9f;
  this.startPoint= new Vector3(startPoint);
  this.Dir = new Vector3(Dir);
  startVelocity = new Vector3(5f, 1f, 5f);
 }

 private Vector2 getXZ() {

  Vector2 XZ = new Vector2();
  XZ.x = startPoint.x + Dir.nor().x
    + Gdx.graphics.getDeltaTime() * startVelocity.x;
  XZ.y = startPoint.z + Dir.nor().z
    + Gdx.graphics.getDeltaTime() * startVelocity.z;
  return XZ;
 }

 private float getY() {
  return 0.5f * gravity * Gdx.graphics.getDeltaTime() * Gdx.graphics.getDeltaTime()
    + startVelocity.y * Gdx.graphics.getDeltaTime() + startPoint.y;
 }

 public float getTForGivenX(float x) {
  // x = startVelocity.x * t + startPoint.x
  // x - startPoint.x = startVelocity.x * t
  // t = (x - startPoint.x) / (startVelocity.x);
  return (x - startPoint.x) / (startVelocity.x);
 }
public void setNewPosition(Vector3 NuovaPos)
{
	startPoint = NuovaPos;
}
 
 public Vector3 getNewPosition() {

  return new Vector3(getXZ().x, getY(), getXZ().y);
 }

}