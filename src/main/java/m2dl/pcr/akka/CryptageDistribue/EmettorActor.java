package m2dl.pcr.akka.CryptageDistribue;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;

public class EmettorActor extends UntypedActor {

    private ActorSelection cryptageActor;
    private ActorSelection controleActor;

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof String && "start".equalsIgnoreCase((String) o)){
            cryptageActor = getContext().actorSelection("akka.tcp://actor-system-cryptage@127.0.0.1:2555/user/cryptage-actor");
            controleActor = getContext().actorSelection("akka.tcp://actor-system-controle@127.0.0.1:2554/user/controle-actor");
            Message m = new Message();
            m.setContent("ceci est un message");
            m.setReceptor(controleActor);
            cryptageActor.tell(m, null);
        }

    }
}
