package api;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public final class Expected {
    private Expected() {
    }

    public static final Matcher<? super Integer> SC_OK_OR_ACCEPTED = anyOf(is(200), is(202));
    public static final Matcher<? super Integer> SC_CREATED_OR_OK = anyOf(is(200), is(201));
    public static final Matcher<? super Integer> SC_CONFLICT_OR_FORBIDDEN = anyOf(is(403), is(409));

    public static final int SC_BAD_REQUEST = 400;
    public static final int SC_UNAUTHORIZED = 401;
    public static final int SC_FORBIDDEN = 403;
    public static final int SC_NOT_FOUND = 404;
    public static final int SC_INTERNAL_ERROR = 500;
}
