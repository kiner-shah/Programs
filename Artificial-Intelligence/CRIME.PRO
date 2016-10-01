domains
   action, name, sex, relation, object, vice = symbol

predicates
   person(name, sex, relation)
   killed_with(name, object)
   killed(name)
   killer_brenda(name)
   killer_mortimer(name)
   motive(vice)
   owns_probably(name, object)
   suspect(name) 
   
clauses
   person(mortimer, m, sibling).
   person(george, m, sibling).
   person(owen, m, sibling).
   person(brenda, f, sibling).
   person(holmes, m, detective).
   person(watson, m, assistant).
   person(sterndale, m, cousin).
   person(hk, m, housekeeper).
   

   killed_with(brenda, devils_foot_poison).
   killed_with(mortimer, devils_foot_poison).
   
   killed(brenda).
   killed(mortimer). 	
   motive(money).
   motive(jealousy).
   motive(righteousnes).

   owns_probably(mortimer, devils_foot_poison).
   owns_probably(sterndale, devils_foot_poison).   
   
   suspect(X) :-
      killed_with(brenda, devils_foot_poison) ,
      owns_probably(X, devils_foot_poison).
   suspect(Y):-
   	killed_with(mortimer, devils_foot_poison),
   	owns_probably(Y, devils_foot_posion).
   	
   killer_brenda(Killer) :-
      person(Killer, m, sibling) ,
      killed(brenda) ,
      suspect(Killer).

    
   killer_mortimer(Killer2) :-
   	person(Killer2, _ ,_),
   	killed(mortimer),
   	not(killer_brenda(Killer2)),
   	suspect(Killer2).  
   