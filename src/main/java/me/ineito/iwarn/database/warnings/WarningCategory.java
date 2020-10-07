package me.ineito.iwarn.database.warnings;

import java.util.HashMap;
import java.util.HashSet;

public class WarningCategory {

    private final String slug;
    private final String reason;
    private HashMap<Integer, String> punishments;

    public String getSlug() {
        return slug;
    }

    public String getReason() {
        return reason;
    }

    public HashMap<Integer, String> getPunishments() {
        return punishments;
    }

    public WarningCategory(String slug, String reason, HashMap<Integer, String> punishments) {
        this.slug = slug;
        this.reason = reason;
        this.punishments = punishments;
    }
}
