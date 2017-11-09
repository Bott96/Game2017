package artificialIntelligence;

import AttacksAndConquers.GameManager;
import Grafica.DynamicElements;
import mondo3D.Enemy;

public class ArtificialIntelligence {

	public ArtificialIntelligence() {

		if (GameManager.DifficoltaFacile) {

			DynamicElements.Nemici.clear();
				GameManager.TimeToFire= 1500f;
			for (int i = 0; i < GameManager.NumeroNemiciDifficoltaFacile; i++) {
				DynamicElements.Nemici.add(new Enemy());
				DynamicElements.Nemici.get(i).setName(String.valueOf(i));
			}

			for (Enemy e : DynamicElements.Nemici) {
				e.start();
			}
		} else if (GameManager.DifficoltaMedio) {

			GameManager.TimeToFire= 1000f;
			DynamicElements.Nemici.clear();
			for (int i = 0; i < GameManager.NumeroNemiciDifficoltaMedio; i++) {
				DynamicElements.Nemici.add(new Enemy());
				DynamicElements.Nemici.get(i).setName(String.valueOf(i));
			}

			for (Enemy e : DynamicElements.Nemici) {
				e.start();
			}
		} else if (GameManager.DifficoltaDifficile) {

			GameManager.TimeToFire= 700f;
			DynamicElements.Nemici.clear();
			for (int i = 0; i < GameManager.NumeroNemiciDifficoltaDifficile; i++) {
				DynamicElements.Nemici.add(new Enemy());
				DynamicElements.Nemici.get(i).setName(String.valueOf(i));
			}

			for (Enemy e : DynamicElements.Nemici) {
				e.start();
			}
		}
	}
}
