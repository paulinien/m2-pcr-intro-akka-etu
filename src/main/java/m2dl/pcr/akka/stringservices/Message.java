package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;

import java.io.Serializable;

public class Message implements Serializable {

    private String content;
    private ActorRef receptor;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ActorRef getReceptor() {
        return receptor;
    }

    public void setReceptor(ActorRef receptor) {
        this.receptor = receptor;
    }
}
