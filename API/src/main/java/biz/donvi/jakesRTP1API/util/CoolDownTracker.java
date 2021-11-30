package biz.donvi.jakesRTP1API.util;

public interface CoolDownTracker {
    /**
     * Gets the cool-down time in seconds.
     *
     * @return The cool-down time in seconds.
     */
    float getCoolDownTime();

    /**
     * Sets the cool--down time in seconds.
     *
     * @param coolDownTime The new cool-down time (in seconds)
     */
    void setCoolDownTime(float coolDownTime);

    /**
     * Checks if between now and the last logged time is greater or less than the last logged time.
     * No changes will be made to the data regardless of the result.
     *
     * @param playerName Name of the player/user to check.
     * @return If the difference in time between now and the last logged time is greater than the cool down.
     */
    boolean check(String playerName);

    /**
     * Checks if the time difference between now and the last logged time is greater or less than
     * the last logged time. If true (or this is the first time) the current time will be logged.
     *
     * @param playerName Name of the player/user to check.
     * @return If the difference in time between now and the last logged time is greater than the cool down.
     */
    boolean checkAndLog(String playerName);

    /**
     * Sets the logged time for the given player to the current time.
     *
     * @param playerName Player to log time for.
     */
    void log(String playerName);

    /**
     * Sets the logged time for the given player to the given time.
     *
     * @param playerName Player to log time for.
     * @param time       The time to log.
     */
    void log(String playerName, long time);

    /**
     * Returns the amount of time between now and the last successful log, or -1 if no time was ever logged.
     *
     * @param playerName The player to check.
     * @return The amount of time between now and the last logged time, or -1 if no time was ever logged.
     */
    long timeDifference(String playerName);

    /**
     * Returns the amount of time between the given time and the last successful log, or -1 if no time was ever logged.
     *
     * @param playerName The player to check.
     * @return The amount of time between given time and the last logged time, or -1 if no time was ever logged.
     */
    long timeDifference(String playerName, long time);

    /**
     * Returns the minimum amount of time to wait (in milliseconds) before being able to call a successful (true) check.
     * Negative values mean a check would return true.
     *
     * @param playerName The player to check.
     * @return Returns the minimum amount of time to wait before being able to call a successful check.
     */
    long timeLeft(String playerName);

    /**
     * Returns the amount of time left to wait before calling rtp again, formatted in a reasonable manor.
     *
     * @param playerName The player to check.
     * @return A string describing the time left until the cooldown is over. Human readable.
     */
    String timeLeftWords(String playerName);

    /**
     * Returns the amount of time that a player must wait between rtp calls, formatted in a reasonable manor.
     *
     * @return A human readable string of the cooldown time.
     */
    String coolDownTime();
}
