(*

  BQUE: bounded queue of E

  queue is created with a max size...
  enqing on a full queue is a no-op

  new: pos       -> BQUE    (here, pos is int > 0)
  enq: BQUE x E  -> BQUE 
  deq: BQUE      -> BQUE
  front: BQUE    -> E
  size: BQUE     -> nat     (naturals, int >= 0 )
  max: BQUE      -> pos
  empty: BQUE    -> boolean
  full: BQUE     -> boolean

*)

datatype BQUE = 
  New of int 
  | enq of BQUE * int
  ;

fun empty (New(n)) = true 
  | empty (enq(S,i)) = false 
  ;

fun max (New(n)) = n
  | max (enq(S,i)) = max(S) 
  ;

fun size (New(n)) = 0
  | size (enq(B,i)) = if size(B)=max(B)
                        then max(B)
                        else size(B)+1 
  ;

fun full (New(m)) = m=0
  | full (enq(S,i)) = if size(S)>=max(S)-1
                        then true
                        else false 
  ;

exception frontEmptyQueue;

fun front (New(n)) = raise frontEmptyQueue 
  | front (enq(S,i)) = if size(S) = 0
                        then i
                        else front(S)
  ;

fun deq (New(n)) = New(n)
  | deq (enq(S,i)) = if full(S)
                        then deq(S)
                        else if empty(S)
                        then S
                        else enq(deq(S),i)
  ;

(*---------------------------------------*)
(*   test data points                    *)
(*---------------------------------------*)

val ns5 = New(5);
val ns2 = New(2)
val ns100 = New(100);
val ns20 = New(20);
val s5full = enq(enq(enq(enq(enq(ns5,1),2),3),4),5);
val s5deq4 = deq(deq(deq(deq(s5full))));
val s5empty = deq(s5deq4);
val oops = enq(s5full,6);
val b1 = New(2); 
val b2 = enq(b1,20);
val b3 = enq(b2,30);
val b4 = enq(enq(b3,40),50);
val b5 = deq(b4);
val b6 = deq(b5);
val b7 = deq(b6);

(*---------------------------------------*)
(*   test cases                          *)
(*---------------------------------------*)

max(ns5) = 5;
max(ns2) = 2;
max(ns100) = 100;
max(ns20) = 20;

full(s5full) = true;
empty(s5full) = false;
size(s5full) = 5;
front(s5full) = 1;
max(s5full) = 5;

full(s5deq4) = false;
empty(s5deq4) = false;
size(s5deq4) = 1;
front(s5deq4) = 5;
max(s5deq4) = 5;

size(s5empty) = 0;
full(s5empty) = false;
empty(s5empty) = true;

size(oops) = 5;
(front(s5empty); false ) handle frontEmptyQueue => true;

size(b2) = 1;
size(b3) = 2;
size(b4) = 2;
max(b4) = 2;
size(deq(b4)) = 1;
size(b5) = 1;
size(b6) = 0;
size(b7) = 0;

size(ns5) = 0;
size(enq(s5deq4,1)) = size(s5deq4) + 1;
(front(ns5); false ) handle frontEmptyQueue => true;
front(enq(ns5,15)) = 15;
front(enq(s5deq4,15)) = front(s5deq4);
deq(ns5) = ns5;
deq(enq(ns5,15)) = ns5;
deq(enq(s5deq4,15)) = enq(deq(s5deq4),15);


