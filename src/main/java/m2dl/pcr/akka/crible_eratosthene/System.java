package m2dl.pcr.akka.crible_eratosthene;

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

        final ActorRef actorRef = actorSystem.actorOf(Props.create(FilterActor.class, 2), "filter-2-actor");

        for(int i=3; i<100; i++) {
            actorRef.tell(i,null);
        }

        actorRef.tell("stop",null);

//        actorRef.tell("Pauline",null);
//        actorRef.tell("Eva",null);
//        actorRef.tell("Bill",null);
//        actorRef.tell("Marc",null);

        Thread.sleep(3000);

        log.debug("Actor System Shutdown Starting...");

        actorSystem.terminate();
    }

}
