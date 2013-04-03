package org.pnm.dun.ui;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.pnm.dun.Environment;
import org.pnm.dun.behavior.Action;
import org.pnm.dun.behavior.AttackAction;
import org.pnm.dun.behavior.MeleeBasic;
import org.pnm.dun.behavior.MoveTowardsAction;
import org.pnm.dun.behavior.Strategy;
import org.pnm.dun.model.Unit;
import org.pnm.dun.resolve.Sim;
import org.pnm.dun.util.Location;

public class GameRunner implements Runnable {

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
				if(e.getLocation(u) != null){
					Action a = strat.getAction(e, u);
					resolve(a);
					sleep();
				}
			}
			sleep();
			turnTracker.reset();
		}
	}

	private void resolve(Action a) {
		if (a instanceof MoveTowardsAction) {
			List<Location> path = e.getShortestPath(a.actor, a.target);
			int move = a.actor.spd;
			for (Location step : path) {
				e.place(a.actor, step);
				if (--move < 1) {
					break;
				}
				sleep();
			}
		} else if (a instanceof AttackAction) {
			AttackAction aa = (AttackAction) a;
			System.out.println(aa.actor + " attacking " + aa.target);
			int resolution = sim.resolve(aa.actor, aa.target, aa.actor.getDefaultWeapon());
			if (resolution > 0) {
				e.remove(aa.target);
			}
		}
	}

	private static void sleep() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
