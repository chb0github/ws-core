package org.bongiorno.common.utils.functions.predicates;

import org.bongiorno.common.utils.Function;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

public class RegexMatchPredicate implements Function<CharSequence, Boolean> {

    private Pattern regex;

    public RegexMatchPredicate(String regex){
        this.regex = Pattern.compile(regex);
    }

    @Override
    public Boolean apply(@Nullable CharSequence input) {
        return input != null && regex.matcher(input).matches();
    }
}
