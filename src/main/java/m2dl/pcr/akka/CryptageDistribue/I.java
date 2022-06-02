package m2dl.pcr.akka.CryptageDistribue;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;

public class I extends UntypedActor {

    private ActorSelection controle;
    private ActorSelection receptor;

    public I(ActorSelection controle, ActorSelection receptor) {
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
