'use strict';

function log(msg){
    console.log(msg);
}
log('###console clear###');
console.clear();

log('## Array API (JavaScript ES6)');
log('- 배열명.join() : 배열요소를 문자열로 반환');

const fruits = ['apple','banana'];

log(fruits);
const sFruits = fruits.join();
log(sFruits);
const sFruits2 = fruits.join(' and ');
log(sFruits2);

log('- 문자열.split(구분자, 갯수) : 문자열을 배열로 반환');
const arrFRuits = sFruits.split(',');
log(arrFRuits);

log('- 배열열.reverse() : 자신의 배열순서를 바꾸고 바뀐 값을 반환 ');
const num = [1,3,2,5,0];
log(num);
const numReverse = num.reverse();
log(num);
log(numReverse);

log('- 배열열.slice() : 배열의 중간값 반환 ');

const newnum = num.slice(2,5);
log(newnum);
log('- 배열열.find(콜백함수) : 배열에서 찾기 ');
class Student{
    constructor(name, age, enrolled, score) { 
        this.name = name;
        this.age = age;
        this.enrolled = enrolled;
        this.score = score; 
    }
}
const students = [
    new Student('sally',1, true , 95),
    new Student('jessica',1, true , 94),
    new Student('seiya',1, false , 90),
    new Student('ellie',1, true , 85)
]
log('- case 1 : students.find(function (student, index){}) ');
const result = students.find(function (student, index){
    log(student, index);
    return student.score === 90;
});
log('- case 2 : students.find((student)=> student.score === 90)');
const result1 = students.find((student)=> student.score === 90);
log(result1);

log('- 배열열.filter(콜백함수) : 배열filter ');

const result2 = students.filter((students) => students.enrolled)
log(result2);

log('- 배열열.map(콜백함수) : 배열 변환 ');
const result3 = students.map((student) => student.score );
log(result3);

const result4 = students.map((student) => student.score * 2 );
log(result4);
log('- 배열열.some(콜백함수) : 1개라도 만족하면 true ');
const result5 = students.some( (student) => student.score < 90 )
log(result5);

log('- 배열열.some(콜백함수) : 모두 만족하면 true ');
const result6 = students.every( (student) => student.score >= 90 )
log(result6);

log('- 배열열.reduce(콜백함수) : 값을 누적 ');
const result7 = students.reduce( (prev, curr) =>{
    log(prev);
    log(curr);
    return prev + curr.score;   
} , 0);
log('평균 : ' + result7  / students.length);

log('\n\n\n\n출처 : 드림코딩 by 엘리');
log('https://www.youtube.com/watch?v=1Lbr29tzAA8&list=PLv2d7VI9OotTVOL4QmPfvJWPJvkmv6h-2&index=9');
