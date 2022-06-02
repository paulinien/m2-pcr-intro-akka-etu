package m2dl.pcr.akka.CryptageDistribue;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;

import java.io.Serializable;

public class Message implements Serializable {

    private String content;
    private ActorSelection receptor;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ActorSelection getReceptor() {
        return receptor;
    }

    public void setReceptor(ActorSelection receptor) {
        this.receptor = receptor;
    }
}
