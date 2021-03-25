
function log(msg){
    console.log(msg);
}
log('###console clear###');
console.clear();
log('출처 : 드림코딩 by 엘리');
log('https://www.youtube.com/watch?v=1Lbr29tzAA8&list=PLv2d7VI9OotTVOL4QmPfvJWPJvkmv6h-2&index=7');
const person = {'name':'sally','age': 10};
console.log(person.name);
console.log(person['name']);
console.log('VSC 1 line Copy : Shift + Alt + 아래방향키');
const person4 = makePerson('jessica',11);
log(person4);
function makePerson(name, age){
    return {
        name : name, 
        age : age
    };
}
const person5 = makePerson2('jessica',11);
log(person5);
function makePerson2(name, age){
    return {
        name , 
        age 
    };
}
console.log('Object에 key 있는 Check');
console.log('name' in person);
console.log('address' in person);
log(' for(key in person) {} ');
for(key in person){
    log(key);
}

log(' for(value of array){ {} ');
const array  = [1,2,3,4];
for(value of array){
    log(value);
}

const user = {name : 'ellie', age : 30};
const user2  = user;
log('reference copy');

user2.name = 'geeny';
log(user.name);

const user3 = {};
for( key in user){
    user3[key] = user[key];
}

log(user3);
const user4 = {};
Object.assign(user4, user3);
log(user4);