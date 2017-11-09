package mondo3D;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import AttacksAndConquers.BoundingBox3D;
import AttacksAndConquers.EnumTipoDiOggetto;
import AttacksAndConquers.Risorse;
import AttacksAndConquers.GameManager;
import Grafica.DynamicElements;
import Grafica.OggettoStatico;
import Grafica.StaticElements;
import MultiPlayer.MultiPlayerSettings;
import UserGameInput.StringAnimation;
import inputEditor.MyInput;

public class Enemy extends Thread {

	public int Vita;
	public int Munizioni;

	public Model Modello; // Medello Dell'oggetto Statico
	public BoundingBox3D BBModel; // BoundingBox
	public ModelInstance Instance; // Istanza Del Modello
	public Vector3 MiaPos;
	public Vector3 MiaDir;
	public AnimationController Anc;
	public boolean RichiestaDiProiettile = false;
	public Proiettile myBullet = null;

	public boolean Morto = false;

	public Enemy() {
		Instance = new ModelInstance(Risorse.assets.get(Risorse.Player, Model.class));
		Anc = new AnimationController(Instance);
		Morto = false;
		boolean Trovata = false;
		MiaPos = new Vector3();
		BBModel = new BoundingBox3D(new Vector3());
		Random rn = new Random();
		Vita = 100;

		while (!Trovata) {
			int range = 750 - (-750) + 1;
			MiaPos.x = rn.nextInt(range) + (-750);
			MiaPos.y = 2;
			MiaPos.z = rn.nextInt(range) + (-750);
			this.BBModel.setNewCoord(MiaPos);
			for (OggettoStatico o : StaticElements.Oggetti) {
				if (o.oggetto != EnumTipoDiOggetto.Terreno && this.BBModel.collide(o.BBModel)) {
					break;
				}
			}

			if (MiaPos.dst(GameManager.PositionPlayer) >= 350)
				Trovata = true;
		}

		BoundingBox b = new BoundingBox();
		Instance.calculateBoundingBox(b);

		Vector3 dim = new Vector3();
		b.getDimensions(dim);

		BBModel.SetDim(dim.x - 31f, dim.y, dim.z - 10);

		MiaDir = new Vector3(GameManager.PositionPlayer.cpy().sub(MiaPos));

		if (MultiPlayerSettings.MultiPlayer) {
			Instance.transform.setToTranslation(MiaPos);
		}
		Instance.transform.set(MiaPos, rotateEnemyToPlayer());
	}

	public Quaternion rotateEnemyToPlayer() {
		Quaternion quat = new Quaternion();
		float dX = GameManager.PositionPlayer.x - MiaPos.x;
		float dZ = GameManager.PositionPlayer.z - MiaPos.z;
		float theta = (float) (Math.atan2(dX, dZ));
		Quaternion rot = quat.setFromAxis(0, 1, 0, (float) Math.toDegrees(theta));

		return rot;
	}

	public Quaternion rotateEnemy() {
		Quaternion quat = new Quaternion();
		float dX = MiaPos.x;
		float dZ = MiaPos.z;
		float theta = (float) (Math.atan2(dX, dZ));
		Quaternion rot = quat.setFromAxis(0, 1, 0, (float) Math.toDegrees(theta));

		return rot;
	}

	public Quaternion rotateEnemyMultiPlayer(Vector3 pos) {
		Quaternion quat = new Quaternion();
		float dX = pos.x - MiaPos.x;
		float dZ = pos.z - MiaPos.z;
		float theta = (float) (Math.atan2(dX, dZ));
		Quaternion rot = quat.setFromAxis(0, 1, 0, (float) Math.toDegrees(theta));

		return rot;
	}

	public boolean getMorto() {
		return Morto;
	}

