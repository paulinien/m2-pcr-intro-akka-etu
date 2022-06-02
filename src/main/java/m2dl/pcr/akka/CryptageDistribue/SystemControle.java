package m2dl.pcr.akka.CryptageDistribue;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class SystemControle {

    public static void main(String[] args) {
        final ActorSystem actorSystem = ActorSystem.create("actor-system-controle", ConfigFactory.load("erreurControleProviderRemote"));

        final ActorRef controleActor = actorSystem.actorOf(Props.create(ErreurControleProviderRemote.class), "controle-actor");
    }

}
