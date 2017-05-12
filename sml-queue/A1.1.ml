(* 
  LIST ADT
  new:
  ins: LIST x elt x int
  rem: LIST x int
  get: LIST x int
  find: LIST x elt
  size: LIST
  empty: LIST
*)

datatype (''a) LIST = Nil
    | ins of (''a) LIST * ''a * int
    ;

exception remNilList;

fun rem(Nil,r) = raise remNilList
    | rem(ins(S,e,i),r) = if r=i
                          then S
                          else if r>i
                          then ins(rem(S,r-1),e,i)
                          else ins(rem(S,r),e,i-1)
    ;

exception getNilList;

fun get(Nil,g) = raise getNilList
    | get(ins(S,e,i),g) = if g=i
                          then e
                          else if g<i 
                          then get(S,g)
                          else get(S,g-1)
    ;

exception findNilList;

fun find(Nil,f) = raise findNilList
    | find(ins(S, e, i),f) = if e=f
                             then i
                             else if find(S,f)<i
                             then find(S,f)
                             else find(S,f)+1
    ;

fun size (Nil) = 0
    | size(ins(S,e,i)) = size(S)+1;
    ;

fun empty (Nil) = true
    | empty(ins(S,e,i)) = false
    ;

(*---------------------------------------*)
(*   test data points                    *)
(*---------------------------------------*)

val l1 = ins(ins(ins(ins(ins(Nil,5,1),4,2),3,3),2,4),1,5);
val l2 = ins(ins(l1,10,4),9,3);
val l3 = rem(rem(rem(rem(rem(rem(rem(l2,7),6),5),4),3),2),1);
val l4 = ins(l2,1000,1000);

(*---------------------------------------*)
(*   test cases                          *)
(*---------------------------------------*)

size(Nil) = 0;
empty(l1) = false;
size(l1) = 5;
get(l1,1) = 5;
find(l1,1) = 5;

get(l2,5) = 10;
find(l2,10) = 5;
size(l2) = 7;
get(l2,7) = 1;

size(l3) = 0;
empty(l3) = true;

( get(l3,1) ; false ) handle getNilList => true;
( rem(l3,1) ; false ) handle remNilList => true;
( find(l3,1) ; false ) handle findNilList => true;

size(Nil) = 0;
size(ins(l4,100,1000)) = size(l4) + 1;
empty(Nil) = true;
get(ins(l1,10,5),5) = 10;
get(ins(l1,10,5),4) = get(l1,4);
get(ins(l1,10,5),6) = get(l1,5);

