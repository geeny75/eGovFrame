'use strict';
function log(msg){
    console.log(msg);
} 
console.clear();

log('## Optional Chaining (ES11)');
const person1 = {
    name :'sally',
    job: {
        title: 'enginner',
        manager:'bob' 
    }
};
const person2 = {
    name : 'Bob' 
}
function printManager(person) {
    log(person.job.manager.name);
}
function printManager2(person) {
    log(person.job?.manager?.name);
}
printManager(person1);
printManager2(person2);

log('## Nullish Coalescing Operateor(ES11)');

const name = '';
const userName = name ?? 'Guest';
log(userName);

const num = 0;
const message = num ?? 'undefiend';
log(message);


log('## BigInt (A BIGINT is always 8 bytes and can store -9223372036854775808 to 9223372036854775807)');
const x = Number.MAX_SAFE_INTEGER + 1;
const y = Number.MAX_SAFE_INTEGER + 2;
log(x === y); //true

//node 8 is not a supported runtime.
const x1 = BigInt(9007199254740991);//9천조..
const y1 = BigInt(9007199254740994);
log(x1 === y1); //false 

const x2  = 9007199254740991n;
log( typeof x2);//bigint

const obj = { a: 1, b:2, c:3};
for(const key in obj ){
    log(obj[key]);// 1 2 3
}

let str = 'I love JavsScript yes I do JavaScript';
let result = str.matchAll(/JavaScript/g);//
log(...result);
/*
[
  'JavaScript',
  index: 27,   
  input: 'I love JavsScript yes I do JavaScript',
  groups: undefined
]
*/

const promise3 = Promise.resolve("another resolve");
const promise1 = Promise.resolve("reject");
const promise2 = Promise.reject("resolve");
const promiseA = [promise1, promise2, promise3];
Promise.all(promiseA).then((a) => log(a));


log('Template Literals = > 백틱(`)으로 log 출력' );

log('\n\n\n\n출처 : 드림코딩 by 엘리');
log('https://www.youtube.com/watch?v=1Lbr29tzAA8&list=PLv2d7VI9OotTVOL4QmPfvJWPJvkmv6h-2&index=23');
