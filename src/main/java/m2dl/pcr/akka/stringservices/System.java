package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class System {

    public static final Logger log = LoggerFactory.getLogger(m2dl.pcr.akka.helloworld1.System.class);

    public static void main(String... args) throws Exception {

        final ActorSystem actorSystem = ActorSystem.create("actor-system");

        Thread.sleep(5000);

        final ActorRef controleActor = actorSystem.actorOf(Props.create(ErreurControleProvider.class), "controle-actor");
        final ActorRef receptorActor = actorSystem.actorOf(Props.create(Receptor.class), "receptor-actor");
        final ActorRef cryptageActor = actorSystem.actorOf(Props.create(CryptageProvider.class), "cryptage-actor");
        final ActorRef iActor = actorSystem.actorOf(Props.create(I.class, controleActor, receptorActor), "i-actor");

        Message m = new Message();
        m.setContent("ceci est un message");
        m.setReceptor(iActor);
        cryptageActor.tell(m, null);

        Thread.sleep(3000);

        log.debug("Actor System Shutdown Starting...");

        actorSystem.terminate();
    }
}
