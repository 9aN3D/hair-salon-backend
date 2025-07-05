package pl.edu.wit.hairsalon.sharedKernel.domain;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.isNull;

public interface Range<T extends Comparable<? super T>> extends Comparable<Range<T>>, SelfValidator<Range<T>> {

    T start();

    T end();

    boolean overlaps(Range<T> arg);

    boolean includes(Range<T> arg);

    boolean includes(T arg);

    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    class DefaultRange<T extends Comparable<? super T>> implements Range<T> {

        private final T start;
        private final T end;

        public DefaultRange(T start, T end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public T start() {
            return start;
        }

        @Override
        public T end() {
            return end;
        }

        @Override
        public boolean overlaps(Range<T> other) {
            return start.compareTo(other.end()) < 0 && end.compareTo(other.start()) > 0;
        }

        @Override
        public boolean includes(Range<T> other) {
            return includes(other.start()) && includes(other.end());
        }

        @Override
        public boolean includes(T other) {
            return other.compareTo(start) >= 0 && other.compareTo(end) <= 0;
        }

        @Override
        public boolean isEmpty() {
            if (isNull(start) || isNull(end)) {
                return true;
            }
            return start.compareTo(end) > 0;
        }

        @Override
        public int compareTo(Range<T> arg) {
            return start.equals(arg.start())
                    ? end.compareTo(arg.end())
                    : start.compareTo(arg.start());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Range<?> that)) return false;
            return start.equals(that.start()) && end.equals(that.end());
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }

        @Override
        public Range<T> validate() {
            if (isEmpty()) {
                throw new ValidationException("Start is after end");
            }
            return this;
        }

        @Override
        public String toString() {
            return isEmpty()
                    ? "Empty date range"
                    : new StringJoiner(" - ")
                    .add("" + start)
                    .add("" + end)
                    .toString();
        }

    }

}
