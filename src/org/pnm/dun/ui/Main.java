package org.pnm.dun.ui;

import org.pnm.dun.Environment;
import org.pnm.dun.model.MonsterFactory;
import org.pnm.dun.model.Unit;
import org.pnm.dun.model.HeroFactory;
import org.pnm.dun.util.Location;

public class Main {

	public static enum Side{PLAYER_1, PLAYER_2};
	
	public static Unit featured;
	
	public static void main(String[] args) {
		Environment e = setupEnvironment();
		showUI(e);
		Thread gameThread = new Thread(new GameRunner(e));
		gameThread.start();
	}

	protected static void showUI(Environment e) {
		Frame frame = new Frame(e);
		Thread refreshThread = new Thread(new DisplayRunner(frame));
		refreshThread.start();
	}

	private static Environment setupEnvironment() {
		Environment e = new Environment();
		int y = 8;
		featured = HeroFactory.hawk();
		Unit mulg = MonsterFactory.mulg();
		mulg.setSide(Unit.Side.MONSTER);
		//e.place(mulg, new Location(5,2), 180);
		e.place(MonsterFactory.grunt(), new Location(4,3), 180);
		e.place(MonsterFactory.grunt(), new Location(7,3), 180);
		e.place(MonsterFactory.grunt(), new Location(6,7), 180);
		//e.place(UnitFactory.will(), new Location(1,0));
		//e.place(UnitFactory.will(), new Location(2,0));
		e.place(HeroFactory.siege(), new Location(5,y));
		e.place(HeroFactory.whitemane(), new Location(4,y));
		e.place(featured, new Location(6,y));
		e.place(HeroFactory.will(), new Location(5,y+1));
		e.place(HeroFactory.dhov(), new Location(6,y+1));
		e.place(HeroFactory.zaal(), new Location(4,y+1));
		return e;
	}

}
