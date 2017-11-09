package AttacksAndConquers;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class BoundingBox3D implements ICollidable {
	public float x;
	public float y;
	public float z;
	public float Base;
	public float Altezza;
	public float Prof;

	Matrix4 trasform;

	public BoundingBox3D(Vector3 pos) {
		x = pos.x;
		y = pos.y;
		z = pos.z;

		Base = Altezza = Prof = 2f;

	}

	public BoundingBox3D(float x2, float y2, float z2, float Base, float Altezza, float Prof) {
		this.x = x2;
		this.y = y2;
		this.z = z2;

		this.Base = Base;
		this.Altezza = Altezza;
		this.Prof = Prof;

	}

	public void SetDim(float Base, float Altezza, float Prof) {
		this.Base = Base;
		this.Altezza = Altezza;
		this.Prof = Prof;

	}

	public void setNewCoord(float Nx, float Ny, float Nz) {
		x = Nx;
		y = Ny;
		z = Nz;
	}
	public Vector3 getCoord() {
		return new Vector3( x, y, z);
	}

	public void setNewCoord(Vector3 vx) {
		x = vx.x;
		y = vx.y;
		z = vx.z;
	}

	@Override
	public boolean collide(ICollidable c) {

		BoundingBox3D b = (BoundingBox3D) c;

		if (!(this.x + this.Base < b.x || this.y + this.Altezza < b.y || this.z + this.Prof < b.z
				|| (b.x + b.Base < this.x || b.y + b.Altezza < this.y || b.z + b.Prof < this.z))) {

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		String s;
		s = "[p_x " + x + "p_y " + y + "p_z " + z + "] [" + "base " + Base + "altezza " + Altezza + " Prof " + Prof
				+ "]";

		return s;
	}

}
