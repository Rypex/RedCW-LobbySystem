package net.redcw.lobbysystem.manager;

/*
Class created by SpigotSource on 02.02.2020 at 14:11
*/

public class FriendManager {

    public String getLastTimeOnline(long endmillis) {
        long current = System.currentTimeMillis();
        long end = endmillis;

        long millis = current - end;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        long days = 0;
        long weeks = 0;
        long months = 0;
        long years = 0;

        while (millis > 1000) {
            millis -= 1000;
            seconds++;
        }

        while (seconds > 60) {
            seconds -= 60;
            minutes++;
        }

        while (minutes > 60) {
            minutes -= 60;
            hours++;
        }

        while (hours > 24) {
            hours -= 24;
            days++;
        }

        while (days > 7) {
            days -= 7;
            weeks++;
        }

        while (weeks > 4) {
            weeks -= 4;
            months++;
        }

        while (months > 12) {
            months -= 12;
            years++;
        }

        if (years != 0) {
            if (years == 1) {
                return 1 + " Jahr";
            } else {
                return years + " Jahre";
            }
        } else if (months != 0) {
            if (months == 1) {
                return 1 + " Monat";
            } else {
                return months + " Monate";
            }
        } else if (weeks != 0) {
            if (weeks == 1) {
                return 1 + " Woche";
            } else {
                return weeks + " Wochen";
            }
        } else if (days != 0) {
            if (days == 1) {
                return 1 + " Tag";
            } else {
                return days + " Tage";
            }
        } else if (hours != 0) {
            if (hours == 1) {
                return 1 + " Stunde";
            } else {
                return hours + " Stunden";
            }
        } else if (minutes != 0) {
            if (minutes == 1) {
                return 1 + " Minute";
            } else {
                return minutes + " Minuten";
            }
        } else if (seconds != 0) {
            if (seconds == 1) {
                return 1 + " Sekunde";
            } else {
                return seconds + " Sekunden";
            }
        } else {
            return "§c§oUnbekannt";
        }

    }

}
