domains
flag,name=symbol

predicates
mother(name,name)
father(name,name)
gender(name,flag)
cousin(name,name)
brother(name,name)
sister(name,name)
wife(name,name)
husband(name,name)
daughter(name,name)
son(name,name)
grandfather(name,name)
grandmother(name,name)
granddaughter(name,name)
grandson(name,name)
auntfmw(name,name)
auntff(name,name)
auntmmf(name,name)
auntmf(name,name)
unclefm(name,name)
uncleffm(name,name)
unclemm(name,name)
unclemfm(name,name)

clauses
gender(anna,male).
gender(jamna,female).
gender(harry,male).
gender(belle,female).
gender(cory,male).
gender(isha,female).
gender(danny,male).
gender(elvis,male).
gender(ford,male).
gender(gauri,female).

mother(belle,danny).
mother(belle,elvis).
mother(isha,ford).
mother(isha,gauri).
mother(jamna,belle).
mother(jamna,cory).

father(anna,belle).
father(anna,cory).
father(harry,danny).
father(harry,elvis).
father(cory,ford).
father(cory,gauri).

wife(jamna,anna).
wife(belle,harry).
wife(isha,cory).

husband(X,Y) :- wife(Y,X).
son(X,Y) :- mother(Y,X), gender(X,male).
daughter(X,Y) :- mother(Y,X), gender(X,female).
brother(X,Y) :- mother(M,X),mother(M,Y),X<>Y,gender(X,male).
sister(X,Y) :- mother(M,X),mother(M,Y),X<>Y,gender(X,female).
cousin(X,Y) :- mother(M,X),father(N,Y),sister(M,N).
grandfather(X,Y) :- mother(M,Y),father(X,M).
grandmother(X,Y) :- grandfather(M,Y),wife(X,M).
granddaughter(X,Y) :- grandfather(Y,X),gender(X,female).
grandson(X,Y) :- grandfather(Y,X),gender(X,male). 
auntfmw(X,Y) :- mother(N,Y),sister(N,M),wife(X,M). /* wife of brother of mother */
auntff(X,Y) :- mother(N,Y),sister(X,N). /* sister of mother */
auntmmf(X,Y) :- father(N,Y),brother(M,N),wife(X,M). /* wife of brother of father */
auntmf(X,Y) :- father(N,Y),sister(X,N). /* sister of father */
uncleffm(X,Y) :- mother(M,Y),sister(N,M),husband(X,N). /* husband of sister of mother */
unclefm(X,Y) :- mother(M,Y),brother(X,M). /* brother of mother */
unclemm(X,Y) :- father(M,Y),brother(X,M). /* brother of father */
unclemfm(X,Y) :- father(M,Y),sister(N,M),husband(X,N). /* husband sister of father */