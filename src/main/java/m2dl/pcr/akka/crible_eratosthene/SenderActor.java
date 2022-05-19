package m2dl.pcr.akka.crible_eratosthene;

import akka.actor.ActorRef;
import akka.actor.Props;
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
                a.tell(1, self());
                nMax = (Integer) n;
                x = 2;
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
                        a.tell(x, self());
                        x++;
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
