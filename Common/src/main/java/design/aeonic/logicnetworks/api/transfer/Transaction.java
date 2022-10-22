package design.aeonic.logicnetworks.api.transfer;

public interface Transaction<T> {

    T get();

    void set(T value);

    Transaction<T> branch(Splitter<T> splitter);

    boolean isOpen();

    void commit();

    void abort();

    @FunctionalInterface
    interface Splitter<T> {
        /**
         * Splits the given value; modifies the parameter to contain the remainder.
         */
        T split(T value);
    }

    @FunctionalInterface
    interface Listener {
        void onClose(boolean wasCommitted);

        default Listener then(Listener listener) {
            return wasCommitted -> {
                onClose(wasCommitted);
                listener.onClose(wasCommitted);
            };
        }
    }
}
