package m2dl.pcr.akka.CryptageDistribue;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class SystemReceptor {

    public static void main(String[] args) {
        final ActorSystem actorSystem = ActorSystem.create("actor-system-receptor", ConfigFactory.load("receptoreActorRemote"));

        final ActorRef receptorActor = actorSystem.actorOf(Props.create(ReceptorRemote.class), "receptor-actor");
    }

}
