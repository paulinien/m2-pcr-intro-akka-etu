package m2dl.pcr.akka.exo1_2;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class ParentActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef helloActor;
    private ActorRef goodbyeActor;

    public ParentActor() {
        log.info("GoodbyeActor constructor");
        helloActor = getContext().actorOf(Props.create(HelloActor.class), "hello-actor");
        goodbyeActor = getContext().actorOf(Props.create(GoodbyeActor.class), "goodbye-actor");
    }

    Procedure<Object> hello = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                helloActor.tell(msg, null);
                getContext().become(goodbye,false);
            } else {
                unhandled(msg);
            }
        }
    };

    Procedure<Object> goodbye = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                goodbyeActor.tell(msg, null);
                getContext().unbecome();
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object o) throws Exception {
        hello.apply(o);
    }
}
