package com.httq.app.helper;

import org.springframework.stereotype.Component;

@Component
public class Helpers {
    public Helpers(){
        StaticUtils.setHelpers(this);
    }

    public String cutText(String text, int begin, int end) {
        if (text == null)
            return "";

        if (text.length() > end) {
            text = text.substring(begin, end);

            if (begin > 0)
                text = text.substring(text.indexOf(" "), text.lastIndexOf(" "));
            else
                text = text.substring(0, text.lastIndexOf(" "));

            text = (begin > 0 ? "…" :"") + text + "…";
        }

        return text;
    }
}
