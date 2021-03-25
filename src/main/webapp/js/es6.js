'use strict';
function log(msg){
    console.log(msg);
} 
console.clear();

log('## Destructuring Assignment ##');

const student = {
    name : 'sally',
    age : 9
}

const {name, age } = student;
const {name : newname, age : newage } = student;

console.log(name, age);
console.log(newname, newage);

const animals = ['dog','cat']; 

const [first, second] = animals;
console.log(first, second);

log('## spread syntax ( reference copy)##')

const obj1 = {key : 'key1', count:1};
const obj2 = {key : 'key2', count:2};
const array = [obj1, obj2];

const arrayCopy = [...array];
const arrayCopy2 = [...array, {key : 'key3', count:3}];
const obj3 = arrayCopy2.slice(2,3);
obj1.key='newkey';
console.log(array, arrayCopy, arrayCopy2);

const fruits1 = ['apple', 'banana'];
const fruits2 = ['kiwi', 'tomato'];

const allFruits = [...fruits1, ...fruits2, 'strawberry'];
console.log(allFruits);
log('## key 이름이 같으면 마지막 값만 표시');
const dog1 = {age1:1};
const dog2 = {age2:2};
const dogs = {...dog1, ...dog2};
log(dogs);

function printMessage(message = 'defualt messge'){

}
log('- 터너리?? 연산자');
const isCat = true;
const componet = isCat ? 'cat': 'dog' ;

log('Template Literals = > 백틱(`)으로 log 출력' );

log('\n\n\n\n출처 : 드림코딩 by 엘리');
log('https://www.youtube.com/watch?v=1Lbr29tzAA8&list=PLv2d7VI9OotTVOL4QmPfvJWPJvkmv6h-2&index=26');
