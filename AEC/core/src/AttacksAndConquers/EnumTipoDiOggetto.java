package AttacksAndConquers;

public enum EnumTipoDiOggetto {
	CarroArmato ,
	Casse,
	Munizioni,
	PrimoSoccorso,
	Terreno,
	Cespuglio,
	Albero;
	
	int getTipo(){
		
		switch (this) {
	    case CarroArmato:
	        return 7;
	    case Cespuglio:
	        return 6;
	    case Casse:
	        return 8;
	    case Munizioni:
	        return 9;
	    case PrimoSoccorso:
	        return 10;
	    case Terreno:
	    	return 4;
	    case Albero:
	    	return 5;
	    	
	    default:
	        throw new AssertionError("Unknown operations " + this);
		}
	}	
}

