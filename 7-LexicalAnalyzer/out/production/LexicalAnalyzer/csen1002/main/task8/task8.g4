grammar task8;

//Parser Rule start to parse the input and check whether it is accepted or rejected
start: (Q2 | Q3 | Q4) + EOF ;

fragment Q0: /* epsilon */ (ZERO ZERO ZERO ZERO | ZERO ZERO (ZERO ONE | ONE)(ONE | ZERO ONE)* ZERO ZERO | ONE | ZERO ONE)*;

fragment Q1: Q0 ZERO ;
//Lexer Rule Q2 which has the Regular Expression of the accepted state Q2
Q2: Q1 ZERO ;

//Lexer Rule Q3 which has the Regular Expression of the accepted state Q3
Q3: Q2 ZERO | Q4 ZERO ;

//Lexer Rule Q4 which has the Regular Expression of the accepted state Q4
Q4: (Q2 (ZERO ONE | ONE))(ONE | ZERO ONE)* ;

//Fragments representing the zeros and ones
fragment ZERO: '0' ;
fragment ONE: '1' ;