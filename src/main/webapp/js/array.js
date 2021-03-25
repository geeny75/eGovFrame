'use strict';

function log(msg){
    console.log(msg);
}
log('###console clear###');
console.clear();
log('출처 : 드림코딩 by 엘리');
log('https://www.youtube.com/watch?v=1Lbr29tzAA8&list=PLv2d7VI9OotTVOL4QmPfvJWPJvkmv6h-2&index=8');

log('함수로 이동 : Ctrl + 함수 click');

const arr1 = new Array();

const fruits = ['apple','banana'];
log(fruits);
log(fruits[0]);
log(fruits[fruits.length-1]);
log("### print of fruits () ###");
log('case1 : for(let obj of fruits ){} ');
for(let obj of fruits ){
    log(obj);
}
log('case2-1 : 오프젝트.forEach() ){}  ==> callBack함수 호출 ');
fruits.forEach(function (fruit, index) {
    console.log(fruits, index);
});
log('case2-2 : 이름없는 함수 : fruits.forEach( (fruit, index) =>{ console.log(fruits, index);} ');
fruits.forEach( (fruit, index) =>{
    console.log(fruits, index);
});
log('case2-3 : 이름없는 한줄 함수 fruits.forEach( (fruit, index) => console.log(fruits, index)); ');
    fruits.forEach( (fruit, index) => console.log(fruits, index));
    
fruits.push('mango','berry','watermelon','kiwi');
log(fruits);

log('## 배열.splice() ');
log(' index 1 이후 2개 지우기,  fruits.splice(1, 2);');
fruits.splice(1, 2);
log(fruits); 

log('-  index 1 이후 모두 지우기,  fruits.splice(1)');
fruits.splice(1);
log(fruits);


log('- index 이후 1개 지우고, 2개 추가,  fruits.splice(1, 1, "tomato","orange"))');
fruits.splice(1, 1, 'tomato','orange');
log(fruits);

log('## 배열연결## ');

const fruits2 = ['potato','grape'];
log(fruits);
log(fruits2);
const newFruits = fruits.concat(fruits2);
newFruits.push('apple');
log(newFruits);

log('## indexof() & includes() & lastIndexOf()');
log(newFruits.indexOf('tomato'));
log(newFruits.includes('coconut'));
log(newFruits.indexOf('apple'));
log(newFruits.lastIndexOf('apple'));