	public void getArtificialIntelligenceFacile() {

		Instance.transform.set(MiaPos, rotateEnemyToPlayer());
		MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
		MiaDir.nor();
		while (!Morto || DynamicElements.Giocatore.getVita() == 0) {

			if (Vita > 0) {

				try {
					Vector3 OldPos = new Vector3(MiaPos);
					Vector3 NuovaPos = new Vector3(MiaPos.add(MiaDir.cpy().scl(0.5f * Gdx.graphics.getDeltaTime())));

					BBModel.setNewCoord(NuovaPos);
					boolean Collide = false;
					boolean Terreno = true;

					for (OggettoStatico o : StaticElements.Oggetti) {

						if (o.oggetto != EnumTipoDiOggetto.Terreno && this.BBModel.collide(o.BBModel)) {
							Collide = true;
							break;
						} else if (o.oggetto == EnumTipoDiOggetto.Terreno && !this.BBModel.collide(o.BBModel)) {
							Terreno = false;
							break;
						}
					}

					if (!Collide) {

						if (MiaPos.dst(GameManager.PositionPlayer) > 50 && MiaPos.dst(GameManager.PositionPlayer) < 100) {

							MiaPos = NuovaPos;
							Instance.transform.set(MiaPos, rotateEnemyToPlayer());
							MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
							Anc.setAnimation(StringAnimation.CamminataAvanti, -1);

						} else {

							if (MiaPos.dst(GameManager.PositionPlayer) < 50) {
								Anc.setAnimation(StringAnimation.AttessaInPiedi, -1);

								if(GameManager.EffectActive)
									GameManager.mp3Shot.play();
								RichiestaDiProiettile = true;
								myBullet = null;
								sleep((long) GameManager.TimeToFire);
							} else {
								if (Terreno) {
									Instance.transform.set(MiaPos, rotateEnemy());
									MiaDir.set((GameManager.PositionPlayer.cpy().sub(MiaPos))
											.scl(new Vector3(0.35f, 1, 0.35f)));
									MiaDir.rotate(Vector3.Y, 35);
									Anc.setAnimation(StringAnimation.CamminataDestra, -1);

									sleep(50);
								} else if (!Terreno) {
									Instance.transform.set(MiaPos, rotateEnemy());
									MiaDir.set((GameManager.PositionPlayer.cpy().sub(MiaPos))
											.scl(new Vector3(0.01f, 1, 0.01f)));
									MiaDir.rotate(Vector3.Y, 90);
									Anc.setAnimation(StringAnimation.CamminataAvanti, -1);

									sleep(50);
								}
							}
						}
					} else {

						MiaPos = OldPos;
						BBModel.setNewCoord(OldPos);

						int x = 0;

						while (x < 50) {

							Anc.setAnimation(StringAnimation.CamminataIndietro, -1);
							MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
							MiaPos.sub(MiaDir.nor().cpy().scl(0.5f * Gdx.graphics.getDeltaTime()));
							x++;
							sleep(20);
							Instance.transform.set(MiaPos, rotateEnemyToPlayer());
						}

						x = 0;

						MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
						MiaDir.nor();
						MiaDir.rotate(Vector3.Y, 90);
						Instance.transform.rotate(Vector3.Y, 90);

						while (x < 50) {

							Anc.setAnimation(StringAnimation.CamminataAvanti, -1);
							MiaPos.add(MiaDir.nor().scl(20f * Gdx.graphics.getDeltaTime()));
							Instance.transform.setToTranslation(MiaPos);

							x++;
							sleep(20);
						}
					}

					sleep(20);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (Morto == false) {

				Anc.setAnimation(StringAnimation.MorteInPiedi, 1);
				BBModel.SetDim(0, 0, 0);
				Morto = true;
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	public void getArtificialIntelligenceMedia() {

		Instance.transform.set(MiaPos, rotateEnemyToPlayer());
		MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
		MiaDir.nor();
		while (!Morto || DynamicElements.Giocatore.getVita() == 0) {

			if (Vita > 0) {

				try {
					Vector3 OldPos = new Vector3(MiaPos);
					Vector3 NuovaPos = new Vector3(MiaPos.add(MiaDir.cpy().scl(0.5f * Gdx.graphics.getDeltaTime())));

					BBModel.setNewCoord(NuovaPos);
					boolean Collide = false;
					boolean Terreno = true;

					for (OggettoStatico o : StaticElements.Oggetti) {

						if (o.oggetto != EnumTipoDiOggetto.Terreno && this.BBModel.collide(o.BBModel)) {
							Collide = true;
							break;
						} else if (o.oggetto == EnumTipoDiOggetto.Terreno && !this.BBModel.collide(o.BBModel)) {
							Terreno = false;
							break;
						}
					}

					if (!Collide) {

						if (MiaPos.dst(GameManager.PositionPlayer) > 50 && MiaPos.dst(GameManager.PositionPlayer) < 125) {

							MiaPos = NuovaPos;
							Instance.transform.set(MiaPos, rotateEnemyToPlayer());
							MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
							Anc.setAnimation(StringAnimation.CamminataAvanti, -1);

						} else {

							if (MiaPos.dst(GameManager.PositionPlayer) < 50) {
								Anc.setAnimation(StringAnimation.AttessaInPiedi, -1);

								if(GameManager.EffectActive)
									GameManager.mp3Shot.play();
								RichiestaDiProiettile = true;
								myBullet = null;
								sleep(1500);
							} else {
								if (Terreno) {
									Instance.transform.set(MiaPos, rotateEnemy());
									MiaDir.set((GameManager.PositionPlayer.cpy().sub(MiaPos))
											.scl(new Vector3(0.35f, 1, 0.35f)));
									MiaDir.rotate(Vector3.Y, 35);
									Anc.setAnimation(StringAnimation.CamminataDestra, -1);

									sleep(50);
								} else if (!Terreno) {
									Instance.transform.set(MiaPos, rotateEnemy());
									MiaDir.set((GameManager.PositionPlayer.cpy().sub(MiaPos))
											.scl(new Vector3(0.01f, 1, 0.01f)));
									MiaDir.rotate(Vector3.Y, 90);
									Anc.setAnimation(StringAnimation.CamminataAvanti, -1);

									sleep(50);
								}
							}
						}
					} else {

						MiaPos = OldPos;
						BBModel.setNewCoord(OldPos);

						int x = 0;

						while (x < 50) {

							Anc.setAnimation(StringAnimation.CamminataIndietro, -1);
							MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
							MiaPos.sub(MiaDir.nor().cpy().scl(0.5f * Gdx.graphics.getDeltaTime()));
							x++;
							sleep(20);
							Instance.transform.set(MiaPos, rotateEnemyToPlayer());
						}

						x = 0;

						MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
						MiaDir.nor();
						MiaDir.rotate(Vector3.Y, 90);
						Instance.transform.rotate(Vector3.Y, 90);

						while (x < 50) {

							Anc.setAnimation(StringAnimation.CamminataAvanti, -1);
							MiaPos.add(MiaDir.nor().scl(20f * Gdx.graphics.getDeltaTime()));
							Instance.transform.setToTranslation(MiaPos);

							x++;
							sleep(20);
						}
					}

					sleep(20);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (Morto == false) {

				Anc.setAnimation(StringAnimation.MorteInPiedi, 1);
				BBModel.SetDim(0, 0, 0);
				Morto = true;
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}

	}

	public void getArtificialIntelligence() {

		Instance.transform.set(MiaPos, rotateEnemyToPlayer());
		MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
		MiaDir.nor();
		while (!Morto || DynamicElements.Giocatore.getVita() == 0) {

			if (Vita > 0) {
				try {
					Vector3 OldPos = new Vector3(MiaPos);
					Vector3 NuovaPos = new Vector3(MiaPos.add(MiaDir.cpy().scl(0.5f * Gdx.graphics.getDeltaTime())));

					BBModel.setNewCoord(NuovaPos);
					boolean Collide = false;
					boolean Terreno = true;

					for (OggettoStatico o : StaticElements.Oggetti) {

						if (o.oggetto != EnumTipoDiOggetto.Terreno && this.BBModel.collide(o.BBModel)) {
							Collide = true;
							break;
						} else if (o.oggetto == EnumTipoDiOggetto.Terreno && !this.BBModel.collide(o.BBModel)) {
							Terreno = false;
							break;
						}
					}

					if (!Collide) {

						if (MiaPos.dst(GameManager.PositionPlayer) > 50 && MiaPos.dst(GameManager.PositionPlayer) < 200) {

							MiaPos = NuovaPos;
							Instance.transform.set(MiaPos, rotateEnemyToPlayer());
							MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
							Anc.setAnimation(StringAnimation.CamminataAvanti, -1);

						} else {

							if (MiaPos.dst(GameManager.PositionPlayer) < 50) {
								Anc.setAnimation(StringAnimation.AttessaInPiedi, -1);

								if(GameManager.EffectActive)
									GameManager.mp3Shot.play();
								RichiestaDiProiettile = true;
								myBullet = null;
								sleep(1500);
							} else {
								if (Terreno) {
									Instance.transform.set(MiaPos, rotateEnemy());
									MiaDir.set((GameManager.PositionPlayer.cpy().sub(MiaPos))
											.scl(new Vector3(0.35f, 1, 0.35f)));
									MiaDir.rotate(Vector3.Y, 35);
									Anc.setAnimation(StringAnimation.CamminataDestra, -1);

									sleep(50);
								} else if (!Terreno) {
									Instance.transform.set(MiaPos, rotateEnemy());
									MiaDir.set((GameManager.PositionPlayer.cpy().sub(MiaPos))
											.scl(new Vector3(0.01f, 1, 0.01f)));
									MiaDir.rotate(Vector3.Y, 90);
									Anc.setAnimation(StringAnimation.CamminataAvanti, -1);

									sleep(50);
								}
							}
						}
					} else {

						MiaPos = OldPos;
						BBModel.setNewCoord(OldPos);

						int x = 0;

						while (x < 50) {

							Anc.setAnimation(StringAnimation.CamminataIndietro, -1);
							MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
							MiaPos.sub(MiaDir.nor().cpy().scl(0.5f * Gdx.graphics.getDeltaTime()));
							x++;
							sleep(20);
							Instance.transform.set(MiaPos, rotateEnemyToPlayer());
						}

						x = 0;

						MiaDir.set(GameManager.PositionPlayer.cpy().sub(MiaPos));
						MiaDir.nor();
						MiaDir.rotate(Vector3.Y, 90);
						Instance.transform.rotate(Vector3.Y, 90);

						while (x < 50) {

							Anc.setAnimation(StringAnimation.CamminataAvanti, -1);
							MiaPos.add(MiaDir.nor().scl(20f * Gdx.graphics.getDeltaTime()));
							Instance.transform.setToTranslation(MiaPos);

							x++;
							sleep(20);
						}
					}

					sleep(20);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (Morto == false) {

				Anc.setAnimation(StringAnimation.MorteInPiedi, 1);
				BBModel.SetDim(0, 0, 0);
				Morto = true;
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}

	}

	boolean AncoraGiocatoriVivi() {

		boolean Pok = DynamicElements.Giocatore.getVita() > 0;

		boolean CiSonoCompagni = false;				
		
		if(DynamicElements.Alleati.size()>0)
			CiSonoCompagni= true;
		
		return Pok && CiSonoCompagni;
	}

	Vector3 EnemyNear() {
		Vector3 p = new Vector3();
		float dis = Float.MAX_VALUE;

		for (Compagno c : DynamicElements.Alleati) {

			Vector3 p1 = new Vector3();
			c.Instance.transform.getTranslation(p1);
			if (p1.dst(MiaPos) < dis) {
				dis = p1.dst(MiaPos);
				p = p1;
			}

		}

		if (GameManager.PositionPlayer.dst(MiaPos) < dis) {

			dis = GameManager.PositionPlayer.dst(MiaPos);
			p = GameManager.PositionPlayer;
		}

		return p;
	}

	public void getArtificilMultiPlayerIntelligence() {

		Instance.transform.set(MiaPos, rotateEnemyMultiPlayer(new Vector3(0, 0, 0)));
		MiaDir.set(new Vector3(0, 0, 0).cpy().sub(MiaPos));
		MiaDir.nor();

		while (!Morto || AncoraGiocatoriVivi()) {

			if (Vita > 0) {

				try {

					Vector3 OldPos = new Vector3(MiaPos);
					Vector3 NuovaPos = new Vector3(MiaPos.add(MiaDir.cpy().scl(0.5f * Gdx.graphics.getDeltaTime())));

					BBModel.setNewCoord(NuovaPos);
					boolean Collide = false;
					boolean Terreno = true;

					for (OggettoStatico o : StaticElements.Oggetti) {

						if (o.oggetto != EnumTipoDiOggetto.Terreno && this.BBModel.collide(o.BBModel)) {
							Collide = true;
							break;
						} else if (o.oggetto == EnumTipoDiOggetto.Terreno && !this.BBModel.collide(o.BBModel)) {
							Terreno = false;
							break;
						}
					}

					if (!Collide) {

						Vector3 Np = EnemyNear();

						if (MiaPos.dst(Np) > 50 && MiaPos.dst(Np) < 125) {

							MiaPos = NuovaPos;
							Instance.transform.set(MiaPos, rotateEnemyMultiPlayer(Np));
							MiaDir.set(Np.cpy().sub(MiaPos));
							Anc.setAnimation(StringAnimation.CamminataAvanti, -1);

						} else {

							if (MiaPos.dst(Np) < 50) {
								Anc.setAnimation(StringAnimation.AttessaInPiedi, -1);
								RichiestaDiProiettile = true;

								if(GameManager.EffectActive)
									GameManager.mp3Shot.play();
								myBullet = null;
								sleep(1000);
							} else {
								if (Terreno) {
									Instance.transform.set(MiaPos, rotateEnemyMultiPlayer(Np));
									MiaDir.set((Np.cpy().sub(MiaPos)).scl(new Vector3(0.35f, 1, 0.35f)));
									MiaDir.rotate(Vector3.Y, 35);
									Anc.setAnimation(StringAnimation.CamminataDestra, -1);

									sleep(50);
								} else if (!Terreno) {
									Instance.transform.set(MiaPos, rotateEnemyMultiPlayer(Np));
									MiaDir.set((Np.cpy().sub(MiaPos)).scl(new Vector3(0.01f, 1, 0.01f)));
									MiaDir.rotate(Vector3.Y, 90);
									Anc.setAnimation(StringAnimation.CamminataAvanti, -1);

									sleep(50);
								}
							}
						}
					} else {

						MiaPos = OldPos;
						BBModel.setNewCoord(OldPos);

						int x = 0;

						while (x < 50) {

							Anc.setAnimation(StringAnimation.CamminataIndietro, -1);
							MiaDir.set(EnemyNear().cpy().sub(MiaPos));
							MiaPos.sub(MiaDir.nor().cpy().scl(0.5f * Gdx.graphics.getDeltaTime()));
							x++;
							sleep(20);
							Instance.transform.set(MiaPos, rotateEnemyMultiPlayer(EnemyNear()));
						}

						x = 0;

						MiaDir.set(EnemyNear().cpy().sub(MiaPos));
						MiaDir.nor();
						MiaDir.rotate(Vector3.Y, 90);
						Instance.transform.rotate(Vector3.Y, 90);

						while (x < 50) {

							Anc.setAnimation(StringAnimation.CamminataAvanti, -1);
							MiaPos.add(MiaDir.nor().scl(20f * Gdx.graphics.getDeltaTime()));
							Instance.transform.setToTranslation(MiaPos);

							x++;
							sleep(20);
						}
					}

					sleep(20);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (Morto == false) {

				Anc.setAnimation(StringAnimation.MorteInPiedi, 1);
				BBModel.SetDim(0, 0, 0);
				Morto = true;
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}

	}

	@Override
	public void run() {
		super.run();

		if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
			getArtificilMultiPlayerIntelligence();
		} else if (GameManager.DifficoltaFacile)
			getArtificialIntelligenceFacile();
		else if (GameManager.DifficoltaMedio)
			getArtificialIntelligenceMedia();
		else if (GameManager.DifficoltaDifficile)
			getArtificialIntelligence();

	}

}
