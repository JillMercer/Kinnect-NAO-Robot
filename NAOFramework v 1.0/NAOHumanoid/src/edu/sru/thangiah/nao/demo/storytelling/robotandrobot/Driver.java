package edu.sru.thangiah.nao.demo.storytelling.robotandrobot;

public class Driver {

	public static final String FILE = System.getProperty("user.dir") + "\\src\\storytelling\\XML\\Stories\\friends.xml";

	public static void main(String[] args) throws Exception {

		SlaveNAO mcBeth = new SlaveNAO("Lady McBeth", "192.168.0.101");
		SlaveNAO kingLear = new SlaveNAO("King Lear", "192.168.0.102");
		SlaveNAO aegon = new SlaveNAO("Aegon", "192.168.0.103");
		SlaveNAO octavius = new SlaveNAO("Octavius", "192.168.0.104");

		MasterNAO master = new MasterNAO("192.168.0.100", FILE);
		Mediator mediator = new Mediator(master);

		mediator.addSlave(mcBeth);
		mediator.addSlave(kingLear);
		mediator.addSlave(aegon);
		mediator.addSlave(octavius);

		mediator.run();
		mediator.join();
	}
}
