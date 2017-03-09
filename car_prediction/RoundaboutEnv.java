package eass.car_prediction;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import ail.mas.scheduling.NActionScheduler;
import ail.syntax.Action;
import ail.syntax.Literal;
import ail.syntax.NumberTermImpl;
import ail.syntax.Unifier;
import ail.syntax.VarTerm;
import ail.util.AILSocketChannelServer;
import ail.util.AILSocketClient;
import ail.util.AILexception;
import ajpf.util.AJPFLogger;
import eass.mas.DefaultEASSEnvironment;

public class RoundaboutEnv extends DefaultEASSEnvironment {
	String logname = "eass.car_predication.RoundaboutEnv";
	/* Base port numbers - vehicle ID will be added to these */
	private int BASE_INBOUND_PORT = 17000;
	private int BASE_OUTBOUND_PORT = 18000;
	
	/* Inbound and outbound sockets, both are needed as Simulink can't handle bidirectional communication on a single socket */
	private AILSocketChannelServer inbound;
	private AILSocketChannelServer outbound;
		
	public RoundaboutEnv() {
		super();
		NActionScheduler s = new NActionScheduler(20);
		s.addJobber(this);
		setScheduler(s);
		addPerceptListener(s);
		
		System.out.print("Vehicle waiting for connection on port " + (BASE_INBOUND_PORT) + "... ");
		inbound = new AILSocketChannelServer(BASE_INBOUND_PORT);
		System.out.println("Connected!");
		System.out.print("Vehicle waiting for connection on port " + (BASE_OUTBOUND_PORT) + "... ");
		outbound = new AILSocketChannelServer(BASE_OUTBOUND_PORT);
		System.out.println("Connected!");

	}
	// command composed of three attributes: commandID (int), number of arguments (int) and the value of arguments (double)
	// if commandID == 0, there is no equivalent command in command_list, 
	public void eachrun() {
		//Literal obstacle = new Literal("start");
		//AJPFLogger.info(logname, "added a shared beilief");
		//addPercept(obstacle);
		
		/* Set up a receive buffer */
		ByteBuffer buf = ByteBuffer.allocate(33*8);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		
		/* Read from the inbound socket, if there's no data then we fail out */
		if (inbound.read(buf) <= 0) {
			AJPFLogger.info(logname, "buffer is empty");
		}
		
		double timestamp = buf.getDouble();
		int obstacle = (int) buf.getDouble();
		int predict_exit_at_first_junction = (int) buf.getDouble();;
		int exit_at_first_junction = (int) buf.getDouble();;

		Literal time = new Literal("time");
		time.addTerm(new NumberTermImpl(timestamp));
		addUniquePercept("abstraction_car_prediction", "time", time);

		Literal obs = new Literal("obstacle");
		obs.addTerm(new NumberTermImpl(obstacle));
		addUniquePercept("abstraction_car_prediction", "obstacle", obs);

		Literal pre_exit = new Literal("predict_exit_at_first_junction");
		pre_exit.addTerm(new NumberTermImpl(predict_exit_at_first_junction));
		addUniquePercept("abstraction_car_prediction", "predict_exit_at_first_junction", pre_exit);

		Literal exit = new Literal("exit_at_first_junction");
		exit.addTerm(new NumberTermImpl(exit_at_first_junction));
		addUniquePercept("abstraction_car_prediction", "exit_at_first_junction", exit);

	}

	
	public Unifier executeAction(String agName, Action act) throws AILexception {
		Unifier u = new Unifier();
		
		/* Create buffer to transmit our command */
		ByteBuffer buf2 = ByteBuffer.allocate(4);
		buf2.order(ByteOrder.LITTLE_ENDIAN);

		if (act.getFunctor().equals("maintain_speed")) {
			buf2.putInt(1);
		} else if (act.getFunctor().equals("speed_down")) {
			buf2.putInt(2);
		} else if (act.getFunctor().equals("maintain_speed")) {
			buf2.putInt(3);
		}
		outbound.write(buf2);
		u.compose(super.executeAction(agName, act));
		return u;
		
	}

}
