'use strict';
function log(msg){
    console.log(msg);
} 
console.clear();
log('hoisting : var, function declaration이 Top으로 이동');

function printImmediately(print){
    print();
}

setTimeout(function(){
    log('in the timer');
},1000);

setTimeout(() => log('In the timer'),1000);

printImmediately( () => log('hello'));



class UserStorage{
    loginUser(id, password, onSuccess, onError) {
        setTimeout(()=>{ 
            if( id === 'sally') {
                onSuccess();
            }else{
                onError();
            }
        });
    }
    getRoles(user, onSuccess, onError){
        setTimeout( () => { 
            if( id === 'sally') {
                onSuccess({name:'sally', role:'admin'});
            }else{
                onError();
            }
        } );
    }
}
log('NodeJs로 js 실행시  prompt 오류(window가 없기 때문에)')
log('Browser로 실행시켜야함')

/*

const userStorage = new UserStorage();
const id = prompt('enter your id');
const password = prompt('enter your password');

userStorage.loginUser(id, password, 
    user=>{
        userStorage.getRoles(
            user,
            userWithRole =>{
                    alert(`${userWithRole.name}  ${userWithRole.role}`);
            },
            error => { log('error 1');}
        )
    },
    error => { log('error 2');} 
);


*/




log('end file.');

log('\n\n\n\n출처 : 드림코딩 by 엘리');
log('https://www.youtube.com/watch?v=1Lbr29tzAA8&list=PLv2d7VI9OotTVOL4QmPfvJWPJvkmv6h-2&index=11');