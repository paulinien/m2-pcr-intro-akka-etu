package m2dl.pcr.akka.crible_eratosthene;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class SenderActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    ActorRef a;
    private int x;
    private int nMax;

    public SenderActor(ActorRef a) {
        this.a = a;
    }

    Procedure<Object> sender = new Procedure<Object>() {
        public void apply(Object n) throws Exception {
            if (n instanceof Integer) {
                log.info("Envoi de 3");
                a.tell(3, self());
                nMax = (Integer) n;
                x = 4;
                getContext().become(senderLoop, false);
            } else {
                unhandled(n);
            }
        }
    };

    Procedure<Object> senderLoop = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if(msg instanceof String){
                if("ok".equalsIgnoreCase(((String) msg))) {
                    if(x <= nMax) {
                        log.info("Envoi de " + x);
                        a.tell(x, self());
                        x++;
                    } else {
                        a.tell("stop", self());
                    }
                }
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object o) throws Exception {
        sender.apply(o);
    }
}
