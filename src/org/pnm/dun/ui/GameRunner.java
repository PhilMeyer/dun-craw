package org.pnm.dun.ui;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.pnm.dun.Environment;
import org.pnm.dun.behavior.Action;
import org.pnm.dun.behavior.ActionSet;
import org.pnm.dun.behavior.AttackAction;
import org.pnm.dun.behavior.MeleeBasic;
import org.pnm.dun.behavior.MoveTowardsAction;
import org.pnm.dun.behavior.Strategy;
import org.pnm.dun.model.Unit;
import org.pnm.dun.resolve.Sim;
import org.pnm.dun.util.GeometryUtil;
import org.pnm.dun.util.Location;

public class GameRunner implements Runnable {

	private static final int LONG_PAUSE = 1500;
	private static final int MED_PAUSE = 50;
	Logger log = Logger.getLogger(GameRunner.class);
	private Environment e;

	Unit u;
	Strategy strat = new MeleeBasic();
	TurnTracker turnTracker;
	Action currentAction;
	Sim sim = new Sim();

	public GameRunner(Environment e) {
		this.e = e;
		turnTracker = new TurnTracker(e);
	}

	public void run() {
		for (;;) {
			log.info("TURN " + turnTracker.turn);
			Set<Entry<Unit, Location>> entries = e.getUnitEntries();
			for (Entry<Unit, Location> entry : entries) {
				Unit u = entry.getKey();
				ActionSet actionSet = new ActionSet();
				if(e.isStillAlive(u)){
					Action a = strat.getAction(e, u, actionSet);
					resolve(a, actionSet);
					sleep(MED_PAUSE);
					Action b = strat.getAction(e, u, actionSet);
					resolve(b, actionSet);
					sleep(MED_PAUSE);
				}
			}
			sleep(MED_PAUSE);
			turnTracker.reset();
		}
	}

	private void resolve(Action a, ActionSet set) {
		if(a == null){
			return;
		}
		e.setCurrentlyActing(a.actor);
		if (a instanceof MoveTowardsAction) {
			System.out.println(a.actor+" is moving towards "+a.target);
			moveTowards(a);
			set.useMove();
			
		} else if (a instanceof AttackAction) {
			System.out.println(a.actor+" is attacking "+a.target);
			attack(a);
			set.useStandard();
		}
	}

	private void attack(Action a) {
		AttackAction aa = (AttackAction) a;
		System.out.println(aa.actor + " attacking " + aa.target);
		Location aLoc = e.getLocation(a.actor);
		Location tLoc = e.getLocation(a.target);
		int angle = GeometryUtil.getAngle(aLoc, tLoc);
		e.orient(a.actor, 180-angle);
		
		int resolution = sim.resolve(aa.actor, aa.target, aa.actor.getDefaultWeapon());
		if (aa.target.hp <= 0) {
			e.remove(aa.target);
		}
	}

	private void moveTowards(Action a) {
		List<Location> path = e.getShortestPath(a.actor, a.target);
		int move = a.actor.spd;
		Location previous = e.getLocation(a.actor);
		for (int i = 0; i < path.size() && move > 0; i++) {
			Location step = path.get(i);
			int angle = GeometryUtil.getAngle(previous, step);
			e.orient(a.actor, 180-angle);
			sleep(MED_PAUSE);
			e.place(a.actor, step);
			previous = step;
			move--;
		}
	}

	private static void sleep() {
		sleep(LONG_PAUSE);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
