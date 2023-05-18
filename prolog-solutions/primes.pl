init(Max) :- mark(2, 4, Max), sieve(3, sqrt(Max), Max), !.

sieve(ToMark, NonMarkedMax, _) :- ToMark > NonMarkedMax, !.
sieve(ToMark, NonMarkedMax, Max) :-
    (composite(ToMark); NonMarkedMaxNext is ToMark * ToMark, mark(ToMark, NonMarkedMaxNext, Max)),
    ToMarkNext is ToMark + 2, sieve(ToMarkNext, NonMarkedMax, Max).

mark(_, Start, M) :- Start > M, !.
mark(Prime, Start, M) :-
    assert(composite(Start)),
    Next is Start + Prime, mark(Prime, Next, M).

prime(1).
prime(2).
prime(N) :- N > 2, \+ composite(N).

prime_divisors(1, []).
prime_divisors(N, D) :- N > 1, prime_divisors(N, D, 2), !.
prime_divisors(N, [N], _) :- prime(N), !.
prime_divisors(N, [Divisor | T], Divisor) :-
    prime(Divisor), 0 is mod(N, Divisor),
    Divided is N / Divisor, prime_divisors(Divided, T, Divisor).
prime_divisors(N, Divisors, Divisor) :- NextDivisor is Divisor + 1, prime_divisors(N, Divisors, NextDivisor).

twice([], []).
twice([H | T], [H, H | TwiceTail]) :- twice(T, TwiceTail).
square_divisors(N, SquareDivisors) :- prime_divisors(N, Divisors), twice(Divisors, SquareDivisors).
