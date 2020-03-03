package com.tony.unit.core;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Predicate;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/18
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public interface LimitPredicate extends Predicate<HttpServletRequest> {


    /*@Override
    default Predicate<ServerWebExchange> and(Predicate<? super ServerWebExchange> other) {
        return new AndLimitPredicate(this, wrapIfNeeded(other));
    }

    static LimitPredicate wrapIfNeeded(Predicate<? super ServerWebExchange> other){
        return null;
    }

    class AndLimitPredicate implements LimitPredicate{

        private LimitPredicate left;
        private LimitPredicate right;

        AndLimitPredicate(LimitPredicate left, LimitPredicate right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean test(ServerWebExchange t) {
            return left.test(t) && right.test(t);
        }
    }*/

    class AlwaysNeedLimitPredicate implements LimitPredicate{
        @Override
        public boolean test(HttpServletRequest httpServletRequest) {
            return true;
        }
    }

}
