package org.pnm.dun.unit;

public class Monster extends Unit{

	public Monster(String name, String imagePath, int mhp, int spd, int def, int arm, int cmd) {
		super(name, imagePath, mhp, spd, def, arm, cmd);
		side = Side.MONSTER;
	}

}
