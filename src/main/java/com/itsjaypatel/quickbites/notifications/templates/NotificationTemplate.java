package com.itsjaypatel.quickbites.notifications.templates;

import java.util.List;
import java.util.Map;

public abstract class NotificationTemplate {

    protected final Map<String,Object> payLoad;
    protected final String name;
    protected final List<String> fields;

    public NotificationTemplate(final Map<String,Object> payLoad,final String name,final List<String> fields) {
        this.payLoad = payLoad;
        this.name = name;
        this.fields = fields;
    }

    public String getTemplate(){
        return name;
    }
}
