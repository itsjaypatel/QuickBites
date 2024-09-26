package com.itsjaypatel.quickbites.notifications.templates;

import com.itsjaypatel.quickbites.exceptions.NotificationPayLoadException;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

public abstract class EmailNotificationTemplate extends NotificationTemplate {

    public EmailNotificationTemplate(final Map<String,Object> payLoad, final String name,final List<String> fields) {
        super(payLoad, name,fields);
    }

    public Context getContext(){
        List<String> missingFields = null;
        try{
            missingFields = fields.stream().filter(field -> payLoad.get(field) == null).toList();
            if(!missingFields.isEmpty()) {
                throw new NotificationPayLoadException("One or more missing field in payload ", missingFields);
            }
            Context context = new Context();
            fields.forEach(field -> context.setVariable(field, payLoad.get(field)));
            return context;
        }catch (NotificationPayLoadException e){
            throw e;
        }catch (Exception e){
            throw new NotificationPayLoadException("Error occurred while preparing context for " + this.getTemplate(),List.of(e.getMessage()));
        }
    }
}
