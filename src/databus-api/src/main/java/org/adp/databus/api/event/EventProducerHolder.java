package org.adp.databus.api.event;

/**
 * hoder the event publisher
 *
 * @author zzq
 */
public class EventProducerHolder {
    private static EventProducer HOLDER = null;

    public static EventProducer getHolder() {
        return HOLDER;
    }

    public static void setHolder(EventProducer producer) {
        if (HOLDER == null) {
            HOLDER = producer;
        }
    }
}
