'use strict';
function log(msg){
    console.log(msg);
} 
console.clear();

log('Optional Chaining (ES11)');
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

log('Nullish Coalescing Operateor(ES11)');

const name = '';
const userName = name ?? 'Guest';
log(userName);

const num = 0;
const message = num ?? 'undefiend';
log(message);




log('Template Literals = > 백틱(`)으로 log 출력' );

log('\n\n\n\n출처 : 드림코딩 by 엘리');
log('https://www.youtube.com/watch?v=1Lbr29tzAA8&list=PLv2d7VI9OotTVOL4QmPfvJWPJvkmv6h-2&index=23');
