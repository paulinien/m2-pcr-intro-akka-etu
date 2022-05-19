package m2dl.pcr.akka.crible_eratosthene;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class FilterActor extends UntypedActor {

    private int value;

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    ActorRef next;

    public FilterActor(int value) {
        this.value = value;
    }

    Procedure<Object> init = new Procedure<Object>() {
        public void apply(Object x) throws Exception {
            if (x instanceof Integer) {
                if((Integer) x % value != 0) {
                    log.info(x + " est premier");
                    next = getContext().actorOf(Props.create(FilterActor.class, x), "next-"+x+"-actor");
                    next.tell(x, null);
                    getContext().become(nextCreated, false);
                }
            } else if(x instanceof String){
                if("stop".equalsIgnoreCase(((String) x))) {
                    getContext().become(destroy, false);
                }
            } else {
                unhandled(x);
            }
        }
    };

    Procedure<Object> nextCreated = new Procedure<Object>() {
        public void apply(Object x) throws Exception {
            if (x instanceof Integer) {
                if((Integer) x % value != 0) {
                    next.tell(x, null);
                }
            } else if(x instanceof String){
                if("stop".equalsIgnoreCase(((String) x))) {
                    getContext().become(destroy, false);
                }
            } else {
                unhandled(x);
            }
        }
    };

    Procedure<Object> destroy = new Procedure<Object>() {
        public void apply(Object x) throws Exception {
            if(x instanceof String){
                if("stop".equalsIgnoreCase(((String) x))) {
                    next.tell(x, null);
                    getContext().stop(self());
                }
            } else {
                unhandled(x);
            }
        }
    };

    @Override
    public void onReceive(Object o) throws Exception {
        init.apply(o);
    }
}
