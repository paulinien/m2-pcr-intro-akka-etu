package m2dl.pcr.akka.CryptageDistribue;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemRemote {

    public static final Logger log = LoggerFactory.getLogger(m2dl.pcr.akka.helloworld1.System.class);

    public static void main(String... args) throws Exception {

        final ActorSystem actorSystem = ActorSystem.create("actor-system-common", ConfigFactory.load("common"));

        Thread.sleep(5000);

        final ActorRef emitor = actorSystem.actorOf(Props.create(EmettorActor.class), "emitor-actor");

        emitor.tell("start", null);

        Thread.sleep(3000);

        log.debug("Actor System Shutdown Starting...");

        actorSystem.terminate();
    }
}
