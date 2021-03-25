'use strict';
function log(msg){
    console.log(msg);
} 
console.clear();

log('async & await');

function fetchUser(){
    return new Promise( (resolve, reject) => {
        resolve('sally');
    });
}
/*
async function fetchUser(){ 
    return  'sally'; 
} */

const user = fetchUser();
user.then(console.log);

log(user);

async function fetchUser(){ 
    await delay(3000);
    return  'sally'; 
}
async function getApple(){ 
    await delay(3000);
    return  'apple'; 
}
async function getBanana(){ 
    await delay(3000);
    return  'banana'; 
}
function pickFruits(){
    const applePromise =  getApple();
    const bananaPromise =  getBanana();
    const apple = await getApplePromise();
    const banana = await getBananaPromise();

    return `${apple} ${banana}` ;
}

pickFruits().then(console.log);
function pickAllFruits(){ 
    log('모두 완료 시 표시');
    return Promise.all([getApple(), getBanana()]).then( fruits => 
        fruits.join(' +  ')
    );
}

pickAllFruits().then(console.log);

function pickOnlyOne(){
    log('먼저 완료되는 것 만 표시');
    return Promise.race([getApple() , getBanana()]);
}
