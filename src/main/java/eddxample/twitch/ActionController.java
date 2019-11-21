package eddxample.twitch;

import java.util.concurrent.atomic.AtomicReference;

public class ActionController {
    public static AtomicReference<String> action;

    void setAction(String s) {
        action.set(s);
    }

}
