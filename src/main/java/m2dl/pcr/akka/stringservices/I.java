package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class I extends UntypedActor {

    private ActorRef controle;
    private ActorRef receptor;

    public I(ActorRef controle, ActorRef receptor) {
        this.controle = controle;
        this.receptor = receptor;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Message) {
            ((Message) o).setReceptor(receptor);
            controle.tell(o, null);
            getContext().stop(self());
        } else {
            unhandled(o);
        }
    }
}
