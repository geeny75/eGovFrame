'use strict';

function log(msg){
    console.log(msg);
}

log('ES6 Function');

 function changeName(obj){
     obj.name = 'corder';
     
 }
 const orgObj = {name:'sally'};
 changeName(orgObj) ;
 log( orgObj.name );

 const print = function(){
     log('print');
 }
 print();

 function randomQuiz(answer, printY, printN){
     if( answer === 'love you'){
         printY();
     }else{
        printN();
     }
 }
 const printYes = function (){
     log('Love you')
}
printNo = function print(){
    log('No')
 }

 const add = (a,b) => a + b;
 /*
 ({
     console.log('IIFE');
 })();
 */
console.log(add(1,2));