(*

  BST: bounded stack of int

  stack is created with a max size...
  pushing on a full stack is a no-op

  new:   int       --> BST
  push:  BST x int --> BST
  pop:   BST       --> BST
  top:   BST       --> E
  empty: BST       --> bool
  full:  BST       --> bool
  max:   BST       --> int
  size:  BST       --> int

*)

datatype BST = 
   New of int  
   | push of BST * int 
   ;

fun empty (New(n)) = true 
  | empty (push(S,i)) = false 
  ;

fun max (New(n)) = n
  | max (push(S,i)) = max(S) 
  ;

fun size (New(n)) = 0
  | size (push(B,i)) = if size(B)=max(B)
                        then max(B)
                        else size(B)+1 
  ;

fun full (New(m)) = m=0
  | full (push(S,i)) = if size(S)>=max(S)-1
                        then true
                        else false 
  ;

exception topEmptyStack;

fun top (New(n)) = raise topEmptyStack 
  | top (push(S,i)) = if full(S) 
                       then top(S) 
                       else i 
  ;

fun pop (New(n)) = New(n)
  | pop (push(S,i)) = if full(S)
                       then pop(S)
                       else S 
  ;


val ns5 = New(5);
val ns2 = New(2);
val ns100 = New(100);
val ns20 = New(20);
    val b1 = New(2); 
        val b2 = push(b1,20);
            val b3 = push(b2,30);
                val b4 = push(b3,40);

val s1 = push(ns5,1);
val s2 = push(s1,2);
val s3 = push(s2,3);
val s4 = push(s3,4);
val s5 = push(s4,5);
val s6 = push(push(s5,6),7);

top(s6);
top(s5);
size(pop(b4));

val oops = push(s5,6);
