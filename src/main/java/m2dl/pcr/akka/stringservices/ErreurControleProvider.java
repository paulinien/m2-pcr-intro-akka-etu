package m2dl.pcr.akka.stringservices;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ErreurControleProvider extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof Message) {
            log.info("Controle");
            ((Message) o).setContent(StringUtils.ajouteCtrl(((Message) o).getContent()));
            ((Message) o).getReceptor().tell(o, null);
        } else {
            unhandled(o);
        }
    }
}
