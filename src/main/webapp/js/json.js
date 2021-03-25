function log(msg){
    console.log(msg);
}
log('###console clear###');
console.clear();


log('## Object  to JSON ##');
let json = JSON.stringify(['apply','banana']);
log(json); //["apply","banana"]


const rabbit = {
    name : 'tori',
    color : 'white',
    size : null,
    birthDate : new Date(),
   // symbol:Symbol('id'),
    jump: () => {
        console.log(`${this.name} can jump!`)
    },
};
log(rabbit);
let json2 = JSON.stringify(rabbit)
log(json2);
log('- 지정한 키만 변환 : JSON.stringify(rabbit, [ name ])  ');
let json3 = JSON.stringify(rabbit, ['name'])
log(json3);

log('- 지정한 키만 변환 : JSON.stringify(rabbit, 콜백함수) \n name을 모두  sally 로 수정  ');
let json4 = JSON.stringify(rabbit, (key, value)  => {
    console.log(`key : ${key} , value : ${value}`);
    return key === 'name' ? 'sally' : value;
} );
log(json4);


log('## Object to JSON  : JSON.parse(json4)  ##'); 
let json5 = JSON.stringify(rabbit);
const obj =  JSON.parse(json5);
//log(rabbit);
//log(json5);
log(obj); 
//rabbit.jump();
const obj2 =  JSON.parse(json5, (key, value) => {
    console.log(`key : ${key} , value : ${value}`)
    return key === 'birthDate' ? new Date(value) : value;
});
log(rabbit.birthDate.getDate());
log(obj2.birthDate.getDate());






log('\n\n\n\n출처 : 드림코딩 by 엘리');
log('https://www.youtube.com/watch?v=1Lbr29tzAA8&list=PLv2d7VI9OotTVOL4QmPfvJWPJvkmv6h-2&index=10');
