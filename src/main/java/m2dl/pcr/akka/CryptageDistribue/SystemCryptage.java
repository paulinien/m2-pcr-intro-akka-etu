package m2dl.pcr.akka.CryptageDistribue;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class SystemCryptage {

    public static void main(String[] args) {
        final ActorSystem actorSystem = ActorSystem.create("actor-system-cryptage", ConfigFactory.load("cryptageProviderActor"));

        final ActorRef cryptageActor = actorSystem.actorOf(Props.create(CryptageProviderRemote.class), "cryptage-actor");
    }

}
