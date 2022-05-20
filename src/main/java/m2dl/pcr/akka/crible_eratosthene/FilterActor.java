package m2dl.pcr.akka.crible_eratosthene;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class FilterActor extends UntypedActor {

    private int value;
    private ActorRef previousActor;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    ActorRef next;

    public FilterActor(int value) {
        this.value = value;
        log.info(value + " est premier");
    }

    Procedure<Object> init = new Procedure<>() {
        public void apply(Object x) throws Exception {
            if (x instanceof Integer) {
                previousActor = sender();
                if((Integer) x % value != 0) {
                    next = getContext().actorOf(Props.create(FilterActor.class, x), "next-"+x+"-actor");
//                    next.tell(x, self());
                    getContext().become(nextCreated, false);
                    previousActor.tell("ok", self());
                } else {
                    previousActor.tell("ok", self());
                }
            } else if(x instanceof String){
                if("stop".equalsIgnoreCase(((String) x))) {
                    getContext().become(destroy, false);
                    self().tell("stop", null);
                }
            } else {
                unhandled(x);
            }
        }
    };

    Procedure<Object> waitAck = new Procedure<>() {
        public void apply(Object x) throws Exception {
            if(x instanceof String){
                if("ok".equalsIgnoreCase(((String) x))) {
                    if(previousActor != null)
                        getContext().sender().tell("ok", previousActor);
                    getContext().become(nextCreated, false);
                }
            } else {
                unhandled(x);
            }
        }
    };

    Procedure<Object> nextCreated = new Procedure<>() {
        public void apply(Object x) throws Exception {
            if (x instanceof Integer) {
                if((Integer) x % value != 0) {
                    next.tell(x, self());
                    getContext().become(waitAck, false);
                } else {
                    sender().tell("ok", self());
                }
            } else if(x instanceof String){
                if("stop".equalsIgnoreCase(((String) x))) {
                    getContext().become(destroy, false);
                    self().tell("stop", null);
                }
            } else {
                unhandled(x);
            }
        }
    };

    Procedure<Object> destroy = new Procedure<>() {
        public void apply(Object x) throws Exception {
            if(x instanceof String){
                if("stop".equalsIgnoreCase(((String) x))) {
                    next.tell(x, null);
                    Thread.sleep(200);
                    log.info("Filter "+value + " shutdown");
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
