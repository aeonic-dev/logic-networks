package design.aeonic.logicnetworks.api.block;

import design.aeonic.logicnetworks.api.logic.network.SignalType;

public interface NetworkCache {
    /**
     * Writes the given value to this network cache and marks it for serialization.
     */
    <T> void writeValue(SignalType<T> type, T value);

    /**
     * Reads the value of the given type from this network cache, or null there is none.<br><br>
     * You shouldn't need to worry about null values if you're using this in a source node; any null values you
     * return as outputs will be ignored and the node will be considered to have no output.
     */
    <T> T readValue(SignalType<T> type);
}
