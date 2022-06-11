contains(V, [V | _]).
contains(V, [_ | T]) :- contains(V, T).

concat([], B, B).
concat([H | T], B, [H | R]) :- concat(T, B, R).

range(L, L, [L]).
range(N, L, [N | T]) :- N < L, N1 is N + 1, range(N1, L, T).

lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

map([], _, []).
map([H | T], F, [HR | TR]) :- G =.. [F, H, HR], call(G), map(T, F, TR).

filter(_, [], _, []).
filter(N, [H | T], F, [H | TR]) :- G =.. [F, N, H], call(G), !, filter(N, T, F, TR).
filter(N, [H | T], F, TR) :- filter(N, T, F, TR).

size([], 0).
size([H | T], R) :- size(T, R1), R is R1 + 1.

isDivide(N, D) :- 0 is mod(N, D).

repeatDivide(N, D, R) :-
    isDivide(N, D), !, N1 is N // D, repeatDivide(N1, D, R).
repeatDivide(N, D, N).

prime(C, N, R) :- C < 2, !, prime(2, N, R).
prime(C, N, [C | TR]) :-
     C2 is C * C,
     C2 =< N, isDivide(N, C), !,
     Q is N // C, prime(C, Q, TR).
prime(C, N, R) :-
     C2 is C * C, C2 =< N, !,
     C1 is C + 1, prime(C1, N, R).
prime(C, N, [N]) :- N =\= 1, !.
prime(C, N, []).

isEquals([H1 | T1], [H2 | T2]) :- H1 = H2, isEquals(T1, T2).
isEquals([], []).

prime(N) :- prime(2, N, R), size(R, 1).
composite(N) :- N =\= 1,\+ prime(N).

prime_divisors(N, Divisors) :- prime(2, N, R), isEquals(R, Divisors).
