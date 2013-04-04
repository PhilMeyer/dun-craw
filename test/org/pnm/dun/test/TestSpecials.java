package org.pnm.dun.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.pnm.dun.engine.RulesEngine;
import org.pnm.dun.env.Environment;
import org.pnm.dun.unit.ActiveType;
import org.pnm.dun.unit.HeroFactory;
import org.pnm.dun.unit.MonsterFactory;
import org.pnm.dun.unit.Unit;

public class TestSpecials extends TestCase{

	private static final int RUNS = 3000;
	private static final Unit G1 = MonsterFactory.generic(14,10,5,9);
	private static final Unit G2 = MonsterFactory.generic(12,13,5,9);
	private static final Unit G3 = MonsterFactory.generic(12,13,5,9);
	private static final Unit D = HeroFactory.dhov();
	private static final Unit S = HeroFactory.siege();
	private static final Unit F = HeroFactory.fang();
	private static final Unit H = HeroFactory.hawk();
	Environment e = new Environment();
	RulesEngine re = new RulesEngine(e);
	
	static Map<String,Double> averages = new HashMap<>();
	
	@Override
	public void setUp(){
		e.place(H, 5,5);
		e.place(S, 5,6);
		e.place(F, 0,0);
		e.place(G1, 0,1);
		e.place(G2, 1,0);
		e.place(G3, 1,1);
		re.testMode = true;
	}	
	

	private static synchronized void dumpMap() {
		for(Entry<String, Double> entry : averages.entrySet()){
			System.out.println(entry.getKey()+" average: "+entry.getValue());
		}
	}
	
	public void testC(){
		calcAverage(ActiveType.CLEAVE, F, G1);
		dumpMap();
	}
	
	public void testBS(){
		calcAverage(ActiveType.BASIC, S, G1);
		dumpMap();
	}
	
	public void testBF(){
		calcAverage(ActiveType.BASIC, F, G1);
		dumpMap();
	}
	
	public void testBH(){
		calcAverage(ActiveType.BASIC, H, G1);
		dumpMap();
	}
	
	public void testTS(){
		calcAverage(ActiveType.TWIN_STRIKE, H, G1);
		dumpMap();
	}
	
	public void testPA(){
		calcAverage(ActiveType.POWER, S, G1);
		dumpMap();
	}

	private void calcAverage(ActiveType type, Unit a, Unit d) {
		int total = 0;
		for(int i = 0; i < RUNS; i++){
			total += re.resolve(a, d, type);
		}
		double avg = total/(double)RUNS;
		averages.put(a.getName()+"_"+type, avg);
		//System.out.println(type+" average: "+avg);
	}	
	
}
