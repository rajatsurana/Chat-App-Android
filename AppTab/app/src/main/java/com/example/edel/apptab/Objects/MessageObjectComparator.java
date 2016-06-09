package com.example.edel.apptab.Objects;

import java.util.Comparator;

/**
 * Created by Rajat on 08-06-2016.
 */
public class MessageObjectComparator implements Comparator<MessageObject> {
    @Override
    public int compare(MessageObject messageObject1, MessageObject messageObject2) {
        return messageObject1.getTime_readable().compareTo(messageObject2.getTime_readable());
    }
}
