package m2dl.pcr.akka.CryptageDistribue;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ReceptorRemote extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Message) {
            log.info(((Message) o).getContent());
        } else {
            unhandled(o);
        }
    }
}
