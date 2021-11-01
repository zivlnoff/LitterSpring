package cn.bugstack.springframework.beans.context;

import javax.sound.midi.Soundbank;
import java.util.EventObject;

public class Event extends EventObject {
    protected Event(Object source){
        super(source);
    }
}
