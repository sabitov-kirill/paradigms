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

% [(1,asd), (2,'123'), (3,sdf), (4,poqwe), (5,'132'), (6,fdg), (7,wert), (8,'234'), (9,werwer)]
% node((5,'132'),
%     node((3,sdf),
%         node((2,'123'),
%             node((1,asd),
%                 nil,
%                 nil),
%             nil),
%         node((4,poqwe),
%             nil,
%             nil)
%     ),
%     node((8,'234'),
%         node((7,wert),
%             node((6,fdg),
%                 nil,
%                 nil
%             ),
%             nil
%         ),
%         node((9,werwer),
%             nil,
%             nil
%         )
%     )
% )