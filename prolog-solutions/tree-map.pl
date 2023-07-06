split(List, Left, Mid, Right) :-
    length(List, Len),
    Median is Len // 2,
    length(Left, Median),
    append(Left, [Mid | Right], List).

map_build([], nil) :- !.
map_build(List, node(Root, Left, Right)) :-
    split(List, Prefix, Root, Suffix),
    map_build(Prefix, Left),
    map_build(Suffix, Right).

map_get(node((Key, Value), _, _), Key, Value) :- !.
map_get(node((RootKey, _), Left, Right), Key, Value) :-
    ( Key < RootKey, map_get(Left, Key, Value) );
      map_get(Right, Key, Value).

map_values(nil, []).
map_values(node((_, Value), Left, Right), Values) :-
    map_values(Left, ValuesLeft),
    map_values(Right, ValuesRight),
    append(ValuesLeft, [Value], ValuesLeftCurrent),
    append(ValuesLeftCurrent, ValuesRight, Values).
