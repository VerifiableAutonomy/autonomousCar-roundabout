package eass.car_prediction;

import ail.mas.scheduling.NActionScheduler;
import ail.syntax.Literal;
import ajpf.util.AJPFLogger;
import eass.mas.DefaultEASSEnvironment;

public class RoundaboutEnv extends DefaultEASSEnvironment {
	String logname = "eass.car_predication.RoundaboutEnv";

	public RoundaboutEnv() {
		super();
		NActionScheduler s = new NActionScheduler(20);
		s.addJobber(this);
		setScheduler(s);
		addPerceptListener(s);
	}

	public void eachrun() {
		Literal obstacle = new Literal("start");
		AJPFLogger.info(logname, "added a shared beilief");
		addPercept(obstacle);
	}

}
