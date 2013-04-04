package org.pnm.dun.ui;

import org.pnm.dun.Environment;
import org.pnm.dun.model.MonsterFactory;
import org.pnm.dun.model.Unit;
import org.pnm.dun.model.HeroFactory;
import org.pnm.dun.util.GeometryUtil;
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
		//e.place(MonsterFactory.mulg(), new Location(5,2), 180);
		e.place(MonsterFactory.grunt(), new Location(5,15), 180);
		e.place(MonsterFactory.grunt(), new Location(6,15), 180);
		e.place(MonsterFactory.grunt(), new Location(7,15), 180);
		e.place(MonsterFactory.grunt(), new Location(8,15), 180);
		e.place(MonsterFactory.grunt(), new Location(5,14), 180);
		e.place(MonsterFactory.grunt(), new Location(6,14), 180);
		e.place(MonsterFactory.grunt(), new Location(7,14), 180);
		e.place(MonsterFactory.grunt(), new Location(8,14), 180);

		int y = 0;
		int x = 0;
		//e.place(HeroFactory.zaal(), new Location(x,y),180);
		//e.place(HeroFactory.dhov(), new Location(x,y+1),180);
		e.place(HeroFactory.whitemane(), new Location(x+1,y),180);
		e.place(HeroFactory.will(), new Location(x+1,y+1),180);
		//e.place(HeroFactory.siege(), new Location(x+2,y+1));
		//e.place(HeroFactory.whitemane(), new Location(x+1,y+2));
		
		return e;
	}

}
